package com.papple.blog.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLike {
	int user_id;
	int product_id;
	String img;
	
	public UserLike() {
		super();
	}

	@Override
	public String toString() {
		return "UserLike [user_id=" + user_id + ", product_id=" + product_id + ", img=" + img + "]";
	}
	
}
