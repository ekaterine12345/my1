module com.example.preperation_final_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;


    opens com.example.preperation_final_1 to javafx.fxml;
    exports com.example.preperation_final_1;
}