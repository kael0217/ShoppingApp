package com.levent.pcd.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@Scope(scopeName = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserEntry {
	private User user;
	private boolean isLogin=false;
}
