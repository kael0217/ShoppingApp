package com.levent.pcd.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data

public class Address {
	private Integer addressId;
	private String fullName;
	private String street;
	private String streetTwo;
	private String city;
	private String state;
	private String zip;
	private String country;
	private List<User> users = new ArrayList<>();

}
