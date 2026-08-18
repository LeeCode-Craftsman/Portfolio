module com.leemra.meterReader {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
	requires com.io7m.digal.core;
	requires java.desktop;
	
	
	opens com.leemra.meterReader to javafx.fxml;
    exports com.leemra.meterReader;
}