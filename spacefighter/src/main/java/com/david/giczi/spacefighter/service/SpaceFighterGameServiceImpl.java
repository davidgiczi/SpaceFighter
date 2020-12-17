package com.david.giczi.spacefighter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.david.giczi.spacefighter.config.Config;
import com.david.giczi.spacefighter.config.GameParam;
import com.david.giczi.spacefighter.domain.Component;
import com.david.giczi.spacefighter.utils.ComponentColor;
import com.david.giczi.spacefighter.utils.ResponseType;

@Service
public class SpaceFighterGameServiceImpl implements SpaceFighterGameService {

	
	private GameParam param;
	
	
	@Autowired
	public void setParam(GameParam param) {
		this.param = param;
	}



	@Override
	public void initGame() {
		
		Config.BOARD_ROWS = param.getBoard_rows();
		Config.BOARD_COLS = param.getBoard_cols();
		Config.JET_VALUE = param.getJet_value();
	}



	@Override
	public List<Component> createGameBoard(int meteorIndex) {
		
		List<Component> gameBoard = new ArrayList<>();
		
		for(int x = 0; x < Config.BOARD_ROWS; x++) {
			for(int y = 0; y < Config.BOARD_COLS; y++) {
				
				Component component = new Component(x * Config.BOARD_COLS + y);
				
				if(component.getLogicBoardIndex() == meteorIndex) {
					component.setColor(ComponentColor.METEOR);
				}
				if(component.getLogicBoardIndex() == Config.JET_VALUE) {
					component.setColor(ComponentColor.JET);
				}
				
				gameBoard.add(component);
			}
		}
		
		return gameBoard;
	}



	@Override
	public Component goJetLeft(Component jet) {
		
		int x = jet.getViewBoard_x();
		int y = jet.getViewBoard_y();
		
		if(jet.getLogicBoardIndex()  > x * Config.BOARD_COLS && 
				jet.getLogicBoardIndex() <= (x + 1) * Config.BOARD_COLS - 1) {
			
			jet.setViewBoardCoords(x, y - 1);
		}
			
		return jet;
	}



	@Override
	public Component goJetRight(Component jet) {
		
		int x = jet.getViewBoard_x();
		int y = jet.getViewBoard_y();
		
		if(jet.getLogicBoardIndex()  >= x * Config.BOARD_COLS && 
				jet.getLogicBoardIndex() < (x + 1) * Config.BOARD_COLS - 1) {
			
			jet.setViewBoardCoords(x, y + 1);
		}
				
		return jet;
	}
	

	@Override
	public List<Component> growMeteor(List<Component> meteor) {
		
		List<Component> meteorStore = new ArrayList<>(meteor);
		
		int meteor_x = meteor.get(0).getViewBoard_x();
		int meteor_y = -1;
		
		while(meteor_y == -1) {
			
			meteor_y = (int) (Math.random() * Config.BOARD_COLS);
			
			Component meteor_pcs = new Component(meteor_x, meteor_y);
			
			if( !meteorStore.contains(meteor_pcs) ) {
				meteorStore.add(meteor_pcs);
			}
			else {
				meteor_y = -1;
			}
			
		}
		
		return comingMeteor(meteorStore);
	}
	
	@Override
	public List<Component> comingMeteor(List<Component> meteor){
		
		meteor.forEach(c -> c.setViewBoardCoords(c.getViewBoard_x() + 1, c.getViewBoard_y()));
		
		return meteor;
	}
	
	

	@Override
	public String createResponseString(Component jet, List<Component> meteor,  ResponseType type) {
		
		StringBuilder resp = new StringBuilder();
		
		switch(type) {
		case JET_LEFT:
		resp.append("jet_")
			.append(jet.getLogicBoardIndex() + 1)
			.append("_").append(jet.getLogicBoardIndex());
		break;
		case JET_RIGHT:
		resp.append("jet_")
			.append(jet.getLogicBoardIndex() - 1).append("_")
			.append(jet.getLogicBoardIndex());
		break;
		case METEOR:
		resp.append("meteor_");
		meteor.forEach(c -> resp.append(c.getLogicBoardIndex()).append("_"));
		resp.deleteCharAt(resp.toString().length() - 1);
		break;
		default:
			
		}
			
		return resp.toString();
	}

	
	
	
}
