package com.papple.blog.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Review {
	String email;
	int product_id;
	int score;
	String content;
	LocalDateTime created_date;
	String path;
	
	public Review() {
	}

	@Override
	public String toString() {
		return "ReviewController [email=" + email + ", product_id=" + product_id + ", score=" + score + ", content="
				+ content + ", created_date=" + created_date + ", path=" + path + "]";
	}
	
}
