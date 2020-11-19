package com.papple.blog.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Recipe {
	private int id;
	private String name;
	private String picture;
	private String type;
	private int view;
	private int store;
}
