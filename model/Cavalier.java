package model;

public class Cavalier extends AbstractPiece implements JumpOver{
	
	// ========== CONSTRUCTORS ========== 

	public Cavalier(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}
	
	// ========== METHODS ========== 

	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		int dx = Math.abs(xFinal - this.getX());
		int dy = Math.abs(yFinal - this.getY());
		if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) { //L-shaped movement
			if (this.setCoord(xFinal, yFinal)) {return true;}
			return false;
		} else {return false;}
	}

}
