
package ifrs.computacao.biblioteca.controller;

import ifrs.computacao.biblioteca.navigation.NavigationController;
import ifrs.computacao.biblioteca.navigation.NavigableController;
import ifrs.computacao.biblioteca.navigation.Page;

import javafx.fxml.FXML;

public class BookFormController implements NavigableController {

    private NavigationController navigationController;

    @Override
    public void setNavigationController(
            NavigationController navigationController) {

        this.navigationController = navigationController;
    }

    @FXML
    private void onBackClick() {

        navigationController.navigateTo(Page.WELCOME);

    }

    @FXML
    private void onSaveBookClick() {

        // Futuramente implementaremos o cadastro do livro.

        navigationController.navigateTo(Page.BOOK_DETAILS);

    }
}
