package com.papple.blog.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeView {
	String email;
	int recipe_id;
	LocalDateTime view_date;
	String name;
	String picture;
	
	public RecipeView() {
		super();
	}

	@Override
	public String toString() {
		return "RecipeView [email=" + email + ", recipe_id=" + recipe_id + ", view_date=" + view_date + ", name=" + name
				+ ", picture=" + picture + "]";
	}
}
