package chessGame;

import java.util.*;

public class mainChess {
	
	/* 
	 * "rules" to see input format and possible commands
	 * 
	 * things left to do
	 * -can't exchange pawns for other pieces
	 * -can still move through pieces
	 */
	
	public Piece[][] board = new Piece[10][11];
	public int turn = 1;
	static Scanner scan = new Scanner(System.in);
	public int[] lastMove = new int[4]; //if ever want to undo
	
	public void playGame()
	{
		//main move loop for game
		System.out.println();
		printMove();
		String move = ""; //getting input from static method
		move = mainChess.takeMove();
		
		while(move.compareTo("end") != 0) //ends if end is typed
		{
			processMove(move); //changes board based on move input
			printMove();
			move = mainChess.takeMove();
		}
	}
	
	public void processMove(String a)
	{
		//evaluates string input for each move

		if(a.compareTo("undo") == 0)
			undoMove();
		else if(a.compareTo("rules") == 0)
			printRules();
		else
		{
			String[] arr = a.split("");	//splitting input
			int[] numarr = new int[4]; //coordinate array
			boolean valid = true; //if move can be made
			String output = "";
				
			try { //catches improper inputs
				numarr[0] = numValue(arr[0]);
				numarr[1] = Integer.parseInt(arr[1]);
				numarr[2] = numValue(arr[3]);
				numarr[3] = Integer.parseInt(arr[4]);
				
				if(board[Math.abs(numarr[1]-8) + 1][numarr[0]] == null)
				{
					valid = false; //if trying to move from null spot
					output = "can't move from empty spot";
				}
			} catch(Exception e) { 
				valid = false; //designates input as invalid
				output = "input format incorrect";
			}
			
			if(valid && output.compareTo("") == 0) //required because of try catch
				output = legalMove(numarr); 
			
			if(valid && output.compareTo("") == 0) //if on board and legal
				changePosition(numarr);
			else 
				System.out.println(output + ", try again\n"); 
		}
	}
	
	public String legalMove(int[] numarr)
	{
		String output = "";
		
		int x1 = Math.abs(numarr[1]-8) + 1; //converting to normal coord system
		int y1 = numarr[0];
		int x2 = Math.abs(numarr[3]-8) + 1;
		int y2 = numarr[2];
		
		if((x1 == x2) && (y1 == y2))
			return "cant move to same place"; //if moving to same place
		
		for(int i = 0; i < numarr.length; i++)
		{
			if((numarr[i] > 8) || (numarr[i] < 1))
			{
				return "can't move off board"; //if move would be off board
			}
		}

		if(board[x2][y2] != null) //if trying to capture on same color
			if((board[x1][y1].isWhite() == board[x2][y2].isWhite()))
				return "can't capture on same color";
		
		//
		// color movement is not currently turn specific
		//
		
		String type = board[x1][y1].pieceType();
		int x = Math.abs(x2-x1);
		int y = Math.abs(y2-y1);
		
		switch(type) { //logic for specific pieces
		
	        case "r": //rook
	        	 if(x != 0 && y != 0)
	        	 	output = "can't move a rook look that";
	        	break;
	        	
	        case "k": //knight
	        	if(x == 1)
	        		if(y != 2)
	        			output = "can't move a knight like that";
	        	else if(x == 2)
	        		if(y != 1)
	        			output = "can't move a knight like that";
	        	else
	        		output = "can't move a knight like that";
	        	break;
	        	
	        case "b": //bishop
	        	if(x != y)
	        		output = "can't move a bishop non-diagonally";
	        	break;
	        	
	        case "q": //queen
	        	if(x != y && x != 0 && y != 0)
	        		output = "can't move a queen like that";
	        	break;
	        	
	        case "K": //king
	        	if(x > 1 || y > 1)
	        		output = "can't move a king that far";
	        	break;
	        	
	        case "p": //pawn
	        	if(x + y > 2)
	        		output = "can't move a pawn that far";
	        	break;
	        	
	        default: output = ""; }
		
		return output;
	}
	
