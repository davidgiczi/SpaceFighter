package com.david.giczi.spacefighter.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.david.giczi.spacefighter.config.Config;
import com.david.giczi.spacefighter.domain.Component;
import com.david.giczi.spacefighter.domain.SoundPlayer;
import com.david.giczi.spacefighter.service.SpaceFighterGameService;
import com.david.giczi.spacefighter.utils.ResponseType;
import com.david.giczi.spacefighter.utils.Sound;


@Controller
public class SpaceFighterGameController {

	
	private SpaceFighterGameService service;
	
	@Autowired
	public void setService(SpaceFighterGameService service) {
		this.service = service;
	}	
	
	
	@RequestMapping("/SpaceFighter/playing/ajaxRequest")
	public void ajaxResponse(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		playBackgroundSoundsAgain(request);
		
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
	public String startGame() {
		
		service.playSoundsOneAfterAnother(Sound.COUNTDOWN);
	
		return "startboard";
	}
	
	@RequestMapping("/SpaceFighter/playing")
	public String initGame(HttpServletRequest request, Model model) {
		
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
		SoundPlayer flyingNoiseplayer = service.playSoundsOneAfterAnother(Sound.FLYING_NOISE);
		request.getSession().setAttribute("flyingNoisePlayer", flyingNoiseplayer);
		SoundPlayer inSpaceNoiseplayer = service.playSoundsOneAfterAnother(Sound.IN_SPACE);
		request.getSession().setAttribute("inSpaceNoisePlayer", inSpaceNoiseplayer);
		
		return "gameboard";
	}
	
	
	@RequestMapping("/SpaceFighter/playing/theEnd")
	public String finishTheGame(@RequestParam String result, Model model)  {
		
		model.addAttribute("result", result);
		
		return "finishboard";
	}
	
	
	private void comingMeteor(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		@SuppressWarnings("unchecked")
		List<Component> meteor = (List<Component>) request.getSession().getAttribute("meteor");
		Component jet = (Component) request.getSession().getAttribute("jet");
		
		if(service.isJetCollidedWithMeteor(jet, meteor)) {
			
			service.playSoundsOneAfterAnother(Sound.COLLISION);
			service.playSoundsOneAfterAnother(Sound.ALERT);
			
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
	
	private void playBackgroundSoundsAgain(HttpServletRequest request) {
		
		SoundPlayer flyingNoiseplayer = (SoundPlayer) request.getSession().getAttribute("flyingNoisePlayer");
		SoundPlayer inSpaceNoisePlayer = (SoundPlayer) request.getSession().getAttribute("inSpaceNoisePlayer");
			
		if(!flyingNoiseplayer.isAlive()) {
				flyingNoiseplayer = service.playSoundsOneAfterAnother(Sound.FLYING_NOISE);
				request.getSession().setAttribute("flyingNoisePlayer", flyingNoiseplayer);
			}
				 
		if(!inSpaceNoisePlayer.isAlive()) {
				inSpaceNoisePlayer = service.playSoundsOneAfterAnother(Sound.IN_SPACE);
				request.getSession().setAttribute("inSpaceNoisePlayer", inSpaceNoisePlayer);
			}
			
		}
	
	
		
	}
	
	
	

