package game;

public class Piece {
	private Color clr;
	private int location;
	private int id;
	
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
	
	public int getPieceId() {
		return id;
	}
	
}
