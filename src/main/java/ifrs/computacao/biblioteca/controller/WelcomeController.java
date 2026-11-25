
package ifrs.computacao.biblioteca.controller;

import ifrs.computacao.biblioteca.navigation.NavigationController;
import ifrs.computacao.biblioteca.navigation.NavigableController;
import ifrs.computacao.biblioteca.navigation.Page;

import javafx.fxml.FXML;

public class WelcomeController implements NavigableController {

    private NavigationController navigationController;

    @Override
    public void setNavigationController(
            NavigationController navigationController) {

        this.navigationController = navigationController;
    }

    @FXML
    private void onCreateBookClick() {

        navigationController.navigateTo(Page.BOOK_FORM);

    }

    @FXML
    private void onViewBooksClick() {

        navigationController.navigateTo(Page.BOOK_DETAILS);

    }
}
