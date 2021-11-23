package chessGame;

import java.util.*;

public class mainChess {
	
	/*
	 * game works by entering piece you want to move,
	 * then entering place you want to move it, eg. "e2 e4"
	 * 
	 * currently doesn't check if moves are legal or not, 
	 * but won't let you move pieces off the 8x8 board
	 * 
	 * if incorrect move is made, just use opponents turn 
	 * to put it back and then redo original move
	 */
	
	
	public Piece[][] board = new Piece[11][11];
	public int turn = 1;
	static Scanner scan = new Scanner(System.in);
	
	public void playGame()
	{
		//main move loop for game
		System.out.println();
		printMove();
		String move = "e2 e5"; //getting input from static method
		
		while(move.compareTo("end") != 0) //ends if end is typed
		{
			move = mainChess.takeMove();
			processMove(move); //changes board based on move input
			printMove();
		}
	}
	
	public void processMove(String a)
	{
		//evaluates string input for each move
		if(a.compareTo("end") == 0) //when ending game
			{
			scan.close();
			System.exit(0);
			}
		else
			{
			//requires method "changePosition()"
			String[] arr = a.split("");	
			int[] numarr = new int[4];
	
			numarr[0] = numValue(arr[0]);
			numarr[1] = Integer.parseInt(arr[1]);
			numarr[2] = numValue(arr[3]);
			numarr[3] = Integer.parseInt(arr[4]);
			
			boolean valid = true;
			for(int i = 0; i < numarr.length; i++)
			{
				if((numarr[i] > 8) || (numarr[i] < 1))
				{
					valid = false;
				}
			}
			
			if(!valid)
				System.out.println("not valid");
			else
				changePosition(numarr);
		}
	}
	
	public void changePosition(int[] numarr)
	{
		//moves pieces based on input from processMove()
		int i1 = numarr[1];
		int j1 = numarr[0];
		
		int i2 = numarr[3];
		int j2 = numarr[2];
		
		board[Math.abs(i2-8) + 1][j2] = new Piece(board[Math.abs(i1-8) + 1]
				[j1].isWhite(), board[Math.abs(i1-8) + 1][j1].pieceType());
		
		board[Math.abs(i1-8) + 1][j1] = null;
	}
	
	public void printMove()
	{
		//prints current turn and board, requires method "printBoard()"
		System.out.println("move format: (a1 b2) or (end) to end");
		System.out.print("Turn: " + turn);
		
		if(turn % 2 == 1)
			System.out.print(" White's turn\n");
		else
			System.out.print(" Black's turn\n");
	
		turn++;
		printBoard(); //outputs board
	}
	
	public void printBoard()
	{
		//only prints contents of 2d array
		String[] letters = {"a","b","c","d","e","f","g","h"};
		
		for(int i = 0; i <= 10; i++)
		{
			for(int j = 0; j <= 10; j++)
			{
				if(board[i][j] != null)
					System.out.print(board[i][j].pieceType() + " ");
				else if(i == 0 || ( j == 0) || (i == 9) || (j == 9))
					System.out.print("  "); //formats blank area
				else if((i == 10) && (j != 10))
					System.out.print(letters[j-1] + "  "); //prints columns
				else if((j == 10) && (i != 10))
					System.out.print((9-i)); //prints edge nums
				else
					System.out.print("   ");
			}
			System.out.println();
		}
		System.out.println("\n**************************");
	}

	public int numValue(String str)
	{
		//used to convert chess coordinate input to array index
		int i = -1;
        switch(str) {
            case "a": i = 1; break;
            case "b": i = 2; break;
            case "c": i = 3; break;
            case "d": i = 4; break;
            case "e": i = 5; break;
            case "f": i = 6; break;
            case "g": i = 7; break;
            case "h": i = 8; break; //backwards to work with board
            default: i = -1; }
        return i;
	}
	
	public static String takeMove()
	{
		//used with global scanner to accept input
		String ans = scan.nextLine();
		return ans;
	}

	public void generateBoard()
	{
		//generates Piece variables on 2d array, only used once
		//order: boolean white, string type
		board[8][1] = new Piece(true, "wr"); //white rooks
		board[8][8] = new Piece(true, "wr");
		
		board[8][2] = new Piece(true, "wk"); //white knights
		board[8][7] = new Piece(true, "wk");
		
		board[8][3] = new Piece(true, "wb"); //white bishops
		board[8][6] = new Piece(true, "wb");
		
		board[8][4] = new Piece(true, "wq"); //white queen
		board[8][5] = new Piece(true, "wK"); //white king
		
		for(int i = 1; i <= 8; i++) //white pawns
			board[7][i] = new Piece(true, "wp");

		board[1][1] = new Piece(true, "br"); //black rooks
		board[1][8] = new Piece(false, "br");
		
		board[1][2] = new Piece(false, "bk"); //black knights
		board[1][7] = new Piece(false, "bk");
		
		board[1][3] = new Piece(false, "bb"); //black bishops
		board[1][6] = new Piece(false, "bb");
		
		board[1][4] = new Piece(false, "bq"); //black queen
		board[1][5] = new Piece(false, "bK"); //black king
		
		for(int i = 1; i <= 8; i++) //black pawns
			board[2][i] = new Piece(false, "bp");
	}
}
