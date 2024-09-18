package br.com.app.product.exception.handle;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(value = Include.NON_NULL)
@Getter
@Builder
public class Problem {
	
	private Integer status;
	
	private LocalDateTime datetime;
	
	private String type;
	
	private String title;
	
	private String message;
	
	private List<Object> objects;
	
	@Getter
	@Builder
	public static class Object {

		private String name;
		
		private String message;
	}

}
