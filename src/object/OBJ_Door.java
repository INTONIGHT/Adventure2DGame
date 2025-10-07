package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{

	public OBJ_Door(GamePanel gp) {
		super(gp);
		name = "Door";
		down1 = setup("/objects/door",gp.TILE_SIZE,gp.TILE_SIZE);
		collision = true;
		//testing a smaller area
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
