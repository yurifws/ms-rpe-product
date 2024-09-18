package br.com.app.product.model;

import br.com.app.product.enuns.ProductStatusEnum;
import lombok.Data;

@Data
public class ProductRequestModel {
	
	private String description;
	
	private ProductStatusEnum status;
}
