package br.com.app.product.enuns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusEnum {

	ATIVO("ATIVO"),
	CANCELADO("CANCELADO");
	
	private final String value;
}
