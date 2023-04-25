package model;

public class Tour extends AbstractPiece{
	
	// ========== CONSTRUCTORS ========== 
	
	public Tour(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}
	
	// ========== METHODS ========== 
	
	//NB : This method only look if move is possible independently of other Pieces object.
	// The Echiquier class is in charge of seeing if move is possible from the perspective
	// of other Pieces objects.
	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		if ( xFinal == this.getX() || yFinal == this.getY()) { //Moving vertically or horizontally
			if (this.setCoord(xFinal, yFinal)) {return true;}
			return false;
		} else {return false;}
	}

}
