package model;

public class Fou extends AbstractPiece {
	
	// ========== CONSTRUCTORS ========== 

	public Fou(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}
	
	// ========== METHODS ========== 
	
	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		if(!Coord.coordonnees_valides(this.getX(), this.getY())
			|| (xFinal == this.getX() && yFinal == this.getY()) ) { //Return false if moving on the same coord ?
				return false;
		} else if ( Math.abs(xFinal-this.getX()) == Math.abs(yFinal-this.getY()) ) {
			return true;
		} else {return false;}
	}

}
