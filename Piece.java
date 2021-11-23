package chessGame;

public class Piece extends mainChess{
	
	public boolean isWhite = true;
	public String pieceType = "";

	public Piece(boolean isWhite, String pieceType)
	{
		this.isWhite = isWhite;
		this.pieceType = pieceType;
	}
	
	public boolean isWhite()
	{
		return(this.isWhite);
	}
	
	public String pieceType()
	{
		return(this.pieceType);
	}
}
