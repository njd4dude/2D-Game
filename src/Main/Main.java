package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import Data.CollisionDetection;
import Data.LoadData;
import Data.Vector2D;
import Data.spriteInfo;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
	public static Color c = new Color(255, 140, 0);
	public static stopWatchX timer = new stopWatchX(70);

	public static ArrayList<spriteInfo> Rsprites = new ArrayList<>();
	public static ArrayList<spriteInfo> Lsprites = new ArrayList<>();
	public static ArrayList<spriteInfo> Fsprites = new ArrayList<>();
	public static ArrayList<spriteInfo> Dsprites = new ArrayList<>();
	public static int currentSpriteIndex = 0;
	public static HashMap<String, String> map = new HashMap<>();
	
	public static spriteInfo player = new spriteInfo(new Vector2D(800, 500), "Lamar");
	public static Vector2D lastVec = player.getCoords();
	public static String direction = "";
	public static int lastSpriteIndex = 0;
	public static int spriteDistance = 0;
	
	public static int BeeY = 400;
	public static int BeeDist = 0;
	public static Queue<Vector2D> beeQ = new LinkedList<>();
	public static Queue<Vector2D> beeQ2 = new LinkedList<>();
	public static Stack<Vector2D> beeS = new Stack<>();
	public static Stack<Vector2D> beeS2 = new Stack<>();
	public static Vector2D beeVec;
	public static Vector2D beeVec2;
	public static stopWatchX beeTimer= new stopWatchX(45);
	
	public static boolean collide = false;
	public static boolean collideLastFrame = false;
	
	public static HashMap<String, CollisionDetection> decor = new HashMap<>();   
	public static HashMap<String, CollisionDetection> decorInteractable = new HashMap<>();  
	
	public static String lastDirection = direction;
	public static boolean spaceTrigger = false;
	
	public static CollisionDetection Lamar;

	public static final int poolObjX = 1050;
	public static final int poolObjY = 540;
	public static final int houseObjX = 750;
	public static final int houseObjY = 0;
	public static final int hiveObjX = 385;
	public static final int hiveObjY = 525;
	
	public static String text = "";
	public static boolean triggerBee = false;
	private static boolean collideL = false;
	private static boolean collideR = false;
	private static boolean collideU = false;
	private static boolean collideD = false;
	
	
	
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	public static void start(){
	//LOAD WALK ANIMATION(LEFT & RIGHT)
		LoadData.HoriFrames();
	//LOAD WALK ANIMATION(UP & DOWN)
		LoadData.VertFrames();
		currentSpriteIndex = 0;
	//LOAD TEXT FILE
		LoadData.TextFile();
	//LOAD BEE ANIMATION
		LoadData.BeeFrames();
	//add collidable objects into container called decor
		LoadData.loadCollidables();
	//add collision buffers for interactable areas next to object
		LoadData.loadInteractables();
	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		//draw sprites to screen
	    drawSprites(ctrl);
	   
	    //does routine every time an input of direction is seen
	    wasdInput();
		
		//when the character stops moving set it to a standing still frame
		stillFrame();
		
		//Bee Movement up and down
		beeMovement();
		
	    //collision detection   
	    Collisions();
	
		 //makes interactable items active
		interactables(ctrl);
		
		ctrl.drawString(500, 500, collideL+" L", c);
		ctrl.drawString(500, 550, collideR+" R", c);
		
	}
	//private methods
	private static void wasdInput() {
		if(currentSpriteIndex >= 8){
			currentSpriteIndex = 0;
			lastSpriteIndex = 0;
		}
		if(direction.equals("right")){
			direction = walk("right");
			player.setTag(Rsprites.get(currentSpriteIndex).getTag());
		}else if(direction.equals("left")){
			direction = walk("left");;
			player.setTag(Lsprites.get(currentSpriteIndex).getTag());
		}else if(direction.equals("up")) {
			direction = walk("up");
			player.setTag(Fsprites.get(currentSpriteIndex).getTag());
		}else if(direction.equals("down")) {
			direction = walk("down");
			player.setTag(Dsprites.get(currentSpriteIndex).getTag());
		}
	}
	
	private static void stillFrame() {
		if(direction.equals("")) {
			switch(lastDirection) {
			case "right":
				player.setTag(Rsprites.get(2).getTag());
				break;
			case "left":
				player.setTag(Lsprites.get(2).getTag());
				break;
			case "up":
				player.setTag(Fsprites.get(0).getTag());
				break;
			case "down":
				player.setTag(Dsprites.get(0).getTag());
				break;
			}
		}
	}
	
	private static final int WALK_STEP = 12;
	private static final int MAX_X = 1675;
	private static final int MAX_Y = 830;

	private static String walk(String direction) {
	    if (timer.isTimeUp()) {
	        int newX = player.getCoords().getX();
	        int newY = player.getCoords().getY();
	        if (direction.equals("right") && newX < MAX_X && !collideR) {
	            newX += WALK_STEP;
	        } else if (direction.equals("left") && newX > 125 && !collideL) {
	            newX -= WALK_STEP;
	        } else if (direction.equals("up") && newY > 0 && !collideU) {
	            newY -= WALK_STEP;
	        } else if (direction.equals("down") && newY < MAX_Y && !collideD) {
	            newY += WALK_STEP;
	        }
	        player.setCoords(new Vector2D(newX, newY));
	        currentSpriteIndex++;
	        timer.resetWatch();
	    }
	    spriteDistance = currentSpriteIndex - lastSpriteIndex;
        if (spriteDistance > 3) {
            direction = "";
        }
	    return direction;
	}
	
	private static void beeMovement() {
		if(beeTimer.isTimeUp()) {
			beeVec =  beeQ.remove();
			beeS.push(beeVec);
			
			beeVec2 = beeQ2.remove();
			beeS2.push(beeVec2);
			
			beeTimer.resetWatch();
			}
				
		if(beeQ.isEmpty()) {
			while(!beeS.isEmpty()) {
				beeQ.add(beeS.pop());
				}
			}
		
		if(beeQ2.isEmpty()) {
			while(!beeS2.isEmpty()) {
				beeQ2.add(beeS2.pop());
				}
			}
	}
	
	
	private static void Collisions() {
		Lamar = new CollisionDetection(player.getCoords().getX() - 50, player.getCoords().getX() + 50, player.getCoords().getY() - 50, player.getCoords().getY() + 50);
		collideL = false;
		collideR = false;
		collideU = false;
		collideD = false;

		for (Map.Entry<String, CollisionDetection> entry : decor.entrySet()) {
		    if (Lamar.intersects(entry.getValue())) {
		        switch (lastDirection) {
		            case "left":
		                collideL = true;
		                break;
		            case "right":
		                collideR = true;
		                break;
		            case "up":
		                collideU = true;
		                break;
		            case "down":
		                collideD = true;
		                break;
		        }
		    }
		}

		if (collideL) {
		    player.getCoords().adjustX(12); // Adjust X-coordinate for left side collision
		} else if (collideR) {
		    player.getCoords().adjustX(-12); // Adjust X-coordinate for right side collision
		} else if (collideU) {
		    player.getCoords().adjustY(12); // Adjust Y-coordinate for upward collision
		} else if (collideD) {
		    player.getCoords().adjustY(-12); // Adjust Y-coordinate for downward collision
		}
	}
	
	private static void interactables(Control ctrl) {
	    boolean spriteNearInteractable = false;
	    
	    if (Lamar.intersects(decorInteractable.get("poolAbove")) && (lastDirection.equals("down"))
	            || Lamar.intersects(decorInteractable.get("poolBelow")) && (lastDirection.equals("up"))
	            || Lamar.intersects(decorInteractable.get("poolLeft")) && (lastDirection.equals("right"))
	            || Lamar.intersects(decorInteractable.get("poolRight")) && (lastDirection.equals("left"))) {
	        text = map.get("string1");
	        spriteNearInteractable = true;
	    } else if (Lamar.intersects(decorInteractable.get("hiveAbove")) && (lastDirection.equals("down"))
	            || Lamar.intersects(decorInteractable.get("hiveBelow")) && (lastDirection.equals("up"))
	            || Lamar.intersects(decorInteractable.get("hiveLeft")) && (lastDirection.equals("right"))
	            || Lamar.intersects(decorInteractable.get("hiveRight")) && (lastDirection.equals("left"))) {
	        text = map.get("string2");
	        spriteNearInteractable = true;
	        if(spaceTrigger)
	        	 triggerBee = true;
	    } else if (Lamar.intersects(decorInteractable.get("houseBelow")) && (lastDirection.equals("up"))) {
	        text = map.get("string3");
	        spriteNearInteractable = true;
	    }
	    
	    if (spriteNearInteractable && spaceTrigger) {
	        ctrl.drawString(60, 930, text, Color.black);
	        ctrl.addSpriteToFrontBuffer(0, 810, "textbox");
	    } else {
	        spaceTrigger = false;
	    }
	}

	private static void drawSprites(Control ctrl) {
		ctrl.addSpriteToFrontBuffer(0, 0, "Background");
		ctrl.addSpriteToFrontBuffer(poolObjX, poolObjY, "poolObj");
		ctrl.addSpriteToFrontBuffer(houseObjX, houseObjY, "houseObj");
		ctrl.addSpriteToFrontBuffer(hiveObjX, hiveObjY, "hiveObj");
		ctrl.addSpriteToFrontBuffer(player.getCoords().getX(), player.getCoords().getY(), player.getTag());
		if(triggerBee) {
			ctrl.addSpriteToFrontBuffer(520, beeVec.getY(), "bee");
			ctrl.addSpriteToFrontBuffer(525, beeVec2.getY(), "bee2");
		}
	}
}

