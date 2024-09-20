package br.com.app.product.model;

import br.com.app.product.enuns.StatusEnum;
import lombok.Data;

@Data
public class ProductRequestModel {
	
	private String description;
	
	private StatusEnum status;
}
