package com.productinfo.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrandNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BrandNotFoundException(String message) {
		super(message);
	}

}
