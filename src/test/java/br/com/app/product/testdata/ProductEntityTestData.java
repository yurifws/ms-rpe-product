package br.com.app.product.testdata;

import java.time.LocalDateTime;

import br.com.app.product.entity.ProductEntity;
import br.com.app.product.enuns.StatusEnum;

public class ProductEntityTestData {
	
	public static ProductEntity getProductEntity() {
		ProductEntity model = new ProductEntity();
		model.setId(1234L);
		model.setStatus(StatusEnum.ATIVO);
		model.setDescription("Produto 1");
		model.setDateCreated(LocalDateTime.of(2023, 11, 25, 11, 12, 13));
		model.setDateUpdated(LocalDateTime.of(2024, 12, 24, 01, 02, 03));
		return model;
	}

}
