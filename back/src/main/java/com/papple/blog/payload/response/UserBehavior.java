package com.papple.blog.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserBehavior {
	int user_id;
	int product_id;
	int rating;
	
	public UserBehavior() {
		super();
	}
}
