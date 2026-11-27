package ifrs.computacao.biblioteca.controller;

import ifrs.computacao.biblioteca.models.Book;
import ifrs.computacao.biblioteca.models.BookStatus;
import ifrs.computacao.biblioteca.navigation.NavigationController;
import ifrs.computacao.biblioteca.navigation.NavigableController;
import ifrs.computacao.biblioteca.navigation.Page;
import ifrs.computacao.biblioteca.service.ServiceFactory;
import ifrs.computacao.biblioteca.service.BookService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class BookFormController implements NavigableController {

    private NavigationController navigationController;

    private BookService bookService;

    // CAMPOS DO FORMULÁRIO

    @FXML
    private TextField titleField;

    @FXML
    private TextField subtitleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField publisherField;

    @FXML
    private TextField isbnField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField languageField;

    @FXML
    private TextField pagesField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField priorityField;

    @FXML
    private TextField tagsField;

    @FXML
    private TextField ratingField;

    @FXML
    private ToggleGroup statusGroup;

    // INICIALIZAÇÃO

    @FXML
    private void initialize() {
        bookService = ServiceFactory.createBookService();
    }

    // NAVEGAÇÃO

    @Override
    public void setNavigationController(
            NavigationController navigationController) {

        this.navigationController = navigationController;
    }

    // CADASTRAR LIVRO

    @FXML
    private void onSaveBookClick() {

        try {

            // Capturar dados da interface

            String title = titleField.getText();

            String isbn = isbnField.getText();

            String authors = authorField.getText();

            Integer pages = parseInteger(pagesField.getText());

            Integer year = parseInteger(yearField.getText());

            BookStatus status = getSelectedStatus();

            // Delegar cadastro ao Service

            Book book = bookService.registerBook(
                    title,
                    isbn,
                    pages,
                    year,
                    status,
                    authors);

            // Feedback de sucesso

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Livro cadastrado",
                    "O livro \"" + book.getTitle() +
                            "\" foi adicionado à sua biblioteca.");

            // Navegar após cadastro

            navigationController.navigateTo(Page.WELCOME);

        } catch (NumberFormatException e) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Valor inválido",
                    "Verifique os campos de páginas e ano.");

        } catch (IllegalArgumentException e) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Dados inválidos",
                    e.getMessage());

        } catch (Exception e) {

            e.printStackTrace();

            showAlert(
                    Alert.AlertType.ERROR,
                    "Erro ao cadastrar",
                    "Não foi possível salvar o livro.");
        }
    }

    // CONVERTER CAMPOS NUMÉRICOS

    private Integer parseInteger(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        return Integer.parseInt(value.trim());
    }

    // OBTER STATUS SELECIONADO

    private BookStatus getSelectedStatus() {

        if (statusGroup.getSelectedToggle() == null) {
            return BookStatus.TO_READ;
        }

        ToggleButton selected = (ToggleButton) statusGroup.getSelectedToggle();

        return switch (selected.getText()) {

            case "Quero comprar" -> BookStatus.WISHLIST;

            case "Quero ler" -> BookStatus.TO_READ;

            case "Disponível" -> BookStatus.AVAILABLE;

            case "Lendo" -> BookStatus.READING;

            case "Pausado" -> BookStatus.PAUSED;

            case "Concluído" -> BookStatus.COMPLETED;

            case "Abandonado" -> BookStatus.ABANDONED;

            default -> BookStatus.TO_READ;
        };
    }

    // EXIBIR ALERTAS

    private void showAlert(
            Alert.AlertType type,
            String title,
            String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    // CANCELAR

    @FXML
    private void onBackClick() {

        navigationController.navigateTo(Page.WELCOME);
    }

}
