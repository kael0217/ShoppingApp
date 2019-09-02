package com.levent.pcd.exception;

public class InventoryMismatchExcpetion extends RuntimeException {
	private static final long serialVersionUID = -532142054733098669L;

	public InventoryMismatchExcpetion(String message) {
		super(message);
	}
}
