package com.papple.blog.repository;

import org.apache.ibatis.annotations.Mapper;
import com.papple.blog.models.*;
import com.papple.blog.payload.response.RecipeDetail;
import com.papple.blog.payload.response.RecipeInfo;

import java.util.*;

@Mapper
public interface RecipeRepository {
	List<RecipeInfo> searchRecipeAll(int page);
	List<RecipeInfo> searchPopularRecipe(int page);
	
	// Recipe Detail
	RecipeDetail searchRecipeBasic(int id);
	List<Recipe_Ingredient> searchRecipeIngredient(int id);
	List<Recipe_Step> searchRecipeStep(int id);
	
	// My Recipe Book
	void insertMyRecipe(int id, String email);
	void deleteMyRecipe(int id, String email);
	List<RecipeInfo> searchMyRecipe(String email);
	
	// View
	int insertRecipeView(int id, String email);
	
	List<RecipeInfo> relativeProduct(int id);
	int upScoreOne(int id);
	int upScoreThree(int id);
	List<RecipeInfo> searchPopularRecipeAll();
	List<RecipeInfo> search(String word, int page);
	int isGood(String email, int id);
	
}
