module org.file.filemanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.file.filemanager to javafx.fxml;
    exports org.file.filemanager;
}