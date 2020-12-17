package com.david.giczi.spacefighter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "param")
public class GameParam {

	private int board_rows;
	private int board_cols;
	private int jet_value;
	
	
	public int getBoard_rows() {
		return board_rows;
	}
	
	public void setBoard_rows(int board_rows) {
		this.board_rows = board_rows;
	}

	public int getBoard_cols() {
		return board_cols;
	}
	public void setBoard_cols(int board_cols) {
		this.board_cols = board_cols;
	}

	public int getJet_value() {
		return jet_value;
	}

	public void setJet_value(int jet_value) {
		this.jet_value = jet_value;
	}
	
}
