package com.papple.blog.payload.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderList {
	int orderCountAll;
	int reviewCount;
	int reviewNotCount;
	List<PurchaseProductList> purchaseList;
}
