package com.levent.pcd.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.levent.pcd.config.AWSConfig;

@Component
public class AWSLogin {
	
	@Value("${amazon.ses.region}")
	private String region;
	@Autowired
	AWSConfig awsConfig;
	
	private Credentials sessionCredentials;
	
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
	
	public AmazonSimpleEmailService getAmazonSESClient() {
		BasicSessionCredentials basicSessionCredentials = getBasicSessionCredentials();
		return AmazonSimpleEmailServiceClientBuilder.standard()
			// Replace US_WEST_2 with the AWS Region you're using for
			// Amazon SES.
			.withRegion(Regions.valueOf(region)).withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials)).build();
	
	}
}