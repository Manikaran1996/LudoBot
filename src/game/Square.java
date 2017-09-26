package game;

import java.util.ArrayList;

public class Square {
	private ArrayList<Piece> pieces;
	
	public Square() {
		pieces = new ArrayList<Piece>();
	}
	
	public void addPiece(Piece p) {
		pieces.add(p);
	}
	
	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	public void removePiece(Piece p) {
		int size = pieces.size() , i;
		for(i = 0; i<size;i++) {
			Piece temp = pieces.get(i);
			if(temp.getColor() == p.getColor()) {
				break;
			}
		}
		pieces.remove(i);
	}
	
	public String toString() {
		return pieces.toString();
	}
}
