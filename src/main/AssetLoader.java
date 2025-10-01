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
		gp.monsters[0] = new MON_GreenSlime(gp);
		gp.monsters[0].worldX = gp.TILE_SIZE * 23;
		gp.monsters[0].worldY = gp.TILE_SIZE * 36;
		
		gp.monsters[1] = new MON_GreenSlime(gp);
		gp.monsters[1].worldX = gp.TILE_SIZE * 23;
		gp.monsters[1].worldY = gp.TILE_SIZE * 37;
		
		
		
	}
	
}
