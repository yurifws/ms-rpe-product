package br.com.app.product.exception;

public abstract class EntityNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1768192136329341938L;

	public EntityNotFoundException(String message){
		super(message);
	}
}
