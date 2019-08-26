package com.levent.pcd.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.levent.pcd.config.AWSConfig;
import com.levent.pcd.model.Product;
import com.levent.pcd.repository.ProductRepository;

@Component

public class UploadImageToS3Once {

	@Autowired
	AWSS3Helper helper;
	@Autowired
	ProductRepository rep;

	@Autowired
	AWSConfig awsConfig;

	public void doUpload() throws AmazonServiceException, SdkClientException, URISyntaxException, IOException {
		AmazonS3 client = helper.getAmazonS3Client();
		File file = new File("C:/Users/payal/products.json");
		ObjectMapper mapper = new ObjectMapper();
		Product[] products = mapper.readValue(file, Product[].class);
		for (Product product : products) {
			try {
				String url = product.getImageUrl();
				URL url1 = new URL(url);
				BufferedImage img = ImageIO.read(url1);
				String filename = url.substring(url.lastIndexOf("/") + 1);
				File file1 = new File(filename);
				ImageIO.write(img, "jpg", file1);
				client.putObject(awsConfig.getS3().getDefaultBucket(), filename, file);
				product.setImageFileName(filename);
				product.setImageUrl(awsConfig.getS3().getEndPoint()+"/"+ filename);
				rep.save(product);
				System.out.println(product.getId()+":"+ product.getImageUrl());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
