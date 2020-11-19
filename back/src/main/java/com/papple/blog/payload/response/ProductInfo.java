package com.papple.blog.payload.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductInfo {
	private int id;
	private String img;
	private String name;
	private int price;
	private int kg;
}
