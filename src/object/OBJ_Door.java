package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Door extends SuperObject{
	GamePanel gp;
	public OBJ_Door(GamePanel gp) {
		name = "Door";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
			utilityTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
		}catch(IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}
}
