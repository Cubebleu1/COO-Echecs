package model;

import java.util.LinkedList;
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
		if (xInit == xFinal && yFinal == yInit){return false;} //No movement
		//String pieceClass = this.getPieceType(xInit, yInit);
		Pieces piece = this.getPiece(xInit, yInit);
		if (piece instanceof JumpOver) {return !this.isPieceHere(xFinal, yFinal);} //Jump over friends if not on destination
		else {
			int x = 0; int y = 0;
			int dx = xFinal-xInit ; int dy = yFinal-yInit; //br de cases dans chaque dimmension
			for (int i = 0; i < Math.max(dx, dy); i++) {
				// On incrémente x/y si o navance (xFinal > xInit), sinon on le décrémente. Si ils sont égaux
				// le variation (dx == x/dy == y) 
				if (xFinal < xInit && x > dx) {x = x-1;} else if (xFinal > xInit && x < dx) {x++;}
				if (yFinal < yInit && y > dy) {y = y-1;} else if (xFinal > xInit && y < dy) {y++;}
				if (this.isPieceHere(xInit+x, yInit+y)) {return false;};
			} 
		} 
		return true;
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
	
	private String getPieceType(int x, int y) {return this.getPiece(x, y).getClass().getSimpleName();}
	
	public List<PieceIHM> getPiecesIHM() {
		PieceIHM newPieceIHM = null;
		List<PieceIHM> list = new LinkedList<PieceIHM>();
		for (Pieces piece : this.pieces) {
			boolean exist = false;
			for (PieceIHM pieceIHM : list) {
				if ((pieceIHM.getTypePiece()).equals(piece.getClass().getSimpleName())) {
					exist = true;
					if (piece.getX()!=-1) {
						pieceIHM.add(new Coord(piece.getX(), piece.getY()));
					}
				}
			}
			if (!exist) {
				if (piece.getX() != -1) {
					newPieceIHM = new PieceIHM(piece.getClass().getSimpleName(), piece.getCouleur());
					newPieceIHM.add(new Coord(piece.getX(), piece.getY()));
					list.add(newPieceIHM);
				}
			}
		}
		return list;
	}
	
	private Couleur getPieceColor(int x, int y) { //Piece forcement de la couleur du jeu... a remplacé par getCouleur()...?
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
		System.out.println(monJeu.getPiecesIHM());
	}
}
