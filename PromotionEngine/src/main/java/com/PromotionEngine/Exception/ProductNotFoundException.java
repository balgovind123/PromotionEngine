package com.PromotionEngine.Exception;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(String exception) {

		super(exception);
		System.out.println("exeption");
	}
}
