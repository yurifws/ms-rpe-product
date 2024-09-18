package br.com.app.product.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -9222078385337616190L;

	public BusinessException(String message){
		super(message);
	}
	
	public BusinessException(String message, Throwable cause){
		super(message, cause);
	}

}
