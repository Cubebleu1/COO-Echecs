package model;

public class Fou extends AbstractPiece {
	
	// ========== CONSTRUCTORS ========== 

	public Fou(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}
	
	// ========== METHODS ========== 
	
	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		if ( Math.abs(xFinal-this.getX()) == Math.abs(yFinal-this.getY())) {
			if (this.setCoord(xFinal, yFinal)) {return true;}
			return false;
		} else {return false;}
	}

}
