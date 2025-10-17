package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed,downPressed,leftPressed,rightPressed,spacePressed, enterPressed, fPressed;
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
		//TITLE Screen
		if(gp.gameState == gp.titleState) {
			titleState(code);
		}
		
		//playState
		else if(gp.gameState == gp.playState) {
			playState(code);
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
		//character state
		else if(gp.gameState == gp.characterState) {
			if(code == KeyEvent.VK_SPACE) {
				gp.gameState = gp.playState;
			}
		}
		
	}
	
	public void titleState(int code) {
		if(code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 2;
			}
		}
		if(code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum > 2) {
				gp.ui.commandNum = 0;
			}
		}
		if(code == KeyEvent.VK_ENTER) {
			switch(gp.ui.commandNum) {
			case 0:
				gp.gameState = gp.playState;
				//would start music here instead.
				break;
				//loadgame
			case 1:
				//TODO
				break;
			//Quit game
			case 2:
				System.exit(0);
				
			}
		}
	}
	
	public void playState(int code) {
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
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if(code == KeyEvent.VK_F) {
			fPressed = true;
		}
		//character State
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
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
			spacePressed = false;
		}
		if(code == KeyEvent.VK_F) {
			fPressed = false;
		}
//		if(code == KeyEvent.VK_ENTER) {
//			spacePressed = false;
//		}
	}

}
