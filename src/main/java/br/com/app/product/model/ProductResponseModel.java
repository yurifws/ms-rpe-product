package br.com.app.product.model;

import java.time.LocalDateTime;

import br.com.app.product.enuns.ProductStatusEnum;
import lombok.Data;

@Data
public class ProductResponseModel {
	
	private Long id;
	
	private String description;
	
	private ProductStatusEnum status;
	
	private LocalDateTime dateCreated;
	
	private LocalDateTime dateUpdated;

}
