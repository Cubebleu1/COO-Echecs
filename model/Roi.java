package model;

public class Roi extends AbstractPiece {
	
	// ========== ATTRIBUTES ==========

	public Roi(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}
	
	// ========== CONSTRUCTORS ==========

	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		if( Math.abs(this.getX() - xFinal) <= 1 && Math.abs(this.getY() - yFinal) <= 1 ) {
			if ( this.setCoord(xFinal, yFinal)) {return true;}
			return false;
		} else {return false;}
	}

}
