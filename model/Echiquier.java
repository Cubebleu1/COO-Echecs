package model;

import java.util.ArrayList;
import java.util.List;

public class Echiquier implements BoardGames {
	
	// ========== ATTRIBUTES ========== 
	
	private Jeu WGame;
	private Jeu BGame;
	private Jeu currentGame;
	private Jeu notCurrentGame;
	private String message;
	
	// ========== CONSTRUCTORS ========== 
	
	public Echiquier() {
		this.WGame = new Jeu(Couleur.BLANC);
		this.BGame = new Jeu(Couleur.NOIR);
		this.currentGame = this.WGame;
		this.notCurrentGame = this.BGame;
	}
	
	// ========== METHODS ========== 

	@Override
	public String toString() {
		return "===== Jeu Blanc =====\n" + this.WGame.toString() + "\n===== Jeu Noir =====\n" + this.BGame.toString();
	}
	
	//Needs isPieceHere and getPiece to be public... SRP ??? TODO : Game interface ???
	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal) {
		if (xInit == xFinal && yFinal == yInit){return false;} //No movement
		Pieces piece = this.currentGame.getPiece(xInit, yInit);
		if(!(piece instanceof JumpOver)) { //No need to check if Piece goes over foes
			int x = 0; int y = 0; //coordinates between xInit and xFinal
			int dx = Math.abs(xFinal-xInit) ; int dy = Math.abs(yFinal-yInit); //number of spaces for each dimension
			//Vertical : dy = 0 ;  Horizontal : dx = 0 ; Diagonal : dx = dy
			for (int i = 0; i < Math.max(dx, dy)-1; i++) { //-1 since we want the last square to be tested for capture
				// increments x and y while reaching destination. Stop incrementing when final is reached.
				if (xFinal < xInit && x > -dx) {x = x-1;} else if (xFinal > xInit && x < dx) {x++;}
				//-dx since x is descending : starts at x=0, then at x = xInit-xFinal = -dx
				// => if xInit = 7 and xFinal = 5, dx = 2 and x=0, then x=-1, then x=-2
				if (yFinal < yInit && y > -dy) {y = y-1;} else if (yFinal > yInit && y < dy) {y++;}
				if (this.notCurrentGame.isPieceHere(xInit+x, yInit+y)) {return false;};
			} 
		if (this.notCurrentGame.isPieceHere(xFinal, yFinal)) {this.notCurrentGame.capture(xFinal, yFinal);} //Capture ?? But why in Jeu... ?
		} 
		return true;
	}
	
	@Override
	public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
		if(!this.isPlayerOK(xInit, yInit)) {return false;} //Wrong player
		if(this.isMoveOk(xInit, yInit, xFinal, yFinal)) {
			if (this.currentGame.move(xInit, yInit, xFinal, yFinal)) { //Cascading call to Jeu and corresponding pieces move() method
				this.switchJoueur(); //If move was legal, switch player
				return true;
			};
		} 
		return false;
	}

	@Override
	public boolean isEnd() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void switchJoueur() {
		Jeu tmp = this.currentGame;
		this.currentGame = this.notCurrentGame;
		this.notCurrentGame = tmp;
	}
	
	public boolean isPlayerOK(int xInit, int yInit) {
		if(this.currentGame.getPiece(xInit, yInit) == null) {return false;}
		return true;
	}
	
	// ========== GETTERS / SETTERS ========== 
	
	@Override
	public String getMessage() {return this.message;}
	
	private void setMessage(String msg) {this.message=msg;}
	
	@Override
	public Couleur getColorCurrentPlayer() {return this.currentGame.getCouleur();}
	
	@Override
	public Couleur getPieceColor(int x, int y) {
		if (this.WGame.isPieceHere(x, y)) {
			return this.WGame.getCouleur();
		} else if (this.BGame.isPieceHere(x, y)) {
			return this.BGame.getCouleur();
		} else {return null;}
	}
	
	public List<PieceIHM> getPiecesIHM(){
		List<PieceIHM> list = new ArrayList<>();
		list.addAll(this.currentGame.getPiecesIHM());
		list.addAll(this.notCurrentGame.getPiecesIHM());
		return list;
	}
	
	// ========== TEST ========== 
	
	public static void main(String[] args) {
		Echiquier monEchiquier = new Echiquier();
		System.out.println(monEchiquier);
		System.out.println(monEchiquier.move(0, 6, 0, 5)); // True
		System.out.println(monEchiquier.move(0, 5, 0, 4)); // False : not white player turn
		System.out.println(monEchiquier.move(1, 0, 0, 2)); // True
		
	}
}
