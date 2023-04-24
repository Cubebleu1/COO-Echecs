package model;

public class Reine extends AbstractPiece {
	
	// ========== CONSTRUCTORS ==========

	public Reine(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}

	// ========== METHODS ==========
	
	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		if(!Coord.coordonnees_valides(this.getX(), this.getY())
			|| (xFinal == this.getX() && yFinal == this.getY()) ) { //Return false if moving on the same coord ?
				return false;
		} else if ((xFinal == this.getX() || yFinal == this.getY()) //Moves like tower
				|| (Math.abs(xFinal-this.getX()) == Math.abs(yFinal-this.getY())) ) { //moves like bishop
			return true;
		} else {
			return false;
		}
	}

}
