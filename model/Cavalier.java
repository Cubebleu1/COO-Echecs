package model;

public class Cavalier extends AbstractPiece {
	
	// ========== CONSTRUCTORS ========== 

	public Cavalier(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}
	
	// ========== METHODS ========== 

	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		if(!Coord.coordonnees_valides(this.getX(), this.getY())
			|| (xFinal == this.getX() && yFinal == this.getY()) ) { //Return false if moving on the same coord ?
				return false;
		} else if ((xFinal == this.getX()+2 && yFinal == this.getY()+1)  //L-shaped movement
				|| (xFinal == this.getX()+1 && yFinal == this.getY()+2)) {
			return true;
		} else {return false;}
	}

}
