package br.com.app.product.enuns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatusEnum {

	ATIVO("ATIVO"),
	CANCELADO("CANCELADO");
	
	private final String value;
}
