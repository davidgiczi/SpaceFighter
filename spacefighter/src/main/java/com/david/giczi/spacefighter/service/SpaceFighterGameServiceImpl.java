package com.david.giczi.spacefighter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.david.giczi.spacefighter.config.Config;
import com.david.giczi.spacefighter.config.GameParam;

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
		
	}

}
