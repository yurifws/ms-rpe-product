package br.com.app.product.exception;

public class ProductNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 2299525454853110671L;
	
	private static final String MSG_PRODUCT_NOT_FOUND = "Não existe um cadastro de produto com código %d";

	public ProductNotFoundException(String mensagem) {
		super(mensagem);
	}

	public ProductNotFoundException(Long productId) {
		this(String.format(MSG_PRODUCT_NOT_FOUND, productId));
	}
}
