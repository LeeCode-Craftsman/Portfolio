module edu.gcu.cst120.battleship.battleship {
	requires javafx.controls;
	requires javafx.fxml;
	
	
	opens edu.gcu.cst120.battleship to javafx.fxml;
	exports edu.gcu.cst120.battleship;
}