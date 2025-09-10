package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	Integer[][] tileMap;
	public int mapTileNum[][];
	Random random = new Random();
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[50];
		getTileImage();
		//this is to generate a random tile map
		tileMap = new Integer[gp.MAX_SCREEN_COL][gp.MAX_SCREEN_ROW];//used this for the random
		 generateTileMap();
		 //map from text file
		 mapTileNum =  new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
		 loadMap("/maps/worldv3.txt");
		
	}
	
	public void getTileImage() {
		//PLACEHOLDER
			setup(0,"grass00",false);
			setup(1,"grass00",false);
			setup(2,"grass00",false);
			setup(3,"grass00",false);
			setup(4,"grass00",false);
			setup(5,"grass00",false);
			setup(6,"grass00",false);
			setup(7,"grass00",false);
			setup(8,"grass00",false);
			setup(9,"grass00",false);
			setup(10,"grass00",false);
			//set collision to true for tiles you want to be able to collide with remember if you want different types keep it consistent
			//can load more images when needed
			setup(11,"grass01",false);
			
			setup(12,"water00",true);
			setup(13,"water01",true);
			setup(14,"water02",true);
			setup(15,"water03",true);
			setup(16,"water04",true);
			setup(17,"water05",true);
			setup(18,"water06",true);
			setup(19,"water07",true);
			setup(20,"water08",true);
			setup(21,"water09",true);
			setup(22,"water10",true);
			setup(23,"water11",true);
			setup(24,"water12",true);
			setup(25,"water13",true);
			
			setup(26,"road00",false);
			setup(27,"road01",false);
			setup(28,"road02",false);
			setup(29,"road03",false);
			setup(30,"road04",false);
			setup(31,"road05",false);
			setup(32,"road06",false);
			setup(33,"road07",false);
			setup(34,"road08",false);
			setup(35,"road09",false);
			setup(36,"road10",false);
			setup(37,"road11",false);
			setup(38,"road12",false);
			
			setup(39,"earth",false);
			setup(40,"wall",true);
			setup(41,"tree",true);
			
	}
	
	public void setup(int index, String imageName, boolean collision) {
		UtilityTool utilityTool = new UtilityTool();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
			tile[index].image = utilityTool.scaleImage(tile[index].image,gp.TILE_SIZE,gp.TILE_SIZE);
			tile[index].collision = collision;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		//ineffiecient way of drawing
//		g2.drawImage(tile[0].image,0,0,gp.TILE_SIZE,gp.TILE_SIZE,null);
//		g2.drawImage(tile[1].image,48,0,gp.TILE_SIZE,gp.TILE_SIZE,null);
//		g2.drawImage(tile[2].image,96,0,gp.TILE_SIZE,gp.TILE_SIZE,null);
		
		int worldCol = 0;
		int worldRow = 0;
		
		
		while(worldCol < gp.MAX_WORLD_COL && worldRow < gp.MAX_WORLD_ROW) {
			//can use tileMap[col][row] for the random
			int tileNum = mapTileNum[worldCol][worldRow];
			//check where the tiles world x is at
			//world x is where its on the map screen x is where we need to draw it
			int worldX = worldCol * gp.TILE_SIZE;
			int worldY = worldRow * gp.TILE_SIZE;
			int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
			int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;
			//this if statement will only draw tiles in the visible screen rather than all tiles at the same time
			//as long as  tile is in the boundary it will be drawn
			//will look the same but saves some processing
			if(worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X && 
			   worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X &&
			   worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y &&
			   worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
				g2.drawImage(tile[tileNum].image, screenX, screenY,null);
			}
			
			//places grass tiles on whole screen
			worldCol++;
			
			
			if(worldCol == gp.MAX_WORLD_COL) {
				worldCol = 0;
				worldRow++;
				
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
