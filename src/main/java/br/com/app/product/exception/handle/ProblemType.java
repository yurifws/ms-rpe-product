package br.com.app.product.exception.handle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProblemType {
	INVALID_DATA("/dados-invalidos", "Dados inválidos"),
	SYSTEM_ERROR("/erro-de-sistema", "Erro de sistema"),
	INVALID_PARAMETER("/parametro-invalido", "Parâmetro inválido"),
	INCOMPREHENSIVE_MESSAGE("/mensagem-incompreensivel", "Mensagem incompreensível"),
	RESOURCE_NOT_FOUND("/recurso-nao-encontrado", "Recurso não encontrado"),
	BUSINESS_ERROR("/erro-negocio", "Violação de regra de negócio");
	
	private final String uri;
	private final String title;
}
