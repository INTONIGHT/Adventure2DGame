package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity{

	public OBJ_Key(GamePanel gp) {
		super(gp);
		name = "Key";
		down1 = setup("/objects/key",gp.TILE_SIZE,gp.TILE_SIZE);
		
	}
}
