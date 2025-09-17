package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Heart extends SuperObject{
	public OBJ_Heart(GamePanel gp) {
		name = "Heart";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png"));
			utilityTool.scaleImage(image, gp.TILE_SIZE, gp.TILE_SIZE);
			utilityTool.scaleImage(image2, gp.TILE_SIZE, gp.TILE_SIZE);
			utilityTool.scaleImage(image3, gp.TILE_SIZE, gp.TILE_SIZE);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
