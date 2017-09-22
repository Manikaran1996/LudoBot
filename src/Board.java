import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Board extends JPanel {
	/**
	 * 
	 */
	private static final int MAX_WIDTH=900;
	private static final int SQ_SIZE = MAX_WIDTH/15;
	//custom colors
	private Color board_red=new Color(255,102,102);
	private Color board_blue=new Color(102,178,255);
	private Color board_yellow=new Color(255,255,102);
	private Color board_green=new Color(102,255,102);
	private static final long serialVersionUID = 1L;
	BufferedImage boardImage;
	public Board() {
		boardImage=new BufferedImage(MAX_WIDTH, MAX_WIDTH, BufferedImage.TYPE_INT_RGB);
		init();
	}
	public void paint(Graphics g) {
		g.drawImage(boardImage,10, 10, this);
	}
	public void init() {
		Graphics g = boardImage.getGraphics();
		//draw white board background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, MAX_WIDTH,MAX_WIDTH);
		//draw home squares
		g.setColor(board_red);
		g.fillRect(0, 0, 6*SQ_SIZE, 6*SQ_SIZE);
		g.setColor(board_green);
		g.fillRect( 9*SQ_SIZE,0, 6*SQ_SIZE, 6*SQ_SIZE);
		g.setColor(board_blue);
		g.fillRect( 0,9*SQ_SIZE, 6*SQ_SIZE, 6*SQ_SIZE);
		g.setColor(board_yellow);
		g.fillRect(9*SQ_SIZE ,9*SQ_SIZE,6*SQ_SIZE, 6*SQ_SIZE);
		//draw final winning triangles
		g.setColor(board_red);
		g.fillPolygon(new int[] {6*SQ_SIZE,MAX_WIDTH/2,6*SQ_SIZE},new int[] {6*SQ_SIZE,MAX_WIDTH/2,9*SQ_SIZE} , 3);
		g.setColor(board_green);
		g.fillPolygon(new int[] {6*SQ_SIZE,9*SQ_SIZE,MAX_WIDTH/2},new int[] {6*SQ_SIZE,6*SQ_SIZE,MAX_WIDTH/2} , 3);
		g.setColor(board_yellow);
		g.fillPolygon(new int[] {9*SQ_SIZE,9*SQ_SIZE,MAX_WIDTH/2},new int[] {6*SQ_SIZE,9*SQ_SIZE,MAX_WIDTH/2} , 3);
		g.setColor(board_blue);
		g.fillPolygon(new int[] {6*SQ_SIZE,MAX_WIDTH/2,9*SQ_SIZE},new int[] {9*SQ_SIZE,MAX_WIDTH/2,9*SQ_SIZE} , 3);
		//draw safe squares
		g.setColor(board_red);
		g.fillRect(SQ_SIZE,6*SQ_SIZE, SQ_SIZE,SQ_SIZE);
		g.fillRect(2*SQ_SIZE,8*SQ_SIZE, SQ_SIZE,SQ_SIZE);
		g.fillRect(SQ_SIZE, 7*SQ_SIZE, 5*SQ_SIZE, SQ_SIZE);
		g.setColor(board_green);
		g.fillRect(8*SQ_SIZE,SQ_SIZE, SQ_SIZE,SQ_SIZE);
		g.fillRect(6*SQ_SIZE,2*SQ_SIZE, SQ_SIZE,SQ_SIZE);
		g.fillRect(7*SQ_SIZE, SQ_SIZE, SQ_SIZE, 5*SQ_SIZE);
		g.setColor(board_yellow);
		g.fillRect(13*SQ_SIZE,8*SQ_SIZE, SQ_SIZE,SQ_SIZE);
		g.fillRect(12*SQ_SIZE,6*SQ_SIZE, SQ_SIZE,SQ_SIZE);
		g.fillRect(9*SQ_SIZE, 7*SQ_SIZE, 5*SQ_SIZE, SQ_SIZE);
		g.setColor(board_blue);
		g.fillRect(6*SQ_SIZE,13*SQ_SIZE, SQ_SIZE,SQ_SIZE);
		g.fillRect(8*SQ_SIZE,12*SQ_SIZE, SQ_SIZE,SQ_SIZE);
		g.fillRect(7*SQ_SIZE, 9*SQ_SIZE, SQ_SIZE, 5*SQ_SIZE);
		//draw normal squares
		for(int i=0;i<3;i++) {
			for(int j=0;j<6;j++) {
				g.setColor(Color.black);
				g.drawRect(0+SQ_SIZE*j, 6*SQ_SIZE+SQ_SIZE*i, SQ_SIZE, SQ_SIZE);
				g.drawRect(9*SQ_SIZE+SQ_SIZE*j, 6*SQ_SIZE+SQ_SIZE*i, SQ_SIZE, SQ_SIZE);
			}
		}
		for(int i=0;i<6;i++) {
			for(int j=0;j<3;j++) {		
				g.drawRect(6*SQ_SIZE+SQ_SIZE*j, SQ_SIZE*i, SQ_SIZE, SQ_SIZE);
				g.drawRect(6*SQ_SIZE+SQ_SIZE*j, 9*SQ_SIZE+SQ_SIZE*i, SQ_SIZE, SQ_SIZE);
			}
		}
	
	}
	public void update() {
		
	}
	public static void main(String args[] ) {
		JFrame frame=new JFrame();
		Board board = new Board();
		frame.getContentPane().add(board);
		frame.setTitle("Ludo board");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Board.MAX_WIDTH+20,Board.MAX_WIDTH+20);
		frame.setVisible(true);
	}
}
