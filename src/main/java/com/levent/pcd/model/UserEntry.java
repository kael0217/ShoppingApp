package com.levent.pcd.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope(scopeName = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserEntry {
	private UserInfo user;
	public boolean isLogin=false;
}
