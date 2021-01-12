package com.david.giczi.spacefighter.domain;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class SoundPlayer extends Thread {
	
	
	private List<String> mp3FilePathStore;
	
	public SoundPlayer(List<String> mp3FilePathStore) {
		
		this.mp3FilePathStore = mp3FilePathStore;
	}
	

	@Override
	public void run() {
		
		mp3FilePathStore.forEach(path -> play(path));
		
	}

	private synchronized void play(String mp3FilePath) {
		
		try (FileInputStream fileInputstream = new FileInputStream(mp3FilePath);){
			Player player = new Player(fileInputstream);
			player.play();
			
			
		} catch (IOException | JavaLayerException e) {
			
			e.printStackTrace();
		}
		
	}
	

}
