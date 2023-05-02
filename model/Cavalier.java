package model;

public class Cavalier extends AbstractPiece {
	
	// ========== CONSTRUCTORS ========== 

	public Cavalier(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}
	
	// ========== METHODS ========== 

	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		if ((xFinal == this.getX()+2 && yFinal == this.getY()+1)  //L-shaped movement
		|| (xFinal == this.getX()+1 && yFinal == this.getY()+2)) {
			if (this.setCoord(xFinal, yFinal)) {return true;}
			return false;
		} else {return false;}
	}

}