	public void changePosition(int[] numarr)
	{
		//moves pieces based on input from processMove()
		turn++;
		lastMove[0] = numarr[0]; lastMove[1] = numarr[1]; 
		lastMove[2] = numarr[2]; lastMove[3] = numarr[3]; 
		
		int x1 = Math.abs(numarr[1]-8) + 1; //converting to normal coord system
		int y1 = numarr[0];
		int x2 = Math.abs(numarr[3]-8) + 1;
		int y2 = numarr[2];
		
		board[x2][y2] = new Piece(board[x1][y1].isWhite(), 
				board[x1][y1].pieceType(), 
				board[x1][y1].pieceColor()); //moving piece
		
		board[x1][y1] = null; //removing old place
	}
	
	public void undoMove()
	{
		if (turn > 1)
		{
			turn -= 2;
			int[] numarr = new int[4];
			numarr[0] = lastMove[2];
			numarr[1] = lastMove[3];
			numarr[2] = lastMove[0];
			numarr[3] = lastMove[1]; //reverses last stored move
			changePosition(numarr); //moves based on inverse of stored
		}
		else {
			System.out.println("cannot undo without moving first");
		}
	}
	
	public void printRules()
	{
		System.out.println(" Movement format: e2 e4");
		System.out.println(" (undo) to undo move");
		System.out.println(" (end) to close program");
	}
	
	public void printMove()
	{
		//prints current turn and board, requires method "printBoard()"
		System.out.println();
		System.out.print(" Turn: " + turn);
		
		if(turn % 2 == 1)
			System.out.print(", White's turn\n\n");
		else
			System.out.print(", Black's turn\n\n");
		
		System.out.print(" -------------------------");
		printBoard(); //outputs board
	}
	
	public void printBoard()
	{
		//only prints contents of 2d array
		String[] letters = {"a","b","c","d","e","f","g","h","",""};
		
		for(int i = 0; i <= 9; i++)
		{
			System.out.print(" ");
			for(int j = 0; j <= 10; j++)
			{
				if(board[i][j] != null)
					System.out.print(board[i][j].pieceColor() + board[i][j].pieceType() + "|");
				
				else if(i == 0)
						System.out.print("");

				else if((i == 10) && (j == 0))
					System.out.print(" ");
				
				else if((i == 9) && (j == 0))
					System.out.print(" ");
				
				else if((i == 0) || ( j == 0))
					System.out.print("|"); //formats blank area
				
				else if((j == 9))
					System.out.print(""); //formats blank area
				
				else if((i == 9))
					System.out.print(letters[j-1] + "  "); //prints columns
				
				else if((j == 10) && (i != 10))
				{
					System.out.print(" " + (9-i)); //prints edge nums
					System.out.print("\n -------------------------");
				}
				
				else if((j == 10) && (i == 10))
					System.out.print("");
				
				else
					System.out.print("  |");
			}
			System.out.println("");
		}
		System.out.println("_____________________________");
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
            case "h": i = 8; break;
            default: i = -1; }
        return i;
	}
	
	public static String takeMove()
	{
		//used with global scanner to accept string input
		String ans = scan.nextLine();
		return ans;
	}

	public void generateBoard()
	{
		//generates Piece variables on 2d array, only used once
		board[8][1] = new Piece(true, "r", "w"); //white rooks
		board[8][8] = new Piece(true, "r", "w");
		board[8][2] = new Piece(true, "k", "w"); //white knights
		board[8][7] = new Piece(true, "k", "w");
		board[8][3] = new Piece(true, "b", "w"); //white bishops
		board[8][6] = new Piece(true, "b", "w");
		board[8][4] = new Piece(true, "q", "w"); //white queen
		board[8][5] = new Piece(true, "K", "w"); //white king
		
		for(int i = 1; i <= 8; i++) //white pawns
			board[7][i] = new Piece(true, "p", "w");

		board[1][1] = new Piece(true, "r", "b"); //black rooks
		board[1][8] = new Piece(false, "r", "b");
		board[1][2] = new Piece(false, "k", "b"); //black knights
		board[1][7] = new Piece(false, "k", "b");
		board[1][3] = new Piece(false, "b", "b"); //black bishops
		board[1][6] = new Piece(false, "b", "b");
		board[1][4] = new Piece(false, "q", "b"); //black queen
		board[1][5] = new Piece(false, "K", "b"); //black king
		
		for(int i = 1; i <= 8; i++) //black pawns
			board[2][i] = new Piece(false, "p", "b");
	}
}
