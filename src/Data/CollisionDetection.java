package Data;

public class CollisionDetection {

	public int x1;
	public int x2;
	public int y1;
	public int y2;

	public CollisionDetection(int x1, int x2, int y1, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public boolean intersects(CollisionDetection other) {
		//checks if theres no collision
		if(this.x1 > other.x2
			|| this.x2 < other.x1
			|| this.y1 > other.y2
			|| this.y2 < other.y1
			) {
			return false;
		}else
			return true;
	}
	
	
}
