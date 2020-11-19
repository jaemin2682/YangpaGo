package com.papple.blog.payload.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BestReview {
	private int id;
	private String product_img;
	private String name;
	private int price;
	private int kg;
	private int score;
	private String content;
	private String review_img;
}
