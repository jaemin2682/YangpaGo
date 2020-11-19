package com.papple.blog.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseProductList {
	int id;
	String name;
	int price;
	String img;
	int kg;
	String store_name;
	int amount;
	int isReview;
	public PurchaseProductList(int id, String name, int price, String img, int kg, String store_name, int amount) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.img = img;
		this.kg = kg;
		this.store_name = store_name;
		this.amount = amount;
	}
}
