package com.papple.blog.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductList {
	int id;
	String name;
	int price;
	String img;
	int kg;
	
	public ProductList() {
		super();
	}

	@Override
	public String toString() {
		return "ProductList [id=" + id + ", name=" + name + ", price=" + price + ", img=" + img + "]";
	}

}
