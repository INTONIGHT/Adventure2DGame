package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed,downPressed,leftPressed,rightPressed,spacePressed;
	public boolean checkDrawTime;
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		//playState
		if(gp.gameState == gp.playState) {
			if(code == KeyEvent.VK_W) {
				upPressed = true;
				
			}
			if(code == KeyEvent.VK_A) {
				leftPressed = true;
				
			}
			if(code == KeyEvent.VK_S) {
				downPressed = true;
				
			}
			if(code == KeyEvent.VK_D) {
				rightPressed = true;
				
			}
			if(code == KeyEvent.VK_P) {
				gp.gameState = gp.pauseState;
				
			}
			if(code == KeyEvent.VK_SPACE) {
				spacePressed = true;
			}
			
			//DEBUG
			if(code == KeyEvent.VK_T) {
				if(!checkDrawTime) {
					checkDrawTime = true;
				}else {
					checkDrawTime = false;
				}
			}
		}
		//pauseState
		else if(gp.gameState == gp.pauseState) {
			if(code == KeyEvent.VK_P) {
				gp.gameState = gp.playState;
			}
		}
		
		else if(gp.gameState == gp.dialogueState) {
			if(code == KeyEvent.VK_SPACE) {
				gp.gameState = gp.playState;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}		
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
			
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
			
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
			
		}
		if(code == KeyEvent.VK_SPACE) {
			
		}
	}

}
