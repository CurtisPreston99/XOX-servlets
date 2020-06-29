package nz.ac.massey.cs.webtech.s_16453897.server;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class tictactoeManager {
	static final int player =1;
	static final int AI=-1;
	
	int[][] board = new int[3][3];
//	starts the board and also has the computer start if start is == AI
	public tictactoeManager(int start) {
		for(int x=0;x<3;x++) {
			for(int y=0;y<3;y++) {
				board[x][y]=0;
			}
		}
		//if ai starts
		if(start == AI) {
			AI();
		}
	}
//	returns all empty tiles coordinates  
	public int[][] availableMoves(){
		ArrayList<int[]> out=new ArrayList<>();
		for(int x=0;x<3;x++) {
			for(int y=0;y<3;y++) {
				if(board[x][y]==0) {
					int[] empty= {x,y};
					out.add(empty);
				}
			}
		}
		int[][] intout= new int[out.size()][2];
		for(int i=0;i<out.size();i++) {
			intout[i]=out.get(i);
		}
		return intout;
	}
//	super advanced AI
	public void AI() {
		Random rand = new Random();
		// gets all available tiles
		int[][] canDo=availableMoves();
		//picks random index
		int move=rand.nextInt(canDo.length);
		//has a move at the index
		board[canDo[move][0]][canDo[move][1]]=AI;
	}
	
//	player move returns true if the player can move there false if its invalid
	public Boolean PlayerMove(int x,int y) {
		//if empty
		if(board[x][y]==0) {
			board[x][y]=player;
			return true;
		}else {
			return false;
		}
	}
	
	//makes image
	public BufferedImage toBufferedImage() {
		//make image
		BufferedImage image = new BufferedImage(300,300, BufferedImage.TYPE_INT_RGB);
		//makes Graphics layer
        Graphics2D g = image.createGraphics();
        //sets background to white
        g.setColor(Color.white);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        //makes grid
		for(int i=0;i<3;i++) {
			for(int e=0;e<3;e++) {
				g.setColor(Color.white);
				g.drawRect(e*100, i*100, 100, 100);
				g.setColor(Color.black);
				g.drawRect((e*100)+1, (i*100)+1, 98, 98);
			}
        }
		//draws all X's and 0's
		for(int x=0;x<3;x++) {
			for(int y=0;y<3;y++) {
				if(board[x][y]==player) {
					//draws x
					g.drawLine(x*100, y*100, (x+1)*100, (y+1)*100);
					g.drawLine((x*100)+100, y*100, (x*100), (y+1)*100);
				}
				if(board[x][y]==AI) {
//					draws 0
					g.drawOval(x*100, y*100, 100, 100);
				}
			}
		}
		
        return image;
	}
	
	//converts to sting
	public String toString() {
		String out="";
		for(int x=0;x<3;x++) {
			String line="";
			for(int y=0;y<3;y++) {
				//if empty
				if(board[x][y]==0) {
					line+="_";
				}
				//if ai
				if(board[x][y]==AI) {
					line+="O";
				}
				//if player
				if(board[x][y]==player) {
					line+="X";
				}
			}
			line+="\n";
			out+=line;
		}
		return out;
	}
	
	//checks if player computer or if its a draw or is there is no winner yet
	public String gameoverCheck() {
		  //down
		  for (int x=0; x<board.length; x++) {
		    if (board[x][0]!=0) {
		      if (board[x][0]==board[x][1]&&board[x][1]==board[x][2]) {
		        if (board[x][0]>0) {
		        	return "user";
		        } else {
		          return "computer";
		        }
		      }
		    }
		  }
		  //Across
		  for (int y=0; y<board.length; y++) {
		    if (board[0][y]!=0) {
		      if (board[0][y]==board[1][y]&&board[1][y]==board[2][y]) {
		        if (board[0][y]>0) {
		        	return "user";
		        } else {
		        	return "computer";
		        }
		      }
		    }
		  }

		  // down and left
		  
		  if (board[0][0]!=0) {
		   	if (board[0][0]==board[1][1]&&board[1][1]==board[2][2]) {
		      if (board[0][0]>0) {
		    	  return "user";
		      } else {
		    	  return "computer";
		      }
		    }
		  }
		  //down and right
		  if (board[2][0]==board[1][1]&&board[1][1]==board[0][2]) {
		    if (board[2][0]!=0) {
		      if (board[2][0]>0) {
		    	  return "user";
		      } else {
		    	  return "computer";
		      }
		    }
		  }

		  //full
		  if (board[0][0]!=0&&board[1][0]!=0&&board[2][0]!=0&&board[0][1]!=0&&board[1][1]!=0&&board[2][1]!=0&&board[0][2]!=0&&board[1][2]!=0&&board[2][2]!=0) {
		    return "draw";
		  }
		  
		  //if no winer yet and more moves to make
		  return "none";
		}
}
