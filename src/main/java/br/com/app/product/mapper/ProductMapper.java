package br.com.app.product.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import br.com.app.product.entity.ProductEntity;
import br.com.app.product.model.ProductRequestModel;
import br.com.app.product.model.ProductResponseModel;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface ProductMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	ProductEntity toEntity(ProductRequestModel productRequestModel);
	
	ProductResponseModel toResponseModel(ProductEntity productEntity);

	List<ProductResponseModel> toListResponseModel(List<ProductEntity> productEntities);
	
}

