package com.papple.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseProduct {
	String email;
	int product_id;
	String seller;
	String name;
	int kg;
	int price;
	int amount;
	String img;
	
	public PurchaseProduct() {
	}

	@Override
	public String toString() {
		return "PurchaseProduct [email=" + email + ", product_id=" + product_id + ", seller=" + seller + ", name="
				+ name + ", kg=" + kg + ", price=" + price + ", amount=" + amount + ", img=" + img + "]";
	}

}
