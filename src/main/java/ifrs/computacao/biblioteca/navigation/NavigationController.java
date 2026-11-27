package ifrs.computacao.biblioteca.navigation;

import ifrs.computacao.biblioteca.App;
import ifrs.computacao.biblioteca.controller.SidebarController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class NavigationController {

    private static final String SIDEBAR_PATH = "/ifrs/computacao/biblioteca/views/sidebar-view.fxml";

    private final Stage stage;
    private final BorderPane shell = new BorderPane();
    private SidebarController sidebarController;

    public NavigationController(Stage stage) {
        this.stage = stage;
        initShell();
    }

    private void initShell() {
        try {
            URL resource = App.class.getResource(SIDEBAR_PATH);

            if (resource == null) {
                throw new IllegalStateException(
                        "Arquivo FXML não encontrado: " + SIDEBAR_PATH);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent sidebar = loader.load();

            sidebarController = loader.getController();
            sidebarController.setNavigationController(this);

            shell.setLeft(sidebar);

            Scene scene = new Scene(shell, 1100, 750);

            // CSS global (veja Passo 4)
            URL css = App.class.getResource(
                    "/ifrs/computacao/biblioteca/css/styles.css");
            if (css != null) {
                scene.getStylesheets().add(css.toExternalForm());
            }

            stage.setScene(scene);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar a sidebar", e);
        }
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

            shell.setCenter(root); // só o centro muda
            sidebarController.setActivePage(page);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar a página: " + page, e);
        }
    }
}