package com.david.giczi.spacefighter.service;

import java.util.List;


import com.david.giczi.spacefighter.domain.Component;
import com.david.giczi.spacefighter.utils.ResponseType;

public interface SpaceFighterGameService {

	
	void initGame();
	List<Component> createGameBoard(int meteorIndex);
	Component goJetLeft(Component jet);
	Component goJetRight(Component jet);
	List<Component> comingMeteor(List<Component> meteor);
	List<Component> growMeteor(List<Component> meteor);
	String createResponseString(Component jet, List<Component> meteor, ResponseType type);
	boolean isJetCollidedWithMeteor(Component jet, List<Component> meteor);

}
