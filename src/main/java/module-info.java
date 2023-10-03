module github.dragonatte.tresenraya {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens github.dragonatte.tresenraya to javafx.fxml;
    exports github.dragonatte.tresenraya;
}