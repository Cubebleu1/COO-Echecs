package model;

public class Reine extends AbstractPiece {
	
	// ========== CONSTRUCTORS ==========

	public Reine(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}

	// ========== METHODS ==========
	
	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		if ((xFinal == this.getX() || yFinal == this.getY()) //Moves like tower
		|| (Math.abs(xFinal-this.getX()) == Math.abs(yFinal-this.getY()))) { //moves like bishop 
			if(this.setCoord(xFinal, yFinal)) {return true;}
			return false;
		} else {return false;}
	}

}
