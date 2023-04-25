package model;

import java.util.List;
import tools.ChessPiecesFactory;

//x=0 ; y=0 is in the top left corner, with black at the bottom

public class Jeu {
	
	// ========== ATTRIBUTES ==========
	
	private List<Pieces> pieces;
	private Couleur couleur;
	private Pieces king;
	
	// ========== CONSTRUCTORS ==========
	
	public Jeu(Couleur couleur) {
		this.pieces = ChessPiecesFactory.newPieces(couleur);
		for (int i = 0; i < this.pieces.size(); i++) { //Looking for the king
			if (this.pieces.get(i).getClass().getSimpleName().equals("Roi")) { this.king =this.pieces.get(i); break;}}
		this.couleur = couleur;
	}
	
	// ========== METHODS ==========
	
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < this.pieces.size(); i++) {
			str = str + this.pieces.get(i).toString()+"\n";
		}
		return str;
	}

	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal) {
		if (this.isPieceHere(xFinal, yFinal)){return false;} //No friendly piece can step on another friendly piece
		return true; //TODO : cas cavalier, cas autres 'faire isPieceHere sur le trajet
	}
	
	private boolean isPieceHere(int x, int y) {
		for (int i = 0; i < this.pieces.size(); i++) {
			if (this.pieces.get(i).getX() == x && this.pieces.get(i).getY() == y ) {return true;}
		} return false;
	}
	
	
	
	// ========== GETTERS/SETTERS ==========
	
	public Couleur getCouleur() {return this.couleur;} //Pouquoi ??? L apiece est forcement de la meme couleur que le jeu...
	
	private Coord getKingCoord() {return new Coord(this.king.getX(), this.king.getY());}
	
	private Pieces getPiece(int x, int y) {
		for (int i = 0; i < this.pieces.size(); i++) {
			if (this.pieces.get(i).getX() == x && this.pieces.get(i).getY() == y ) {return this.pieces.get(i);}
		} return null;
	}
	
	private Couleur getPieceColor(int x, int y) {
		Pieces piece = this.getPiece(x, y);
		if (piece != null) {return piece.getCouleur();}
		return null;
	}
	
	// ========== TESTS ==========
	
	public static void main(String[] args) {
		Jeu monJeu = new Jeu(Couleur.BLANC);
		System.out.println(monJeu);
		System.out.println(monJeu.getKingCoord());
		System.out.println(monJeu.getPiece(0, 1)); //Null
		System.out.println(monJeu.getPiece(0, 7)); //tower
		System.out.println(monJeu.getPieceColor(0, 7));
	}
}
