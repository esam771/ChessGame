package chessGame;

import java.util.*;

public class mainChess {
	
	public Piece[][] board = new Piece[11][11];
	
	public void playGame()
	{
		System.out.println("type (end) to finish");
		printBoard();
		String a = mainChess.takeMove();
		
		if(a.compareTo("end") != 0)
		{
			processMove(a);
		}
	}
	
	public void processMove(String a)
	{
		String[] arr = a.split("");	
		int[] numarr = new int[4];

		numarr[0] = numValue(arr[0]);
		numarr[1] = Integer.parseInt(arr[1]);
		numarr[2] = numValue(arr[3]);
		numarr[3] = Integer.parseInt(arr[4]);
		
		System.out.print(numarr[0] +" "+ numarr[1] +" "+ numarr[2] +" "+ numarr[3]);
		
	}
	
	public int numValue(String str)
	{
		int i = -1;
		
        switch(str)
        {
            case "a": i = 1; break;
            case "b": i = 2; break;
            case "c": i = 3; break;
            case "d": i = 4; break;
            case "e": i = 5; break;
            case "f": i = 6; break;
            case "g": i = 7; break;
            case "h": i = 8; break;
            default: i = -1;
        }
        return i;
	}
	
	public static String takeMove()
	{
		Scanner scan = new Scanner(System.in);
		String ans = scan.nextLine();
		scan.close();
		return ans;
	}
	
	public void printBoard()
	{
		String[] letters = {"a","b","c","d","e","f","g","h"};
		
		for(int i = 0; i <= 10; i++)
		{
			for(int j = 0; j <= 10; j++)
			{
				if(board[i][j] != null)
					System.out.print(board[i][j].pieceType() + " ");
				else if(i == 0 || ( j == 0) || (i == 9) || (j == 9))
					System.out.print("  ");
				else if((i == 10) && (j != 10))
					System.out.print(letters[j-1] + "  ");
				else if((j == 10) && (i != 10))
					System.out.print((9-i));
				else
					System.out.print("   ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void generateBoard()
	{
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
			
		//order: boolean white, string type
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
