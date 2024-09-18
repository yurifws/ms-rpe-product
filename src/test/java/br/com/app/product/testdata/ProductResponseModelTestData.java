package br.com.app.product.testdata;

import java.time.LocalDateTime;

import br.com.app.product.enuns.ProductStatusEnum;
import br.com.app.product.model.ProductResponseModel;

public class ProductResponseModelTestData {
	
	public static ProductResponseModel getProductResponseModel() {
		ProductResponseModel model = new ProductResponseModel();
		model.setId(1234L);
		model.setStatus(ProductStatusEnum.ATIVO);
		model.setDescription("Produto 1");
		model.setDateCreated(LocalDateTime.of(2023, 11, 25, 11, 12, 13));
		model.setDateUpdated(LocalDateTime.of(2024, 12, 24, 01, 02, 03));
		return model;
	}

}
