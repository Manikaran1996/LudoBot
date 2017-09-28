package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Strategy {
	public Strategy() {
		// TODO Auto-generated constructor stub
	}
	public static int defensiveStrategy(Board board,Piece me[],int diceRoll) {
		int ind=0;
		int n=me.length;
		int threats[]=new int[n];
		for(int i=0;i<n;i++) { // for each my piece
			if(!board.checkIfMoveIsLegal(me[i], diceRoll)) {
				threats[i]=10000; //dummy max
				continue;
			}
			threats[i]=0;
			int newLocation = board.getNewLocation(me[i], diceRoll);
			for(int j=0;j<n;j++) {
				if (i==j){
					if(board.isASafeSquare(newLocation)) continue;
					if(newLocation>52) continue;
					// for each previous 5 locations possible
					// count threats by checking list of each square for opponent color pawn
					for(int k=1;k<=5;k++) {
						int loc = newLocation-k;
						if(loc<=0) loc+=52;
						ArrayList<Piece> pieces=board.getPiecesAtLocation(loc);
						for (Iterator<Piece> iterator = pieces.iterator(); iterator
								.hasNext();) {
							Piece piece = (Piece) iterator.next();
							if(piece.getColor()!=me[j].getColor()) threats[i]++;
						}
					}
				}
				else {
					if(board.isASafeSquare(me[j].getLocationOnBoard())) continue;
					if(me[j].getLocationOnBoard()>52) continue;
					// for each previous 5 locations possible
					// count threats by checking list of each square for opponent color pawn
					for(int k=1;k<=5;k++) {
						int loc = me[j].getLocationOnBoard()-k;
						if(loc<=0) loc+=52;
						ArrayList<Piece> pieces=board.getPiecesAtLocation(loc);
						for (Iterator<Piece> iterator = pieces.iterator(); iterator
								.hasNext();) {
							Piece piece = (Piece) iterator.next();
							if(board.getNewLocation(piece, k+1) > 52) continue;
							// use relative location from startif on cut moves to safe home final zone then dont count it ------------------TODO
							if(piece.getColor()!=me[j].getColor()) threats[i]++;
						}
					}
				}
			}
		}
		boolean allEqual=true;
		for(int i=0;i<n-1;i++) if(threats[i]!=threats[i+1]) allEqual=false;
		if(allEqual) return -1;
		for(int i=1;i<n;i++) if(threats[ind]>threats[i]) ind=i; 
		return ind;
	}
	public static int aggressiveStrategy(Board board,Piece me[],int diceRoll) {
		int n=me.length;
		for(int i=0;i<n;i++) {
			if(!board.checkIfMoveIsLegal(me[i], diceRoll)) continue;
			int newLocation = board.getNewLocation(me[i], diceRoll);
			if(board.isASafeSquare(newLocation))continue;
			if(newLocation>52) continue;
			ArrayList<Piece> pieces = board.getPiecesAtLocation(newLocation);
			if(pieces.size()!=0 && pieces.get(0).getColor()!=me[i].getColor() ) return i;
		}
		return -1;
	}
	//error yet to be resolved
	public static int fastStrategy(Board board,Piece me[],int diceRoll) {
		int ind=0;
		int maxpos=-1;
		int n=me.length;
		int max_move[]=new int[n];
		for(int i=0;i<n;i++) {
			if(board.checkIfMoveIsLegal(me[i], diceRoll)) max_move[i]=me[i].getRelativePosition();
			else max_move[i]=-1;
			if(maxpos <max_move[i]) maxpos=max_move[i]; 
		}
		if(maxpos==-1) return -1;
		for(int i=1;i<n;i++) if(max_move[ind]<max_move[i]) ind=i; 
		return ind;
	}
	}
