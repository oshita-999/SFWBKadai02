package com.example.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.app.domain.Item;
import com.example.app.service.ItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {
	
	private final ItemService service;
	private final HttpSession session;
	
	@GetMapping
	public String showCart(Model model) {
		int total = 0;
		if(session.getAttribute("cart") != null) {
			Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
			List<Item> itemList = new ArrayList<>();
			for(Entry<Integer, Integer> idUnit : cart.entrySet()) {
				Item item = service.getItemById(idUnit.getKey());
				item.setUnit(idUnit.getValue());
				itemList.add(item);
				total += item.getPrice() * idUnit.getValue();
			}
			model.addAttribute("itemList", itemList);
			model.addAttribute("total", total);
		} else {
			return "redirect:/item";
		}
		return "cart";
	}
	
	@PostMapping
	public String proceedPayment() {
		session.invalidate();
		return "redirect:/cart/done";
	}
	
	@GetMapping("/done")
	public String showCompleted() {
		return "cartDone";
	}
	
	// 小計のマップから合計を算出
	private int getTotal(Map<Integer, Integer> subtotalMap) {
		int total = 0;
		for(Entry<Integer, Integer> entry : subtotalMap.entrySet()) {
			total += entry.getValue();
		}
		return total;
	}
	
	// 小計のマップを生成
	private Map<Integer, Integer> getSubtotalMap() {
		Map<Integer, Integer> subtotalMap = new HashMap<>();
		for(int id = 1; id <= 3; id++) {
			subtotalMap.put(id, getSubtotal(id));
		}
		return subtotalMap;
	}
	
	// セッションから購入個数を取得し、小計を計算
	private int getSubtotal(int itemId) {
		Item item = service.getItemById(itemId);
		Integer unit = (Integer) session.getAttribute("item" + itemId);
		unit = unit != null ? unit : 0;
		return item.getPrice() * unit;
	}
	
}