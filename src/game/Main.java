package game;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public static void main(String [] args) {
		Board board = new Board();
		Scanner sc = new Scanner(System.in);
		String details = sc.nextLine();
		String[] initialMessage = details.split(" ");
		System.err.println("Player ID : " + initialMessage[0]);
		System.err.println("Time Limit : " + initialMessage[1]);
		System.err.println("Game mode : " + initialMessage[2]);
		int playerId = Integer.parseInt(initialMessage[0]);
		int timeLimit = Integer.parseInt(initialMessage[1]);
		int gameMode = Integer.parseInt(initialMessage[2]);
		Piece[] me, opponent;
		me = new Piece[4];
		opponent = new Piece[4];
		Color myColor = Color.RED, opponentColor;
		if(gameMode == 0) {
			if(playerId == 1) {
				myColor = Color.RED;
				opponentColor = Color.YELLOW;
			}
			else {
				myColor = Color.YELLOW;
				opponentColor = Color.RED;
			}
			
		}
		else {
			if(playerId == 1) {
				myColor = Color.BLUE;
				opponentColor = Color.GREEN;
			}
			else {
				myColor = Color.GREEN;
				opponentColor = Color.BLUE;
			}
		}
		initializePieces(me, myColor);
		initializePieces(opponent, opponentColor);
		
		//adding pieces on to board
		
		for(int i=0;i<4;i++) {
			board.addPiece(me[i], 0);
			board.addPiece(opponent[i], 0);
		}
		
		boolean myTurn = false;
		if(playerId == 1)
			myTurn = true;
		
		while(!(hasWon(me) || hasWon(opponent))) {
			if(myTurn) {
				System.out.println("<THROW>");
				String diceMessage = sc.nextLine();
				String[] msg = diceMessage.split(" ");
				// msg[0] = "YOU"
				// msg[1] = "ROLLED"
				ArrayList<Integer> diceValues = new ArrayList<>();
				for(int i=2;i<msg.length;i++) {
					diceValues.add(Integer.parseInt(msg[i]));
				}
				
				String move = board.randomMove(me, diceValues.get(0));
				
				// Make Move
				// print move on STDOUT
				//System.out.println(createMove(myColor, 0, diceValues.get(0)));
				System.out.println(move);
				myTurn = false;
			}
			else {
				String diceMessage = sc.nextLine();
				if(diceMessage.equals("REPEAT")) {
					myTurn = true;
					continue;
				}
				//System.err.println("Player1 Dice : " + player1Dice);
				String opponentMoveMessage = sc.nextLine();
				//System.err.println("Player1 Move : " + player1Move);
				
				// executing move
				String[] moves = opponentMoveMessage.split("<next>");
				myTurn = true;
				for(String str:moves) {
					if(str.equals("REPEAT")) {
						myTurn = false;
						break;
					}
					int pieceId = Integer.parseInt(String.valueOf(str.charAt(1)));
					int move = Integer.parseInt(String.valueOf(str.charAt(3)));
					boolean result= board.opponentMove(opponent[pieceId], move);
					//System.err.println("opponent " + result);
					
				}
				/*String diceMessage = sc.nextLine();
				String moveMessage = sc.nextLine();
				System.err.println("Player1 devi : " + initialMessage[0]); */
			}
			/*System.err.println("Positions : ");
			for(int i=0;i<4;i++)
				System.err.print(me[i].getLocationOnBoard() + "  ");
			System.err.println();
			for(int i=0;i<4;i++)
				System.err.print(opponent[i].getLocationOnBoard() + "  ");
			System.err.println();*/
		}
		sc.close();
		
	}
	
	public static String createMove(Color c, int id, int move) {
		StringBuilder builder = new StringBuilder();
		switch(c) {
		case RED:
			builder.append("R");
			break;
		case GREEN:
			builder.append("G");
			break;
		case BLUE:
			builder.append("B");
			break;
		case YELLOW:
			builder.append("Y");
			break;
		}
		builder.append(id);
		builder.append("_"+move);
		return builder.toString();
		
	}
	
	private static void initializePieces(Piece[] p , Color c) {
		for(int i=0;i<p.length;i++) {
			p[i] = new Piece(0, c, i);
		}
	}
	
	private static boolean hasWon(Piece[] p) {
		boolean won = true;
		for(int i=0;i<p.length;i++) {
			if(p[i].getLocationOnBoard() != 58) {
				won = false;
				break;
			}
		}
		return won;
	}
	
	
}
