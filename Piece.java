package chessGame;

public class Piece extends mainChess{
	
	public boolean isWhite = true;
	public String pieceType = "";
	public String pieceColor = "";

	public Piece(boolean isWhite, String pieceType, String pieceColor)
	{
		this.isWhite = isWhite;
		this.pieceType = pieceType;
		this.pieceColor = pieceColor;
	}
	
	public boolean isWhite()
	{
		return(this.isWhite);
	}
	
	public String pieceType()
	{
		return(this.pieceType);
	}
	
	public String pieceColor()
	{
		return(this.pieceColor);
	}
}
