package ifrs.computacao.biblioteca.controller;

import ifrs.computacao.biblioteca.navigation.NavigableController;
import ifrs.computacao.biblioteca.navigation.NavigationController;
import ifrs.computacao.biblioteca.navigation.Page;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.EnumMap;
import java.util.Map;

public class SidebarController implements NavigableController {

    private NavigationController navigationController;

    @FXML
    private Button welcomeButton;
    @FXML
    private Button bookFormButton;
    @FXML
    private Button bookDetailsButton;

    private final Map<Page, Button> buttons = new EnumMap<>(Page.class);

    @FXML
    private void initialize() {
        buttons.put(Page.WELCOME, welcomeButton);
        buttons.put(Page.BOOK_FORM, bookFormButton);
        buttons.put(Page.BOOK_DETAILS, bookDetailsButton);
    }

    @Override
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

    @FXML
    private void onWelcomeClick() {
        navigationController.navigateTo(Page.WELCOME);
    }

    @FXML
    private void onBookFormClick() {
        navigationController.navigateTo(Page.BOOK_FORM);
    }

    @FXML
    private void onBookDetailsClick() {
        navigationController.navigateTo(Page.BOOK_DETAILS);
    }

    /** Destaca o botão da página ativa. */
    public void setActivePage(Page page) {
        buttons.forEach((p, button) -> {
            button.getStyleClass().remove("active");
            if (p == page) {
                button.getStyleClass().add("active");
            }
        });
    }
}