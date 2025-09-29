package main;

import java.awt.Rectangle;

public class EventHandler {
	GamePanel gp;
	EventRect eventRect[][];
	
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventRect = new EventRect[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
		//allows you to have an event rect for everytile
		int col = 0;
		int row = 0;
		while(col < gp.MAX_WORLD_COL && row <gp.MAX_WORLD_ROW) {
			eventRect[col][row] = new EventRect();
			
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			col++;
			
			if(col == gp.MAX_WORLD_COL) {
				col = 0;
				row++;
			}
		}
		
		
		
	}
	
	public void checkEvent() {
		//probably want a method of actually creating event locations and checking not sure
		
		if(hit(27,16,"right")) {
			//event happens
			damagePit(27,16,gp.dialogueState);
		}
		//healing
		if(hit(23,12,"up")) {
			healingPool(23,12,gp.dialogueState);
		}
		//example of teleport
		
//		if(hit(27,16,"right")) {
//			teleport(gp.dialogueState);
//		}
	}
	
	public void teleport(int gameState) {
		// TODO Auto-generated method stub
		gp.gameState = gameState;
		gp.player.worldX = gp.TILE_SIZE*37;
		gp.player.worldY = gp.TILE_SIZE *10;
		gp.ui.currentDialogue = "Teleport!";
	}

	public void damagePit(int col,int row,int gameState) {
		// TODO Auto-generated method stub
		gp.gameState = gameState;
		gp.ui.currentDialogue = "You fall into a pit!";
		gp.player.life -= 1;
		
		//gp.player.direction = "left";
		//this would effectively let you do one time events
		eventRect[col][row].eventDone = true;
	}

	public boolean hit(int col, int row, String reqDirection) {
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		eventRect[col][row].x = col*gp.TILE_SIZE + eventRect[col][row].x;
		eventRect[col][row].y = row*gp.TILE_SIZE + eventRect[col][row].y;
		//System.out.println("PlayerSolidArea:" + gp.player.solidArea + " eventRect:" + eventRect);
		if(gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
			if(gp.player.direction.equals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
			}
		}
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		
		return hit;
	}
	
	public void healingPool(int col,int row,int gameState) {
		if(gp.keyH.enterPressed) {
			gp.gameState = gameState;
			gp.ui.currentDialogue = "You drink the water.\n Your life has been recovered";
			gp.player.life = gp.player.maxLife;
			
		}
		gp.keyH.enterPressed = false;
	}
}
