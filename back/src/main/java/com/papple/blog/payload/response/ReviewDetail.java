package com.papple.blog.payload.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDetail {
	String nickname;
	int product_id;
	int score;
	String content;
	LocalDateTime created_date;
	String path;
	String name;
	String img;
}
