package main;

import entity.NPC_OldMan;
import monsters.MON_GreenSlime;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetLoader {
	GamePanel gp;
	public AssetLoader(GamePanel gp) {
		this.gp = gp;
	}
	
	
	
	public void setObject() {

	}
	
	public void setNPC() {
		gp.npc[0] = new  NPC_OldMan(gp);
		gp.npc[0].worldX = gp.TILE_SIZE * 21;
		gp.npc[0].worldY = gp.TILE_SIZE * 21;
		
	}
	
	public void setMonster() {
		
		int maxRange = 40;
		int min = 10;
		int range = maxRange - min  + 1;
		for(int i =0; i< 5;i ++) {
			gp.monsters[i] = new MON_GreenSlime(gp);
			//i would need some logic to check the tile type its trying to put them on I dont feel like that right now
			//this is the lazy way but it works for now :)
			gp.monsters[i].worldX = gp.TILE_SIZE * 21 + i * gp.TILE_SIZE;
			gp.monsters[i].worldY = gp.TILE_SIZE * 38 + i * gp.TILE_SIZE;
		}
		
	}
	
}
