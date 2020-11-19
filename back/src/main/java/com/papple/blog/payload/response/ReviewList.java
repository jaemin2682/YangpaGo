package com.papple.blog.payload.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ReviewList {
	private BestReviewOne bestReview;
	private List<ReviewByProductDetail> reviewList;
}
