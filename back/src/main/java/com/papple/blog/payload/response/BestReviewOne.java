package com.papple.blog.payload.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BestReviewOne {
	private String email;
	private String content;
	private int score;
	private String review_img;
}
