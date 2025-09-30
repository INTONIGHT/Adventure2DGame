package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		name = "Heart";
		image = setup("objects/heart_full.png");
		image2 = setup("objects/heart_full.png");
		image3 = setup("objects/heart_full.png");
		
		
	}
}
