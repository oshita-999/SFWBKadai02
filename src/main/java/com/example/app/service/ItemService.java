package com.example.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.app.domain.Item;

@Service
public class ItemService {
	
	private Map<Integer, Item> itemMap;
	
	public ItemService() {
		itemMap = new HashMap<>();
		itemMap.put(1, new Item(1,"和風レターセット", 780, 0));
		itemMap.put(2, new Item(2,"毛筆ペン", 280, 0));
		itemMap.put(3, new Item(3,"簡単万年筆", 480, 0));
		itemMap.put(4, new Item(4,"AAAA", 580, 0));
		itemMap.put(5, new Item(5,"BBBB", 1280, 0));
		itemMap.put(6, new Item(6,"CCCC", 3900, 0));
	}
	
	public Map<Integer, Item> getAllItems() {
		return itemMap;
	}
	
	public Item getItemById(int id) {
		return itemMap.get(id);
	}
	
	public List<Item> getItemsByIds(Integer id, List<Item> cartItems) {
		List<Item> itemList = new ArrayList<>();
		for(Item cartItem : cartItems) {
			Item item = itemMap.get(cartItem.getId());
			if(item != null) {
				itemList.add(item);
			}
		}
		return itemList;
	}

}
