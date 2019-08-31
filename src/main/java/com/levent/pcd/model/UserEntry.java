package com.levent.pcd.model;

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
public class UserEntry {
	private UserInfo user;
	public boolean isLogin=false;
}
