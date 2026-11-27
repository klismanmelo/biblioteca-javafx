module ifrs.computacao.biblioteca {

    requires javafx.controls;
    requires javafx.fxml;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;

    // Driver SQLite (módulo automático, se aplicável)
    requires org.xerial.sqlitejdbc;

    opens ifrs.computacao.biblioteca.controller to javafx.fxml;
    opens ifrs.computacao.biblioteca.models to org.hibernate.orm.core;

    exports ifrs.computacao.biblioteca;
    exports ifrs.computacao.biblioteca.navigation;
}