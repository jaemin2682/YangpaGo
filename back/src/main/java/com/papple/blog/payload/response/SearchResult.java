package com.papple.blog.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResult {
	private List<StoreList> storeList;
	private List<ProductList> productList;
}
