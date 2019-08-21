package com.levent.pcd.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@Scope("session")
public class UserEntry {
	private User user;
}
