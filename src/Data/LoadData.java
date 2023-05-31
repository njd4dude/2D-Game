package Data;
import java.util.StringTokenizer;

import FileIO.EZFileRead;
import Main.Main;
public class LoadData {
	public static void HoriFrames(){
		for(int i = 0; i <= 2000; i += 12){
			if(Main.currentSpriteIndex > 8) //once it reaches the last frame of walk animation reset back to the first frame
				Main.currentSpriteIndex = 0;
			Vector2D RtempVec = new Vector2D(i, 850);
			Vector2D LtempVec = new Vector2D(2000-i, 850);
			Main.Rsprites.add(new spriteInfo(RtempVec, "Rwalk"+Integer.toString(Main.currentSpriteIndex))); //populates sprite walking animations across the screen
			Main.Lsprites.add(new spriteInfo(LtempVec, "Lwalk"+Integer.toString(Main.currentSpriteIndex))); //populates walking towards the left
			Main.currentSpriteIndex ++; //iterate to the next key frame
		}
		Main.currentSpriteIndex = 0;
	}
	
	public static void VertFrames(){
		for(int i = 0; i <= 1200; i += 12){
			if(Main.currentSpriteIndex > 7) //once it reaches the last frame of walk animation reset back to the first frame
				Main.currentSpriteIndex = 0;
			Vector2D UtempVec = new Vector2D(500, i);
			Vector2D DtempVec = new Vector2D(500, 1200-i);
			Main.Fsprites.add(new spriteInfo(UtempVec, "Fwalk"+Integer.toString(Main.currentSpriteIndex))); //populates sprite walking animations across the screen
			Main.Dsprites.add(new spriteInfo(DtempVec, "Dwalk"+Integer.toString(Main.currentSpriteIndex))); //populates walking towards the left
			Main.currentSpriteIndex++; //iterate to the next key frame
		}
		Main.currentSpriteIndex = 0;
	}
	
	public static void TextFile(){
		EZFileRead ezr = new EZFileRead("LamarTalk.txt");
		for(int i = 0; i < ezr.getNumLines(); i++){
			String line = ezr.getLine(i);
			StringTokenizer token = new StringTokenizer(line,"*");
			String key = token.nextToken();
			String value = token.nextToken();
			Main.map.put(key, value);
		}
	}
	
	public static void BeeFrames() {
		int flyHeight = 10;
		for(int i =0; i < flyHeight ; i++) {
			Main.beeQ.add(new Vector2D(500, 500 + i));
			Main.beeQ2.add(new Vector2D(500, 510 - i));
		}
	}
	
	public static void loadCollidables() {
		Main.decor.put("pool", new CollisionDetection(Main.poolObjX+60, Main.poolObjX+305, Main.poolObjY+70, Main.poolObjY+150));
		Main.decor.put("hive", new CollisionDetection(Main.hiveObjX-20, Main.hiveObjX+60, Main.hiveObjY-50, Main.hiveObjY+100));
		Main.decor.put("house",new CollisionDetection(Main.houseObjX, Main.houseObjX+ 310, Main.houseObjY-100, Main.houseObjY+215));
	}
	
	public static void loadInteractables() {
		Main.decorInteractable.put("poolAbove", new CollisionDetection(Main.poolObjX+120, Main.poolObjX+245, Main.poolObjY+20, Main.poolObjY+70));
		Main.decorInteractable.put("poolBelow", new CollisionDetection(Main.poolObjX+120, Main.poolObjX+245, Main.poolObjY+150, Main.poolObjY+230));
		Main.decorInteractable.put("poolLeft", new CollisionDetection(Main.poolObjX, Main.poolObjX+60, Main.poolObjY+100, Main.poolObjY+150));
		Main.decorInteractable.put("poolRight", new CollisionDetection(Main.poolObjX+305, Main.poolObjX+365, Main.poolObjY+100, Main.poolObjY+150));
		
		Main.decorInteractable.put("hiveAbove", new CollisionDetection(Main.hiveObjX-20, Main.hiveObjX+60, Main.hiveObjY-100, Main.hiveObjY-50));
		Main.decorInteractable.put("hiveBelow", new CollisionDetection(Main.hiveObjX-20, Main.hiveObjX+60, Main.hiveObjY+100, Main.hiveObjY+150));
		Main.decorInteractable.put("hiveLeft", new CollisionDetection(Main.hiveObjX-70, Main.hiveObjX-20, Main.hiveObjY-10, Main.hiveObjY+100));
		Main.decorInteractable.put("hiveRight", new CollisionDetection(Main.hiveObjX+60, Main.hiveObjX+110, Main.hiveObjY-10, Main.hiveObjY+100));
		
		Main.decorInteractable.put("houseBelow", new CollisionDetection(Main.houseObjX+20, Main.houseObjX+ 290, Main.houseObjY+215, Main.houseObjY+285));
	}
}
