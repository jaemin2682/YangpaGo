package com.papple.blog.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Recipe_Step {
	int recipe_id;
	int step;
	String content;
}
