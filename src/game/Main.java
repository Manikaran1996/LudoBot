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
		char color = ' ';
		Color c = Color.RED;
		if(gameMode == 0) {
			if(playerId == 1) {
				color = 'R';
				c = Color.RED;
			}
			else {
				color = 'Y';
				c = Color.YELLOW;
			}
		}
		if(gameMode == 1) {
			if(playerId == 1) {
				color = 'B';
				c = Color.BLUE;
			}
			else {
				color = 'G';
				c = Color.GREEN;
			}
		}
		if(playerId == 1) {
			System.out.println("<THROW>");
			String diceMessage = sc.nextLine();
			String[] msg = diceMessage.split(" ");
			// msg[0] = "YOU"
			// msg[1] = "ROLLED"
			ArrayList<Integer> diceValues = new ArrayList<>();
			for(int i=2;i<msg.length;i++) {
				diceValues.add(Integer.parseInt(msg[i]));
			}
			System.err.println("Dice Throw Received (D) : " + diceValues);
			System.out.println(createMove(c, 0, diceValues.get(0)));
			// Make Move
			// print move on STDOUT
		}
		else {
			String player1Dice = sc.nextLine();
			System.err.println("Player1 Dice : " + player1Dice);
			String player1Move = sc.nextLine();
			System.err.println("Player1 Move : " + player1Move);
			/*String diceMessage = sc.nextLine();
			String moveMessage = sc.nextLine();
			System.err.println("Player1 devi : " + initialMessage[0]); */
		}
		sc.close();
		
	}
	
	private static String createMove(Color c, int id, int move) {
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
}
