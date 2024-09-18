package br.com.app.product.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.app.product.entity.ProductEntity;
import br.com.app.product.enuns.ProductStatusEnum;
import br.com.app.product.exception.ProductNotFoundException;
import br.com.app.product.model.ProductRequestModel;
import br.com.app.product.model.ProductResponseModel;
import br.com.app.product.repository.ProductRepository;
import br.com.app.product.testdata.ProductEntityTestData;
import br.com.app.product.testdata.ProductRequestModelTestData;

@SpringBootTest
class ProductServiceTest {
	
	@Mock
	ProductRepository productRepository;
	
	@InjectMocks
	ProductService productService;
	
	@Test
	void findAll() {
		when(productRepository.findAll()).thenReturn(List.of(ProductEntityTestData.getProductEntity()));
		
		List<ProductResponseModel> actual = productService.findAll();
		
		verify(productRepository).findAll();
		
		assertFalse(actual.isEmpty());
	}
	
	@Test
	void findAll_returnEmpty() {
		when(productRepository.findAll()).thenReturn(new ArrayList<>());
		
		List<ProductResponseModel> actual = productService.findAll();
		
		verify(productRepository).findAll();
		
		assertTrue(actual.isEmpty());
	}

	@Test
	void searchById() {
		Long id = 1234L;
		when(productRepository.findById(id)).thenReturn(Optional.of(ProductEntityTestData.getProductEntity()));
		
		ProductResponseModel actual = productService.searchById(id);
		
		verify(productRepository).findById(id);

		assertNotNull(actual);
	}
	
	@Test
	void searchById_throwProductNotFoundException() {
		Long id = 9999L;
		when(productRepository.findById(id)).thenReturn(Optional.empty());
		
		assertThrows(ProductNotFoundException.class, () -> productService.searchById(id)); 
		
		verify(productRepository).findById(id);
	}

	@Test
	void insert() {
		ProductEntity mockEntity = ProductEntityTestData.getProductEntity();
		mockEntity.setId(null);
		mockEntity.setDateCreated(null);
		mockEntity.setDateUpdated(null);
		
		ProductEntity expected = ProductEntityTestData.getProductEntity();
		when(productRepository.save(mockEntity)).thenReturn(expected);
		
		ProductRequestModel request = ProductRequestModelTestData.getProductRequestModel();
		
		ProductResponseModel actual = productService.insert(request);
		
		verify(productRepository).save(mockEntity);
		
		assertNotNull(actual);
	}

	@Test
	void update() {
		Long id = 1234L;
		
		ProductEntity expected = ProductEntityTestData.getProductEntity();
		expected.setDescription("Produto 2");
		expected.setStatus(ProductStatusEnum.CANCELADO);
		

		when(productRepository.findById(id)).thenReturn(Optional.of(ProductEntityTestData.getProductEntity()));
		when(productRepository.save(expected)).thenReturn(expected);
		
		ProductRequestModel request = ProductRequestModelTestData.getProductRequestModel();
		request.setDescription("Produto 2");
		request.setStatus(ProductStatusEnum.CANCELADO);
		ProductResponseModel actual = productService.update(id, request);

		verify(productRepository).findById(id);
		verify(productRepository).save(expected);
		
		assertNotNull(actual);
	}

	@Test
	void removeById() {
		Long id = 1234L;

		when(productRepository.findById(id)).thenReturn(Optional.of(ProductEntityTestData.getProductEntity()));
		doNothing().when(productRepository).deleteById(id);
		
		productService.removeById(id);
		
		verify(productRepository).findById(id);
		verify(productRepository).deleteById(id);
		
	}

}
