package main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel{
	final static int ORIGINAL_TILE_SIZE = 16;
	final static int SCALE = 3;
	final static int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;//48
	final static int MAX_SCREEN_COL = 16;
	final static int MAX_SCREEN_ROW = 12;
	final static int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; //768
	final static int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;//576
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);//drawing done in an offscreen buffer
		
	}
	
}
