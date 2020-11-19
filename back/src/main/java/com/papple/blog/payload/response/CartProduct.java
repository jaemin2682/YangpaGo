package com.papple.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartProduct {
	String email;
	int product_id;
	String seller;
	String name;
	int kg;
	int price;
	int amount;
	String img;
	String store_name;
	
	public CartProduct() {
	}

	@Override
	public String toString() {
		return "CartProduct [email=" + email + ", product_id=" + product_id + ", seller=" + seller + ", name=" + name
				+ ", kg=" + kg + ", price=" + price + ", amount=" + amount + ", img=" + img + ", store_name="
				+ store_name + "]";
	}

}
