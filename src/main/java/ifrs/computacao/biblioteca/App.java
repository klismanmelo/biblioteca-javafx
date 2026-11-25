
package ifrs.computacao.biblioteca;

import ifrs.computacao.biblioteca.navigation.NavigationController;
import ifrs.computacao.biblioteca.navigation.Page;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Minha Biblioteca Pessoal");

        NavigationController navigationController = new NavigationController(stage);

        navigationController.navigateTo(Page.WELCOME);

        stage.show();
    }

}
