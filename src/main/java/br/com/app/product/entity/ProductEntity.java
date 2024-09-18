package br.com.app.product.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.app.product.enuns.ProductStatusEnum;
import lombok.Data;

@Data
@Entity
@Table(name = "Product")
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProductId")
	private Long id;
	
	@Column(name = "ProductDescription")
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ProductStatus")
	private ProductStatusEnum status;

	@CreationTimestamp
	@Column(name = "ProductDateCreated")
	private LocalDateTime dateCreated;

	@UpdateTimestamp
	@Column(name = "ProductDateUpdated")
	private LocalDateTime dateUpdated;
}
