package com.levent.pcd.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.levent.pcd.service.AWSS3Helper;

public class S3Controller {
	
	// AWS
	@Autowired
	@Qualifier("AWSS3HelperImpl")
	private AWSS3Helper awshelper;
	
	@GetMapping("/addProductWithS3")
	public String testAddFile() {
		return "NMDWSM";
	}

//	@PreAuthorize("hasRole('ADMIN')")

//	@InitBinder
//	public void fileBinder(WebDataBinder binder) {
//		binder.setDisallowedFields("file");
//	}

	@GetMapping("/getImageByKey/{fileName}")
	public String getImageByKey(@PathVariable String fileName) throws FileNotFoundException, IOException {
		System.out.println(awshelper.getFileUrlByName(fileName));
		return awshelper.getFileFromUrl(fileName).getPath();
	}

	@GetMapping("/getImageStringByKey/{fileName}")
	public String getImageStringByKey(@PathVariable String fileName) throws FileNotFoundException, IOException {
		String image = awshelper.getStreamFromUrl("01_men_one.jpg");
		return image;
	}

	@DeleteMapping("/deleteFileByKey/{fileName}")
	@ResponseStatus(code = HttpStatus.OK)
	public void deleteFileByKey(@PathVariable String fileName) {
		System.out.println(fileName);
		System.out.println(awshelper.deleteFileByKey(fileName));
	}

}
