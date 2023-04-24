package model;

public abstract class AbstractPiece implements Pieces {
	
	// ========== ATTRIBUTES ==========
	
	private Couleur couleur;
	private Coord coord;
	
	// ========== CONSTRUCTORS ==========
	
	public AbstractPiece(Couleur couleur, Coord coord) {
		if(!Coord.coordonnees_valides(coord.x, coord.y)) {
			throw new IllegalArgumentException("La piece est mal située !");
		}
		this.couleur = couleur;
		this.coord = coord;
	}
	
	// ========== METHODS ==========
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() 
				+ " aux coordonées " 
				+ this.coord.toString();
	}
	
	public boolean move(int xFinal, int yFinal) {
		if (this.isMoveOk(xFinal, yFinal)) {
			this.coord.x = xFinal;
			this.coord.y = yFinal; //Public attributes in coord :( ...
			return true;
		} else {return false;}
	}
	
	public boolean capture() {
		if (this.getX() + this.getY() == -2) {
			return false; //Cannot capture if piece is already captures (x = y = -1)
		} else {
			this.coord.x = -1;
			this.coord.y = -1;
			return true;
		}
	}
	
	// ========== GETTERS / SETTERS ==========
	
	public Couleur getCouleur() {return this.couleur;}
	
	public int getX() {return this.coord.x;}
	//Encapsulation ? Either way, never return coord, but always a copy !
	public int getY() {return this.coord.y;}
	
	// ========== TESTS ==========
	
	public static void main(String[] args) {
		System.out.println("========== TESTS TOUR ========== ");
		Pieces maTour = new Tour(Couleur.BLANC, new Coord(0,0));
		System.out.println(maTour);
		System.out.println(maTour.move(4, 4)); //False, diagonal
		System.out.println(maTour.move(0, 4)); //True, vertical
		System.out.println(maTour); //Update coords
		
		System.out.println("========== TESTS CAVALIER ========== ");
		Pieces monCavalier = new Cavalier(Couleur.BLANC, new Coord(0,0));
		System.out.println(monCavalier);
		System.out.println(monCavalier.move(0, 2)); //False, straight horizontal
		System.out.println(monCavalier.move(1, 2)); //True, L-shaped
		System.out.println(monCavalier.move(2, 40)); //False, out of the board
		System.out.println(monCavalier); //Update coords
		
		System.out.println("========== TESTS FOU ========== ");
		Pieces monFou = new Fou(Couleur.BLANC, new Coord(0,0));
		System.out.println(monFou);
		System.out.println(monFou.move(0, 2)); //False, straight horizontal
		System.out.println(monFou.move(4, 4)); //True, diagonal
		System.out.println(monFou); //Update coords
		
		System.out.println("========== TESTS REINE / DAME ==========");
		Pieces maReine = new Reine(Couleur.NOIR, new Coord(0,0));
		System.out.println(maReine);
		System.out.println(maReine.move(0, 2)); //True, tower movement
		System.out.println(maReine.move(2, 4)); // True, bishop movement
		System.out.println(maReine.move(3, 6)); // False, l-Shaped
		System.out.println(maReine);
	}
	
}