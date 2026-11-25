
package ifrs.computacao.biblioteca.navigation;

public enum Page {

    WELCOME("/ifrs/computacao/biblioteca/views/welcome-view.fxml"),
    BOOK_FORM("/ifrs/computacao/biblioteca/views/book-form-view.fxml"),
    BOOK_DETAILS("/ifrs/computacao/biblioteca/views/book-details-view.fxml");

    private final String path;

    Page(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
