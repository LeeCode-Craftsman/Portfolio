module com.leemra.battleship.battleship {
	requires javafx.controls;
	requires javafx.fxml;
	
	
	opens com.leemra.battleship to javafx.fxml;
	exports com.leemra.battleship;
}