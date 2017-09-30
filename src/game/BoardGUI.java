package game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PrimitiveIterator.OfDouble;

import javax.sound.midi.SysexMessage;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class BoardGUI extends JPanel {
	/**
	 * 
	 */
	public static final int MAX_WIDTH=900;
	private static final int SQ_SIZE = MAX_WIDTH/15;
	private static final int PAWN_RAD=(9*SQ_SIZE)/10;
	//custom colors
	private Color board_red=new Color(255,102,102);
	private Color board_blue=new Color(102,178,255);
	private Color board_yellow=new Color(255,255,102);
	private Color board_green=new Color(102,255,102);
	private Color pawn_red=new Color(153,0,0);
	private Color pawn_green=new Color(0,153,0);
	private Color pawn_yellow=new Color(204,204,0);
	private Color pawn_blue=new Color(0,0,153);
	Color colors[]={pawn_red,pawn_green,pawn_yellow,pawn_blue};
	Color board_colors[]={board_red,board_green,board_yellow,board_blue};
	private int home_corners[][];
	private int home_locations[][];
	private int winning_locations[][];
	private int square_locations[][];
	private int win_safe_locations[][][];
	private int counts[];
	private static final long serialVersionUID = 1L;
	BufferedImage boardImage;
	//BufferedImage tempImage;
	public BoardGUI() {
		boardImage=new BufferedImage(MAX_WIDTH, MAX_WIDTH, BufferedImage.TYPE_INT_RGB);
		//tempImage=new BufferedImage(MAX_WIDTH, MAX_WIDTH, BufferedImage.TYPE_INT_RGB);
		createMapping();
		init();
	}
	public void paint(Graphics g) {
		//System.err.println("Painting ");
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
		//tempImage=deepCopy(boardImage);
	}
	private int colorToType(game.Color c) {
		int type=0;
		switch(c) {
		case RED: type=0; break;
		case YELLOW: type=2; break;
		case BLUE: type=3; break;
		case GREEN: type=1; break;
		}
		return type;
	}
	private void createMapping() {
		home_corners=new int [][] {{0,0},{9*SQ_SIZE,0},{9*SQ_SIZE,9*SQ_SIZE},{0,9*SQ_SIZE}};
		home_locations=new int[][] {{(3*SQ_SIZE)/2,(3*SQ_SIZE)/2},{(21*SQ_SIZE)/2,(3*SQ_SIZE)/2},{(21*SQ_SIZE)/2,(21*SQ_SIZE)/2},{(3*SQ_SIZE)/2,(21*SQ_SIZE)/2}};
		winning_locations=new int[][] {{6*SQ_SIZE,7*SQ_SIZE},{7*SQ_SIZE,6*SQ_SIZE},{8*SQ_SIZE,7*SQ_SIZE},{7*SQ_SIZE,8*SQ_SIZE}};
		win_safe_locations=new int[4][5][2];
		square_locations=new int[53][2];
		for(int i=0;i<5;i++) {
			win_safe_locations[0][i][0]=(i+1)*SQ_SIZE;
			win_safe_locations[0][i][1]=7*SQ_SIZE;
			win_safe_locations[1][i][0]=7*SQ_SIZE;
			win_safe_locations[1][i][1]=(i+1)*SQ_SIZE;
			win_safe_locations[2][i][0]=(13-i)*SQ_SIZE;
			win_safe_locations[2][i][1]=7*SQ_SIZE;
			win_safe_locations[3][i][0]=7*SQ_SIZE;
			win_safe_locations[3][i][1]=(13-i)*SQ_SIZE;
		}
		for(int i=0;i<5;i++) {
			square_locations[1+i][0]=(i+1)*SQ_SIZE;
			square_locations[1+i][1]=6*SQ_SIZE;
			square_locations[14+i][0]=8*SQ_SIZE;
			square_locations[14+i][1]=(i+1)*SQ_SIZE;
			square_locations[27+i][0]=(13-i)*SQ_SIZE;
			square_locations[27+i][1]=8*SQ_SIZE;
			square_locations[40+i][0]=6*SQ_SIZE;
			square_locations[40+i][1]=(13-i)*SQ_SIZE;
			square_locations[6+i][0]=6*SQ_SIZE;
			square_locations[6+i][1]=(5-i)*SQ_SIZE;
			square_locations[19+i][0]=(i+9)*SQ_SIZE;
			square_locations[19+i][1]=6*SQ_SIZE;
			square_locations[32+i][0]=8*SQ_SIZE;
			square_locations[32+i][1]=(9+i)*SQ_SIZE;
			square_locations[45+i][0]=(5-i)*SQ_SIZE;
			square_locations[45+i][1]=8*SQ_SIZE;
		}
		for(int i=0;i<3;i++) {
			square_locations[50+i][0]=0;
			square_locations[50+i][1]=(8-i)*SQ_SIZE;
			square_locations[11+i][0]=(6+i)*SQ_SIZE;
			square_locations[11+i][1]=0;
			square_locations[24+i][0]=14*SQ_SIZE;
			square_locations[24+i][1]=(6+i)*SQ_SIZE;
			square_locations[37+i][0]=(8-i)*SQ_SIZE;
			square_locations[37+i][1]=14*SQ_SIZE;
			
		}
		counts=new int[60];
		for(int i=0;i<=58;i++) counts[i]=0;
	}
	private void drawPawn(Graphics g,int x,int y) {
		x=x+SQ_SIZE/2-PAWN_RAD/2;
		y=y+SQ_SIZE/2-PAWN_RAD/2;
		g.fillOval(x,y,PAWN_RAD,PAWN_RAD);
		g.setColor(Color.black);
		g.drawOval(x,y,PAWN_RAD,PAWN_RAD);
		
	}
	private void drawPawn(Graphics g,int x,int y,int r) {
		x=x+SQ_SIZE/2-r/2;
		y=y+SQ_SIZE/2-r/2;
		g.fillOval(x,y,r,r);
		g.setColor(Color.black);
		g.drawOval(x,y,r,r);
	}
	private void drawPawn(Graphics g,int x,int y,ArrayList<Piece> arr) {
		int rad=PAWN_RAD;
		for (Iterator<Piece> iterator = arr.iterator(); iterator.hasNext();) {
			Piece piece = (Piece) iterator.next();
			g.setColor(colors[colorToType(piece.getColor())]);
			drawPawn(g, x, y, rad);
			rad-=6;
		}
	}
	private void drawHomeSquare(Graphics g,int type,int cnt) {
		g.setColor(board_colors[type]);
		g.fillRect(home_corners[type][0], home_corners[type][1], 6*SQ_SIZE, 6*SQ_SIZE);
		g.setColor(Color.black);
		g.drawRect(home_corners[type][0], home_corners[type][1], 6*SQ_SIZE, 6*SQ_SIZE);
		Color color=colors[type];
		int top=home_locations[type][0];
		int left=home_locations[type][1];
		switch(cnt) {
		case 4: g.setColor(color);
				drawPawn(g, top, left);
		case 3: g.setColor(color);
				drawPawn(g, top+2*SQ_SIZE, left);
		case 2: g.setColor(color);
				drawPawn(g, top, left+2*SQ_SIZE);
		case 1: g.setColor(color);
				drawPawn(g, top+2*SQ_SIZE, left+2*SQ_SIZE);
		}
	}
	private void drawWinningSquare(Graphics g,game.Color type,int cnt) {
		int top=winning_locations[colorToType(type)][0],left=winning_locations[colorToType(type)][1];
		Color color=colors[colorToType(type)];
		Piece piece=new Piece(0,type,0);
		ArrayList<Piece> arrayList=new ArrayList<>();
		for(int i=0;i<cnt;i++) arrayList.add(piece);
		g.setColor(color);
		drawPawn(g, top, left, arrayList);
		
	}
	public void redraw(Graphics g,int x,int y,ArrayList<Piece> p,Color back) {
		g.setColor(back);
		g.fillRect(x, y, SQ_SIZE, SQ_SIZE);
		g.setColor(Color.black);
		g.drawRect(x,y,SQ_SIZE,SQ_SIZE);
		drawPawn(g, x, y, p);
	}
	public void update ( Board board) {
		Graphics g=boardImage.getGraphics();
		for(int i=0;i<=58;i++) {
			ArrayList<Piece> pieces=board.getPiecesAtLocation(i);
			if(counts[i]==pieces.size()) continue;
			counts[i]=pieces.size();
			if(i==0 || i>=53) {
				ArrayList<Piece> rc=new ArrayList<>();
				ArrayList<Piece> bc=new ArrayList<>();
				ArrayList<Piece> yc=new ArrayList<>();
				ArrayList<Piece> gc=new ArrayList<>();
				for (Iterator<Piece> iterator = pieces.iterator(); iterator.hasNext();) {
					Piece piece = (Piece) iterator.next();
					game.Color type=piece.getColor();
					switch (type) {
					case RED: rc.add(piece);
						break; 
					case BLUE: bc.add(piece); 
					break;
					case YELLOW: yc.add(piece); 
						break;
					case GREEN:gc.add(piece); 
						break;
					default:
						break;
					}
				}
				if(i==0) {
					drawHomeSquare(g, 0, rc.size());
					drawHomeSquare(g, 1, gc.size());
					drawHomeSquare(g, 2, yc.size());
					drawHomeSquare(g, 3, bc.size());
				}
				else if(i==58){
					drawWinningSquare(g, game.Color.RED, rc.size());
					drawWinningSquare(g, game.Color.GREEN, gc.size());
					drawWinningSquare(g, game.Color.YELLOW, yc.size());
					drawWinningSquare(g, game.Color.BLUE, bc.size());
				}
				else {
					redraw(g, win_safe_locations[0][i-53][0], win_safe_locations[0][i-53][1], rc,board_colors[0]);
					redraw(g, win_safe_locations[1][i-53][0], win_safe_locations[1][i-53][1], gc,board_colors[1]);
					redraw(g, win_safe_locations[2][i-53][0], win_safe_locations[2][i-53][1], yc,board_colors[2]);
					redraw(g, win_safe_locations[3][i-53][0], win_safe_locations[3][i-53][1], bc,board_colors[3]);
					
				}
			}
			else {
				if(i==1 || i == 9) redraw(g, square_locations[i][0], square_locations[i][1], pieces, board_red); 
				else if(i==14 || i==22) redraw(g, square_locations[i][0], square_locations[i][1], pieces, board_green);
				else if(i==27 || i==35) redraw(g, square_locations[i][0], square_locations[i][1], pieces, board_yellow);
				else if(i==40 || i==48) redraw(g, square_locations[i][0], square_locations[i][1], pieces, board_blue);
				else redraw(g, square_locations[i][0], square_locations[i][1], pieces, Color.white);
			}
		}
		repaint();
		
	}
	public static void main(String args[] ) throws InterruptedException {
		JFrame frame=new JFrame();
		BoardGUI board = new BoardGUI();
		frame.getContentPane().add(board);
		frame.setTitle("Ludo board");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(BoardGUI.MAX_WIDTH+20,BoardGUI.MAX_WIDTH+20);
		frame.setVisible(true);
		Board gm=new Board();
		gm.addPiece(new Piece(58,game.Color.YELLOW,0),0);
		gm.addPiece(new Piece(58,game.Color.YELLOW,1),0);
		gm.addPiece(new Piece(31,game.Color.YELLOW,2),38);
		gm.addPiece(new Piece(11,game.Color.YELLOW,3),0);
		gm.addPiece(new Piece(58,game.Color.RED,1),0);
		gm.addPiece(new Piece(38,game.Color.RED,2),0);
		gm.addPiece(new Piece(11,game.Color.RED,3),0);
		gm.addPiece(new Piece(51,game.Color.RED,0),0);
		board.update(gm);
		
		
	}
	static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
}
