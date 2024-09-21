package br.com.app.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.app.product.entity.ProductEntity;
import br.com.app.product.exception.ProductNotFoundException;
import br.com.app.product.mapper.ProductMapper;
import br.com.app.product.model.ProductRequestModel;
import br.com.app.product.model.ProductResponseModel;
import br.com.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
	
	private final ProductRepository productRepository;

	@Override
	public List<ProductResponseModel> findAll() {
		List<ProductEntity> products = productRepository.findAll();
		
		if(!products.isEmpty()) {
			return ProductMapper.INSTANCE.toListResponseModel(products);
		}
		
		return new ArrayList<>();
	}

	@Override
	public ProductResponseModel searchById(Long id) {
		return ProductMapper.INSTANCE.toResponseModel(findById(id));
	}
	
	@Transactional
	@Override
	public ProductResponseModel insert(ProductRequestModel productRequestModel) {
		return ProductMapper.INSTANCE.toResponseModel(
				save(ProductMapper.INSTANCE.toEntity(productRequestModel)));
	}

	@Transactional
	@Override
	public ProductResponseModel update(Long id, ProductRequestModel productRequestModel) {
		ProductEntity productEntity = findById(id);
		BeanUtils.copyProperties(productRequestModel, productEntity, "id");
		return ProductMapper.INSTANCE.toResponseModel(save(productEntity));
	}

	@Transactional
	@Override
	public void removeById(Long id) {
		ProductEntity productEntity = findById(id);
		productRepository.deleteById(productEntity.getId());
	}

	private ProductEntity findById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	private ProductEntity save(ProductEntity productEntity) {
		return productRepository.save(productEntity);
	}

}
