module com.example.reprodutormusicajavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.reprodutormusicajavafx to javafx.fxml;
    exports com.example.reprodutormusicajavafx;
}