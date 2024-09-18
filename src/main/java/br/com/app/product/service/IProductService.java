package br.com.app.product.service;

import java.util.List;

import br.com.app.product.model.ProductRequestModel;
import br.com.app.product.model.ProductResponseModel;

public interface IProductService {

	List<ProductResponseModel> findAll();
	ProductResponseModel searchById(Long id);  
	ProductResponseModel insert(ProductRequestModel productRequestModel);
	ProductResponseModel update(Long id, ProductRequestModel productRequestModel);
	void removeById(Long id);
}
 