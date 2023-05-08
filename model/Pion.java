package model;

//X is column, Y is row

public class Pion extends AbstractPiece {
	private boolean firstMove = true;

	public Pion(Couleur couleur, Coord coord) {
		super(couleur, coord);
	}
	
	//TODO : Deplacer la verif set coord dans move() de l'abstract

	@Override
	public boolean isMoveOk(int xFinal, int yFinal) {
		if(((xFinal == this.getX() && Math.abs(yFinal-this.getY()) == 2) // First move : two spaces
		||  (xFinal == this.getX() && Math.abs(yFinal-this.getY()) == 1))
		&& this.firstMove) {
			if(this.setCoord(xFinal, yFinal)){this.firstMove=false; return true;}
			return false;
		} else if (xFinal == this.getX() && Math.abs(yFinal-this.getY()) == 1){ // Other moves : one space
			if(this.setCoord(xFinal, yFinal)){return true;}
			return false;
		} else {return false;}
	}
}
