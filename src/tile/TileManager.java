package tile;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	Tile[] tile;
	Integer[][] tileMap;
	Random random = new Random();
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[25];
		getTileImage();
		tileMap = new Integer[gp.MAX_SCREEN_COL][gp.MAX_SCREEN_ROW];
		 generateTileMap();
		
	}
	
	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass01.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water01.png"));
			//can load more images when needed
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		//ineffiecient way of drawing
//		g2.drawImage(tile[0].image,0,0,gp.TILE_SIZE,gp.TILE_SIZE,null);
//		g2.drawImage(tile[1].image,48,0,gp.TILE_SIZE,gp.TILE_SIZE,null);
//		g2.drawImage(tile[2].image,96,0,gp.TILE_SIZE,gp.TILE_SIZE,null);
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < gp.MAX_SCREEN_COL && row < gp.MAX_SCREEN_ROW) {
			int tileNum = tileMap[col][row];
			
			g2.drawImage(tile[tileNum].image, x, y, gp.TILE_SIZE,gp.TILE_SIZE,null);
			//places grass tiles on whole screen
			col++;
			x += gp.TILE_SIZE;
			
			if(col == gp.MAX_SCREEN_COL) {
				col = 0;
				x = 0;
				row++;
				y += gp.TILE_SIZE;
			}
		}
	}
	
	public Integer[][] generateTileMap(){
		
		int col = 0;
		int row = 0;
		
		int max = 2; //should be set to the number of tiles you have
		int min = 0;
		int range = max - min + 1;
		
		while(col < gp.MAX_SCREEN_COL && row < gp.MAX_SCREEN_ROW) {
			//generate a random Integer and then assign it to the tilemap need to ensure its in the range of tiles available
			int tileNum = (int)(Math.random() * range) + min;
			tileMap[col][row] = tileNum;
			col++;
			if(col == gp.MAX_SCREEN_COL) {
				col = 0;
				row++;
			}
		}
		return tileMap;
		
	}
}
