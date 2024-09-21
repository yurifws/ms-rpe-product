package br.com.app.product.controller;

import static br.com.app.product.constants.RestConstants.PATH_PRODUCTS;
import static br.com.app.product.constants.RestConstants.PATH_VARIABLE_ID;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.app.product.enuns.StatusEnum;
import br.com.app.product.model.ProductRequestModel;
import br.com.app.product.model.ProductResponseModel;
import br.com.app.product.service.IProductService;
import br.com.app.product.testdata.ProductRequestModelTestData;
import br.com.app.product.testdata.ProductResponseModelTestData;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IProductService productService;
	
	@Test
	void findAll() throws Exception {
		List<ProductResponseModel> expected = List.of(ProductResponseModelTestData.getProductResponseModel());
		
		when(productService.findAll()).thenReturn(expected);
		
		mockMvc.perform(MockMvcRequestBuilders.get(PATH_PRODUCTS)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id").value(expected.get(0).getId()))
		.andExpect(jsonPath("$[0].description").value(expected.get(0).getDescription()))
		.andExpect(jsonPath("$[0].status").value(expected.get(0).getStatus().name()))
		.andExpect(jsonPath("$[0].dateCreated").value(expected.get(0).getDateCreated().toString()))
		.andExpect(jsonPath("$[0].dateUpdated").value(expected.get(0).getDateUpdated().toString()));

		verify(productService).findAll();
	}
	
	@Test
	void searchById() throws Exception {

		Long id = 1234L;
		ProductResponseModel expected = ProductResponseModelTestData.getProductResponseModel();
		
		when(productService.searchById(id)).thenReturn(expected);
		
		mockMvc.perform(MockMvcRequestBuilders.get(PATH_PRODUCTS + PATH_VARIABLE_ID, id)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(expected.getId()))
		.andExpect(jsonPath("$.description").value(expected.getDescription()))
		.andExpect(jsonPath("$.status").value(expected.getStatus().name()))
		.andExpect(jsonPath("$.dateCreated").value(expected.getDateCreated().toString()))
		.andExpect(jsonPath("$.dateUpdated").value(expected.getDateUpdated().toString()));
		
		verify(productService).searchById(id);
	}
	
	@Test
	void insert() throws Exception {

		ProductRequestModel request = ProductRequestModelTestData.getProductRequestModel();
		ProductResponseModel expected = ProductResponseModelTestData.getProductResponseModel();
		when(productService.insert(request)).thenReturn(expected);
		
		String body = """
				{
					"description": "Produto 1",
					"status": "ATIVO"
				}
				""";
		
		mockMvc.perform(MockMvcRequestBuilders.post(PATH_PRODUCTS)
				.content(body)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").value(expected.getId()))
		.andExpect(jsonPath("$.description").value(expected.getDescription()))
		.andExpect(jsonPath("$.status").value(expected.getStatus().name()))
		.andExpect(jsonPath("$.dateCreated").value(expected.getDateCreated().toString()))
		.andExpect(jsonPath("$.dateUpdated").value(expected.getDateUpdated().toString()));
		
		verify(productService).insert(request);
	}
	
	@Test
	void update() throws Exception {
		Long id = 1234L;
		ProductRequestModel request = ProductRequestModelTestData.getProductRequestModel();
		request.setDescription("Produto 2");
		request.setStatus(StatusEnum.CANCELADO);
		
		ProductResponseModel expected = ProductResponseModelTestData.getProductResponseModel();
		expected.setDescription("Produto 2");
		expected.setStatus(StatusEnum.CANCELADO);
		
		when(productService.update(id, request)).thenReturn(expected);
		
		String body = """
				{
					"description": "Produto 2",
					"status": "CANCELADO"
				}
				""";
		
		mockMvc.perform(MockMvcRequestBuilders.put(PATH_PRODUCTS + PATH_VARIABLE_ID, id)
				.content(body)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(expected.getId()))
		.andExpect(jsonPath("$.description").value(expected.getDescription()))
		.andExpect(jsonPath("$.status").value(expected.getStatus().name()))
		.andExpect(jsonPath("$.dateCreated").value(expected.getDateCreated().toString()))
		.andExpect(jsonPath("$.dateUpdated").value(expected.getDateUpdated().toString()));
		
		verify(productService).update(id, request);
	}
	
	@Test
	void removeById() throws Exception {

		Long id = 1234L;
		doNothing().when(productService).removeById(id);
		
		mockMvc.perform(MockMvcRequestBuilders.delete(PATH_PRODUCTS + PATH_VARIABLE_ID, id)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andDo(print())
		.andExpect(status().isNoContent());
		
		verify(productService).removeById(id);
	}
	
}
