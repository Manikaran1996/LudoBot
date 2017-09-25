package game;

public class Piece {
	private Color clr;
	private int location;
	private int id;
	private int relativePosition;
	
	public Piece() {
		location = 0;
		relativePosition = 0;
	}
	
	public Piece(int loc, Color c, int id) {
		clr = c;
		location = loc;
		this.id = id;
	}
	
	public int getLocationOnBoard() {
		return location;
	}
	
	public Color getColor() {
		return clr;
	}
	
	public void changeLocationTo(int loc) {
		location = loc;
	}
	
	public void setRelativePosition(int pos) {
		relativePosition = pos;
	}
	
	public int getRelativePosition() {
		return relativePosition;
	}
	
	public int getId() {
		return id;
	}
	
	public void setColor(Color c) {
		clr = c;
	}
	
	public void setId(int id) {
		
	}
}
