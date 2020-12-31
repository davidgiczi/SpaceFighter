package com.david.giczi.spacefighter.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.david.giczi.spacefighter.config.Config;
import com.david.giczi.spacefighter.domain.Component;
import com.david.giczi.spacefighter.service.SpaceFighterGameService;
import com.david.giczi.spacefighter.utils.ResponseType;

@Controller
public class SpaceFighterGameController {

	
	private SpaceFighterGameService service;
	
	
	@Autowired
	public void setService(SpaceFighterGameService service) {
		this.service = service;
	}
	
	@RequestMapping("/SpaceFighter/ajaxRequest")
	public void ajaxResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String userRequest = request.getParameter("usereq");
		
		switch (userRequest) {
		
		case "comingMeteor":
			comingMeteor(request, response);
			break;
		case "goJetLeft":
			goJetLeft(request, response);
			break;
		case "goJetRight":
			goJetRight(request, response);
			break;

		default:
		}

	}
	
	
	
	@RequestMapping("/SpaceFighter")
	public String initGame(Model model, HttpServletRequest request) {
		
		request.getSession().invalidate();
		service.initGame();
		model.addAttribute("board_rows", Config.BOARD_ROWS);
		model.addAttribute("board_cols", Config.BOARD_COLS);
		model.addAttribute("jet_position", Config.JET_POSITION);
		int meteorIndex = (int)(Math.random() * Config.BOARD_ROWS);
		model.addAttribute("board", service.createGameBoard(meteorIndex));
		model.addAttribute("meteorIndex", meteorIndex);
		request.getSession().setAttribute("meteor", Arrays.asList(new Component(meteorIndex)));
		request.getSession().setAttribute("jet",  new Component(Config.JET_POSITION));
		
		return "gameboard";
	}
	
	
	private void comingMeteor(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		@SuppressWarnings("unchecked")
		List<Component> meteor = (List<Component>) request.getSession().getAttribute("meteor");
		Component jet = (Component) request.getSession().getAttribute("jet");
		
		if(service.isJetCollidedWithMeteor(jet, meteor)) {

			response.getWriter().append("collision");
		}
		else if(meteor.size() < Config.BOARD_ROWS - 2) {
			meteor = service.growMeteor(meteor);	
		}
		else if(meteor.size() == Config.BOARD_ROWS) {
			meteor = createMeteor(meteor);
		}
		else {
			meteor = service.comingMeteor(meteor);
			meteor.add(new Component(-1));
		}
		
		
		response.getWriter().append(service.createResponseString(null, meteor, ResponseType.METEOR));
		
		request.getSession().setAttribute("meteor", meteor);
	}
	
	private void goJetLeft(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Component jet = (Component) request.getSession().getAttribute("jet");
		jet = service.goJetLeft(jet);
		response.getWriter().append(service.createResponseString(jet, null,  ResponseType.JET_LEFT));
		request.getSession().setAttribute("jet",  jet);
		
	}
	
	private void goJetRight(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Component jet = (Component) request.getSession().getAttribute("jet");
		jet = service.goJetRight(jet);
		response.getWriter().append(service.createResponseString(jet, null,  ResponseType.JET_RIGHT));
		request.getSession().setAttribute("jet",  jet);
		
	}
	
	private List<Component> createMeteor(List<Component> meteor){
		meteor.clear();
		int meteorIndex = (int)(Math.random() * Config.BOARD_ROWS);
		return Arrays.asList(new Component(meteorIndex));
	}
}
