package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	Tile[] tile;
	Integer[][] tileMap;
	int mapTileNum[][];
	Random random = new Random();
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[25];
		getTileImage();
		//this is to generate a random tile map
		tileMap = new Integer[gp.MAX_SCREEN_COL][gp.MAX_SCREEN_ROW];//used this for the random
		 generateTileMap();
		 //map from text file
		 mapTileNum =  new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
		 loadMap("/maps/world01.txt");
		
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
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
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
		
		while(col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
			//can use tileMap[col][row] for the random
			int tileNum = mapTileNum[col][row];
			
			g2.drawImage(tile[tileNum].image, x, y, gp.TILE_SIZE,gp.TILE_SIZE,null);
			//places grass tiles on whole screen
			col++;
			x += gp.TILE_SIZE;
			
			if(col == gp.MAX_WORLD_COL) {
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
	
	//load a map from a text file
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col = 0;
			int row = 0;
			while(col <gp.MAX_WORLD_COL && row <gp.MAX_WORLD_ROW) {
				String line  = br.readLine();
				while(col < gp.MAX_WORLD_COL) {
					String numbers[] = line.split(" ");//from the line splitting it into individual numbers putting into this array
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.MAX_WORLD_COL) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
