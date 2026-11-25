
package ifrs.computacao.biblioteca.navigation;

import ifrs.computacao.biblioteca.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class NavigationController {

    private final Stage stage;

    public NavigationController(Stage stage) {
        this.stage = stage;
    }

    public void navigateTo(Page page) {

        try {

            URL resource = App.class.getResource(page.getPath());

            if (resource == null) {
                throw new IllegalStateException(
                        "Arquivo FXML não encontrado: " + page.getPath());
            }

            FXMLLoader loader = new FXMLLoader(resource);

            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller instanceof NavigableController navigable) {
                navigable.setNavigationController(this);
            }

            if (stage.getScene() == null) {

                Scene scene = new Scene(root, 1100, 750);

                stage.setScene(scene);

            } else {

                stage.getScene().setRoot(root);

            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erro ao carregar a página: " + page,
                    e);

        }
    }
}
