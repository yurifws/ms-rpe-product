package br.com.app.product.controller;

import static br.com.app.product.constants.RestConstants.PATH_VARIABLE_ID;
import static br.com.app.product.constants.RestConstants.PATH_PRODUCTS;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.app.product.model.ProductRequestModel;
import br.com.app.product.model.ProductResponseModel;
import br.com.app.product.service.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = PATH_PRODUCTS, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<ProductResponseModel>> findAll() {
		return ResponseEntity.ok(productService.findAll());
	}
	
	@GetMapping(PATH_VARIABLE_ID)
	public ResponseEntity<ProductResponseModel> searchById(@PathVariable Long id) {
		return ResponseEntity.ok(productService.searchById(id));
	}

	@PostMapping
	public ResponseEntity<ProductResponseModel> insert(
			@RequestBody ProductRequestModel productRequestModel) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(productService.insert(productRequestModel));
	}

	@PutMapping(PATH_VARIABLE_ID)
	public ResponseEntity<ProductResponseModel> update(@PathVariable Long id, 
			@RequestBody ProductRequestModel productRequestModel) {
		return ResponseEntity.ok(productService.update(id, productRequestModel));
	}

	@DeleteMapping(PATH_VARIABLE_ID)
	public ResponseEntity<Void> removeById(@PathVariable Long id) {
		productService.removeById(id);
		return ResponseEntity.noContent().build();
	}

}
