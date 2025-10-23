package com.example.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Item {
	private Integer id;
	private String name;
	private int price;
	private Integer unit;

}
