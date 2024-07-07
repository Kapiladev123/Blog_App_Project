package com.blog.payloads;

import java.util.List;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

	private int categoryId;
	@NotEmpty
	@Min(4)
	private String categoryTitle;
	@NotEmpty
	private String categoryDescription;
	
	
}
