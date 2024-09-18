package br.com.app.product.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.app.product.entity.ProductEntity;
import br.com.app.product.model.ProductRequestModel;
import br.com.app.product.model.ProductResponseModel;
import br.com.app.product.testdata.ProductEntityTestData;
import br.com.app.product.testdata.ProductRequestModelTestData;

@SpringBootTest
class ProductMapperTest {
	
	@Test
	void toEntity() {
		ProductRequestModel expected = ProductRequestModelTestData.getProductRequestModel();
		ProductEntity actual = ProductMapper.INSTANCE.toEntity(expected);
		
		assertNotNull(actual);
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getStatus(), actual.getStatus());
	}
	
	@Test
	void toResponseModel() {
		ProductEntity expected = ProductEntityTestData.getProductEntity();
		ProductResponseModel actual = ProductMapper.INSTANCE.toResponseModel(expected);
		
		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getStatus(), actual.getStatus());
		assertEquals(expected.getDateCreated(), actual.getDateCreated());
		assertEquals(expected.getDateUpdated(), actual.getDateUpdated());
	}

	@Test
	void toListResponseModel() {
		
		ProductEntity expected = ProductEntityTestData.getProductEntity();
		List<ProductResponseModel> response = ProductMapper.INSTANCE.toListResponseModel(List.of(expected));
		
		assertFalse(response.isEmpty());
		
		ProductResponseModel actual = response.get(0);

		assertNotNull(actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getStatus(), actual.getStatus());
		assertEquals(expected.getDateCreated(), actual.getDateCreated());
		assertEquals(expected.getDateUpdated(), actual.getDateUpdated());
	}
}
