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

import br.com.app.product.enuns.StatusEnum;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;
	
	@Column(name = "product_description")
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "product_status")
	private StatusEnum status;

	@CreationTimestamp
	@Column(name = "product_date_created")
	private LocalDateTime dateCreated;

	@UpdateTimestamp
	@Column(name = "product_date_updated")
	private LocalDateTime dateUpdated;
}
