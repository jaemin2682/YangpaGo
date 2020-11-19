package com.papple.blog.payload.response;

import java.util.List;

import com.papple.blog.models.Recipe_Ingredient;
import com.papple.blog.models.Recipe_Step;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeHome {
	List<RecipeInfo> recommend_recipe;
	List<RecipeInfo> popular_recipe;
}
