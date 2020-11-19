package com.papple.blog.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Recipe_Ingredient {
	private int recipe_id;
	private String content;
	private String amount;
}
