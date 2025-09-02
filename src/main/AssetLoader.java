package main;

import object.OBJ_Key;

public class AssetLoader {
	GamePanel gp;
	public AssetLoader(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		gp.obj[0] = new OBJ_Key();
		//manually setting this for the key
		gp.obj[0].worldX = 23 * gp.TILE_SIZE;
		gp.obj[0].worldY = 7 * gp.TILE_SIZE;
		
		gp.obj[1] = new OBJ_Key();
		gp.obj[1].worldX = 23 * gp.TILE_SIZE;
		gp.obj[1].worldY = 40 * gp.TILE_SIZE;
	}
}
