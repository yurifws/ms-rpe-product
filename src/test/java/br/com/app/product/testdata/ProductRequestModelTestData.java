package br.com.app.product.testdata;

import br.com.app.product.enuns.StatusEnum;
import br.com.app.product.model.ProductRequestModel;

public class ProductRequestModelTestData {
	
	public static ProductRequestModel getProductRequestModel() {
		ProductRequestModel model = new ProductRequestModel();
		model.setStatus(StatusEnum.ATIVO);
		model.setDescription("Produto 1");
		return model;
	}

}
