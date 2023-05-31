/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ')				return;
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
		case '$':
			Main.spaceTrigger = !Main.spaceTrigger;
			break;
		case 'd':
			Main.direction = "right";
			Main.lastSpriteIndex = Main.currentSpriteIndex; //used to find the distance between the two points
			Main.lastVec = Main.player.getCoords();
			Main.lastDirection = Main.direction;
			break;
		case 's':
			Main.direction = "down";
			Main.lastSpriteIndex = Main.currentSpriteIndex; 
			Main.lastVec = Main.player.getCoords();
			Main.lastDirection = Main.direction;
			break;
		case 'w':
			Main.direction = "up";
			Main.lastSpriteIndex = Main.currentSpriteIndex; 
			Main.lastVec = Main.player.getCoords();
			Main.lastDirection = Main.direction;
			break;
		case 'a':
			Main.direction = "left";
			Main.lastSpriteIndex = Main.currentSpriteIndex; //used to find the distance between the two points
			Main.lastVec = Main.player.getCoords();
			Main.lastDirection = Main.direction;
			break;
		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
		}
	}
}