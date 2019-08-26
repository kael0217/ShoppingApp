package com.levent.pcd.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.levent.pcd.config.AWSConfig;
import com.levent.pcd.config.AWSConfig.Aws;

import lombok.Data;

@Service("AWSS3HelperImpl")
public class AWSS3HelperImpl implements AWSS3Helper {

	@Autowired
	AWSConfig awsConfig;
	private Credentials sessionCredentials;

	@PostConstruct
	private void init() {

	}

	private BasicSessionCredentials getBasicSessionCredentials() {
		if (sessionCredentials == null || sessionCredentials.getExpiration().before(new Date()))
			sessionCredentials = getSessionCredentials();

		return new BasicSessionCredentials(sessionCredentials.getAccessKeyId(), sessionCredentials.getSecretAccessKey(),
				sessionCredentials.getSessionToken());
	}

	private Credentials getSessionCredentials() {
		AWSSecurityTokenServiceClient stsClient = new AWSSecurityTokenServiceClient(
				new BasicAWSCredentials(awsConfig.getAws().getAccessKeyId(), awsConfig.getAws().getAccessKeySecret()));
		GetSessionTokenRequest getSessionTokenRequest = new GetSessionTokenRequest().withDurationSeconds(43200);
		this.sessionCredentials = stsClient.getSessionToken(getSessionTokenRequest).getCredentials();
		return this.sessionCredentials;
	}

	public AmazonS3 getAmazonS3Client() {
		BasicSessionCredentials basicSessionCredentials = getBasicSessionCredentials();
		return AmazonS3ClientBuilder.standard().withRegion(awsConfig.getS3().getRegion())
				.withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials)).build();
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	@Override
	@CachePut(value="imageServerCache", key="#root.args[1]")
	public String putObject(MultipartFile file, String fileName) throws AmazonServiceException, SdkClientException, IOException {
		PutObjectResult putObjectResult = getAmazonS3Client().putObject(awsConfig.getS3().getDefaultBucket(),
				fileName, convertMultiPartToFile(file));
		System.out.println(putObjectResult.getMetadata());
		return getAmazonS3Client().getUrl(awsConfig.getS3().getDefaultBucket(), fileName).toExternalForm();
	}

	@Override
	@Cacheable(value="imageServerCache", key="#root.args[0]")
	public File getFileFromUrl(String filename) throws IOException {
		System.out.println("Getting");
		File file = new File("src/main/webapp/resources/S3Images/"+filename);
		System.out.println(file.getAbsolutePath());
		S3Object o = getAmazonS3Client().getObject(awsConfig.getS3().getDefaultBucket(), filename);
		try (FileOutputStream fos = new FileOutputStream(new File(filename));
			 BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(file));) {
			InputStream s3is = o.getObjectContent();
			int i = s3is.read();
			while (i != -1) {
				bs.write(i);
				i = s3is.read();
			}
		}
		System.out.println(o.getKey());
		return file;
	}
	
	@Override
	@Cacheable(value="imageStringServerCache", key="#root.args[0]")
	public String getStreamFromUrl(String filename) throws IOException {
		System.out.println("Getting by Stream");
		S3Object o = getAmazonS3Client().getObject(awsConfig.getS3().getDefaultBucket(), filename);
		return  StreamUtils.copyToString(o.getObjectContent(), StandardCharsets.UTF_8);
	}
	
	
	@Override
	@CacheEvict(value="imageServerCache", key="#root.args[0]")
	public String deleteFileByKey(String fileName) {
		// String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		// System.out.println(fileName);
		getAmazonS3Client().deleteObject(new DeleteObjectRequest(awsConfig.getS3().getDefaultBucket(), fileName));
		return "Successfully deleted";
	}

	@Override
	public String getFileUrlByName(String fileName) {
		return getAmazonS3Client().getUrl(awsConfig.getS3().getDefaultBucket(), fileName).toExternalForm();
		
	}

}
