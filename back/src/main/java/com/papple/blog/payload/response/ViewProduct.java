package com.papple.blog.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewProduct {
	String email;
	int product_id;
	LocalDateTime view_date;
	String img;
	String name;
	
	public ViewProduct() {
	}

	@Override
	public String toString() {
		return "ViewProduct [email=" + email + ", product_id=" + product_id + ", view_date=" + view_date + ", img="
				+ img + ", name=" + name + "]";
	}

}
