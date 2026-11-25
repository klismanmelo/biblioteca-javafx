module ifrs.computacao.biblioteca {

    requires javafx.controls;
    requires javafx.fxml;

    // JPA
    requires jakarta.persistence;

    // Hibernate
    requires org.hibernate.orm.core;

    // SQLite
    requires java.sql;

    opens ifrs.computacao.biblioteca.controller to javafx.fxml;

    // Permite ao Hibernate acessar os atributos das entidades
    opens ifrs.computacao.biblioteca.models to org.hibernate.orm.core;

    exports ifrs.computacao.biblioteca;
    exports ifrs.computacao.biblioteca.navigation;
}