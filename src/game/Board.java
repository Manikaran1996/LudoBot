package game;

import java.util.ArrayList;

public class Board {
	private Square[] square;
	private int[] safeSquares;
	public Board() {
		square = new Square[60];
		for(int i=0;i<60;i++) square[i]=new Square();
		safeSquares = new int[8];
		safeSquares[0] = 1;
		safeSquares[1] = 9;
		safeSquares[2] = 14;
		safeSquares[3] = 22;
		safeSquares[4] = 27;
		safeSquares[5] = 35;
		safeSquares[6] = 40;
		safeSquares[7] = 48;
	}
	ArrayList<Piece> getPiecesAtLocation(int x) {
		if(x < 60)
			return square[x].getPieces();
		return null;
	}
	
	void putPieceAt(int loc, Color c, int id) {
		square[loc].addPiece(new Piece(loc, c, id));
	}
	
	void addPiece(Piece p, int loc) {
		square[loc].addPiece(p);
	}
	
	//TODO add the logic when one piece cuts other piece of different colour
	boolean movePiece(int from, int to) {
		if(square[from] == null)
			return false;
		square[to] = square[from];
		square[from] = null;
		return true;
	}
	
	boolean movePiece(Piece p, int number) {
		boolean legal = checkIfMoveIsLegal(p, number);
		if(!legal) {
			return false;
		}
		int currentLocation = p.getLocationOnBoard();
		// TODO - new location should take into account from 52 to 1
		int newLocation = currentLocation + number;
		/* if(newLocation > 52) {
			newLocation -= 52;
		} */
		ArrayList<Piece> piecesAtNewLoc = square[newLocation].getPieces();
		//If there is not piece at new location then simply move
		if(piecesAtNewLoc.size() == 0) {
			square[currentLocation].removePiece(p);
			p.changeLocationTo(newLocation);
			square[newLocation].addPiece(p);
		}
		else {
			// If there is a piece at new location then it could either be the 
			// safe square or a normal square. In case of safe square place piece at new
			// location else, check if there is block-ed, otherwise, move the piece
			// already at the location to the 0th position and the piece p to the new location.
			Piece temp = piecesAtNewLoc.get(0);
			if(temp != null) {
				if(isASafeSquare(newLocation)) {
					p.changeLocationTo(newLocation);
					square[currentLocation].removePiece(p);
					square[newLocation].addPiece(p);
					return true;
				}
				else if(temp.getColor() == p.getColor()) //blockEd
					return false;
				else if(temp.getColor() != p.getColor()) {
					square[newLocation].removePiece(temp);
					p.changeLocationTo(newLocation);
					square[newLocation].addPiece(p);
					temp.changeLocationTo(0);
					square[0].addPiece(temp);
					return true;
				}
			}
		}
			
		return true;
	}
	
	boolean checkIfMoveIsLegal(Piece p, int number) {
		int currentLocation = p.getLocationOnBoard();
		if(currentLocation == 0 && (number != 1 || number != 6 ))
			return false;
		if(currentLocation + number > 58)
			return false;
		return true;
	}
	
	
	boolean isASafeSquare(int position) {
		for(int i=0;i<8;i++) {
			if(safeSquares[i] == position)
				return true;
		}
		return false;
	}
	
}
