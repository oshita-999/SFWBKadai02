package com.example.app.controller;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.Item;
import com.example.app.service.ItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ItemController {

	private final ItemService service;
	private final HttpSession session;
	private Map<Integer, Integer> cart = new HashMap<>();
	
	@GetMapping("/")
	public String showItems(Model model) {
		model.addAttribute("itemList", service.getAllItems());
		return "index";
	}
	
	@GetMapping("/item")
	public String showItem(
			@RequestParam(required = false) Integer id,
			Model model) {
		if(id == null) {return "redirect:/";}
		Item item = service.getItemById(id);
		if(item == null) {return "redirect:/";}
		int unit = getUnit(id);
		model.addAttribute("item", item);
		model.addAttribute("unit", unit);
		return "item";
	}
	
	@PostMapping("/item")
	public String calcSubtotal(
			@RequestParam int id,
			@RequestParam int unit,
			Model model) {
		Item item = service.getItemById(id);
		if(session.getAttribute("cart") == null) {
			cart.put(id, unit);
			session.setAttribute("cart", cart);			
		} else {
			cart = (Map<Integer, Integer>) session.getAttribute("cart");
			cart.put(id, unit);
			session.setAttribute("cart", cart);
		}	
		model.addAttribute("item", item);
		model.addAttribute("unit", getUnit(id));
		return "item";
	}
	
	private int getUnit(int itemId) {
		cart = (Map<Integer, Integer>) session.getAttribute("cart");
		if(cart == null) {
			return 0;
		} else {
			Integer unit = cart.get(itemId);
			if(unit == null) {
				return 0;
			} else {
				return unit;
			}
		}
	}
	
}





