package com.papple.blog.payload.response;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreHome {
	private List<Store_ProductInfo> cheapList;
	private List<BestReview> reviewList;
	private List<Store_ProductInfo> popularList;
}
