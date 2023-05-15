package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import controler.ChessGameControlers;
import controler.controlerLocal.ChessGameControler;
import model.Coord;
import model.Couleur;
import model.PieceIHM;
import model.Pieces;
import model.observable.ChessGame;
import tools.ChessImageProvider;
import tools.ChessPiecesFactory;

@SuppressWarnings("deprecation")
public class ChessGameGUI extends JFrame implements MouseMotionListener, MouseListener, Observer {
	
	// ========== ATTRIBUTES ==========
	
	// Some attributes here are just a way to make variable communicate through methods
	
	JLayeredPane layeredPane; //Allows multiple component to be on same pane (z axis) (Here : ChessBoard Panel + Pieces Panel
	JPanel chessBoard; 
	JLabel chessPiece; //Chess piece being moved
	int xAdjustment ; int yAdjustment;
	ChessGameControlers controler;
	Coord coordInit;
	Coord coordFinal;
	
	
	// ========== CONSTRUCTORS ==========
	
	public ChessGameGUI(String name, ChessGameControlers chessControler, Dimension dim ) {
		this.controler = chessControler;
		
		// Create a Layered Plane for the application
		this.layeredPane = new JLayeredPane();
		this.getContentPane().add(this.layeredPane); //Add layered pane to this (ChessGameUI) frame
		this.layeredPane.setPreferredSize(dim);
		this.layeredPane.addMouseListener(this); //Add this as mouse listener (possible since it implements the necessary interface
		this.layeredPane.addMouseMotionListener(this); //Same
		
		// Create a chess board Panel
		this.chessBoard = new JPanel();
		this.layeredPane.add(this.chessBoard, JLayeredPane.DEFAULT_LAYER); // JLayeredPane.DEFAULT_LAYER <=> Bottom layer (z=0)
		this.chessBoard.setLayout(new GridLayout(8,8)); //Use a grid layout for the chess board
		this.chessBoard.setPreferredSize(dim);
		this.chessBoard.setBounds(0, 0, dim.width, dim.height); //Resize board to fit layered pane
		
		// TODO : How does this works ?
		
		for (int i = 0; i < 64; i++) {
			JPanel square = new JPanel(new BorderLayout()); //Why Border Layout ? Because it is simple
			this.chessBoard.add(square);
			int row = (i / 8) % 2;
			if (row == 0) {square.setBackground( i % 2 == 0 ? Color.black : Color.white );}
			else {square.setBackground( i % 2 == 0 ? Color.white : Color.black);}
		}
	}
	
	// ========== METHODS ==========

	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.chessPiece == null) return;
		this.chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);	
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		coordInit = this.toCoords(e.getX(), e.getY());
		this.chessPiece = null;
		Component comp = this.chessBoard.findComponentAt(e.getX(), e.getY()); // Get component that was clicked on
		if (comp instanceof JPanel) return; //If comp is anything else than JLabel, then it's not a chess piece : nothing is done
		if(this.controler.isPlayerOK(coordInit)) {
			Point parentLocation = comp.getParent().getLocation(); //Get position of square where the piece is
			comp.getParent().remove(comp); //Remove chessPiece from it's initial square
			this.xAdjustment = parentLocation.x - e.getX(); //Get adjustment to chess piece position compared with mouse position
			this.yAdjustment = parentLocation.y - e.getY();
			this.chessPiece = (JLabel) comp; //Save and cast comp to JLabel since we know it is one (overwise would have returned)
			this.chessPiece.setLocation(parentLocation.x, parentLocation.y);
			this.chessPiece.setSize(this.chessPiece.getWidth(), this.chessPiece.getHeight()); //prevent piece from changin size when clicked
			this.layeredPane.add(this.chessPiece, JLayeredPane.DRAG_LAYER);//JLayeredPane.DRAG_LAYER <=> above all components (z=max=400)	
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.chessPiece == null) return;
		else {
			this.coordFinal = this.toCoords(e.getX(), e.getY());
			Component comp = this.chessBoard.findComponentAt(e.getX(), e.getY());
			if (this.controler.move(coordInit, coordFinal)) {
			} else {this.undoMove(); System.out.println("UNDO");}
			//this.layeredPane.remove(this.chessPiece);
			this.chessPiece.setVisible(false);
			this.chessPiece= null;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Observable o, Object arg) {
		this.clearBoard();
		if (arg instanceof Iterable) {
			for (PieceIHM piece : (Iterable<PieceIHM>) arg) {
				String img = ChessImageProvider.getImageFile(piece.getTypePiece(), piece.getCouleur());
				List<Coord> coords = piece.getList();
				for (Coord coord : coords) {
					int componentNumber = coord.y*8+coord.x; //JGridLayout is filled from left to right, top to bottom
					JLabel label = new JLabel(new ImageIcon(img)); //Chess piece label
					JPanel panel = (JPanel) this.chessBoard.getComponent(componentNumber);
					panel.add(label); //Add JLabel above the JPanel square
				}
			}
		} else {System.err.println("PiecesIHM error");}
		revalidate();
		repaint();
	}
	
	public void clearBoard() {
	    Component[] components = chessBoard.getComponents();
	    for (Component component : components) {
	        if (component instanceof JPanel) {
	            JPanel panel = (JPanel) component;
	            panel.removeAll(); // Supprime tous les JLabel contenus dans le JPanel
	        }
	    }
	}
	
	private Coord toCoords(int x, int y) {
		int caseSize = 700 / 8; // Taille de chaque case
	    x = x / caseSize; // Calcul de la case en X
	    y = y / caseSize; // Calcul de la case en Y
	    return new Coord(x, y);
	}
	
	private void undoMove() {
		//this.layeredPane.remove(this.chessPiece);
		JPanel panel = (JPanel) this.chessBoard.getComponent(this.coordInit.y*8+this.coordInit.x); //Put chess piece back on initial square
		this.layeredPane.remove(this.chessPiece);
		panel.add(this.chessPiece);
		//this.layeredPane.remove(this.chessPiece); //Remove dragged piece since it is now placed on chess board
		this.chessPiece.setVisible(true);
		this.chessPiece = null;
		this.chessBoard.revalidate();
		this.chessBoard.repaint();
		
	}

}
