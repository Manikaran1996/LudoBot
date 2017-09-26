package game;

import java.util.ArrayList;

public class Board {
	private Square[] square;
	private int[] safeSquares;
	private int redStartLocation = 1;
	private int yellowStartLocation = 27 ;
	private int greenStartLocation = 14;
	private int blueStartLocation = 40;
	
	public Board() {
		square = new Square[60];
		for(int i=0;i<60;i++) square[i]=new Square();
		safeSquares = new int[14];
		safeSquares[0] = 1;
		safeSquares[1] = 9;
		safeSquares[2] = 14;
		safeSquares[3] = 22;
		safeSquares[4] = 27;
		safeSquares[5] = 35;
		safeSquares[6] = 40;
		safeSquares[7] = 48;
		safeSquares[8] = 53;
		safeSquares[9] = 54;
		safeSquares[10] = 55;
		safeSquares[11] = 56;
		safeSquares[12] = 57;
		safeSquares[13] = 58;
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

	
	boolean movePiece(Piece p, int number) {
		boolean legal = checkIfMoveIsLegal(p, number);
		if(!legal) {
			return false;
		}
		int currentLocation = p.getLocationOnBoard();
		System.err.println("Bot: currentLocation " + currentLocation);
		int relativePosition = p.getRelativePosition();
		Color c = p.getColor();
		int newLocation = 0;
		if(relativePosition + number < 52) {
			newLocation = currentLocation + number;
			if(c != Color.RED) {
				if(newLocation > 52) {
					newLocation = newLocation - 52;
				}
			}
			relativePosition += number;
		}
		else if(relativePosition < 52 && relativePosition + number >= 52) {
			newLocation = relativePosition + number + 1;
			relativePosition += number + 1;
		}
		else if(relativePosition >= 52) {
			// TODO - CHECK
			// updation of relative position is also to be handled
			newLocation = relativePosition + number;
			relativePosition += number;
		}
		
		ArrayList<Piece> piecesAtNewLoc = square[newLocation].getPieces();
		//If there is not piece at new location then simply move
		if(piecesAtNewLoc.size() == 0) {
			square[currentLocation].removePiece(p);
			p.changeLocationTo(newLocation);
			square[newLocation].addPiece(p);
			p.setRelativePosition(relativePosition);
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
					p.setRelativePosition(relativePosition);
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
					p.setRelativePosition(relativePosition);
					return true;
				}
			}
		}
			
		return true;
	}
	
	boolean checkIfMoveIsLegal(Piece p, int number) {
		int currentLocation = p.getLocationOnBoard();
		if(currentLocation == 0 && (number != 1 && number != 6 ))
			return false;
		if(currentLocation + number > 58)
			return false;
		return true;
	}
	
	
	boolean isASafeSquare(int position) {
		for(int i=0;i<14;i++) {
			if(safeSquares[i] == position)
				return true;
		}
		return false;
	}
	
	public String randomMove(Piece[] piece, int dice) {
		for(Piece p : piece) {
			if(p.getLocationOnBoard() != 0) {
				boolean moved = movePiece(p, dice);
				if(moved) {
					System.err.println("Bot : " + p.getLocationOnBoard() + " " + p.getRelativePosition());
					return Main.createMove(p.getColor(), p.getId(), dice);
				}
			}
		}
		if(dice == 1 || dice == 6) {
			int i=0;
			while(i<4 && piece[i].getLocationOnBoard() >= 58) {
				i++;
			}
			if(i==4)
				return "NA";
			square[i].removePiece(piece[i]);
			piece[i].setRelativePosition(1);
			switch(piece[i].getColor()) {
			case RED:
				piece[i].changeLocationTo(redStartLocation);
				square[redStartLocation].addPiece(piece[i]);
				break;
			case BLUE:
				piece[i].changeLocationTo(blueStartLocation);
				square[blueStartLocation].addPiece(piece[i]);
				break;
			case GREEN:
				piece[i].changeLocationTo(greenStartLocation);
				square[greenStartLocation].addPiece(piece[i]);
				break;
			case YELLOW:
				piece[i].changeLocationTo(yellowStartLocation);
				square[yellowStartLocation].addPiece(piece[i]);
				break;
			}
			return Main.createMove(piece[i].getColor(), piece[i].getId(), 1);
		}
		return "NA";
		
	}
	
	public void opponentMove(Piece p, int number) {
		int currLocation = p.getLocationOnBoard();
	}
}
