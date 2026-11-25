
module ifrs.computacao.biblioteca {

    requires javafx.controls;
    requires javafx.fxml;

    opens ifrs.computacao.biblioteca.controller to javafx.fxml;

    exports ifrs.computacao.biblioteca;
    exports ifrs.computacao.biblioteca.navigation;

}
