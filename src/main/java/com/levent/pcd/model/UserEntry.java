package com.levent.pcd.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Component
@Scope(scopeName = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
@ToString
public class UserEntry implements Serializable{
	

	private static final long serialVersionUID = 8226676716937932576L;
	private UserInfo user;
	public boolean isLogin=false;
}
