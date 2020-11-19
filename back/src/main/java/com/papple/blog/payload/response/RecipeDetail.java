package com.papple.blog.payload.response;

import java.util.List;

import com.papple.blog.models.Recipe_Ingredient;
import com.papple.blog.models.Recipe_Step;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RecipeDetail {
	private int id;
	private String name;
	private String picture;
	private String type;
	private int isGood;
	private List<Recipe_Ingredient> ingredient;
	private List<Recipe_Step> step;
	private List<RecipeInfo> relative_product;
	private List<RecipeInfo> recommend_recipe;
	
	public RecipeDetail(int id, String name, String picture, String type) {
		this.id = id; this.name = name; this.picture = picture; this.type = type;
	}
	
}
