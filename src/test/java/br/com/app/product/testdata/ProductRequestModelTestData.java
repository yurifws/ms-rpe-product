package br.com.app.product.testdata;

import br.com.app.product.enuns.ProductStatusEnum;
import br.com.app.product.model.ProductRequestModel;

public class ProductRequestModelTestData {
	
	public static ProductRequestModel getProductRequestModel() {
		ProductRequestModel model = new ProductRequestModel();
		model.setStatus(ProductStatusEnum.ATIVO);
		model.setDescription("Produto 1");
		return model;
	}

}
