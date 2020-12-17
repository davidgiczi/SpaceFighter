package com.david.giczi.spacefighter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.david.giczi.spacefighter.config.Config;
import com.david.giczi.spacefighter.service.SpaceFighterGameService;

@Controller
public class SpaceFighterGameController {

	
	private SpaceFighterGameService service;
	
	
	@Autowired
	public void setService(SpaceFighterGameService service) {
		this.service = service;
	}
	
	@RequestMapping("/SpaceFighter")
	public String playGame(Model model) {
		
		service.initGame();
		
		model.addAttribute("board_rows", Config.BOARD_ROWS);
		model.addAttribute("board_cols", Config.BOARD_COLS);
		
		return "gameboard";
	}
	
	
	
	
}
