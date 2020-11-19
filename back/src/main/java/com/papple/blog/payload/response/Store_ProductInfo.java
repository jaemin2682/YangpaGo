package com.papple.blog.payload.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Store_ProductInfo {
	private int id;
	private String name;
	private String img;
	private int price;
	private int kg;
}
