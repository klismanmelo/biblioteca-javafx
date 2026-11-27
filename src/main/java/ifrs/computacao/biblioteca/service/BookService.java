package ifrs.computacao.biblioteca.service;

import ifrs.computacao.biblioteca.models.Book;
import ifrs.computacao.biblioteca.models.BookStatus;
import ifrs.computacao.biblioteca.repository.BookRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Cadastra um novo livro na biblioteca.
     */
    public Book registerBook(
            String title,
            String isbn,
            Integer totalPages,
            Integer publicationYear,
            BookStatus status,
            String authorsText) {

        // Validar título
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "O título do livro é obrigatório.");
        }

        title = title.trim();

        // Validar páginas
        if (totalPages != null && totalPages <= 0) {
            throw new IllegalArgumentException(
                    "O número de páginas deve ser maior que zero.");
        }

        // Validar ano
        if (publicationYear != null &&
                (publicationYear < 1 || publicationYear > 9999)) {
            throw new IllegalArgumentException(
                    "Informe um ano de publicação válido.");
        }

        // Normalizar ISBN
        isbn = normalizeIsbn(isbn);

        // Verificar ISBN duplicado
        if (isbn != null &&
                bookRepository.findByIsbn(isbn).isPresent()) {

            throw new IllegalArgumentException(
                    "Já existe um livro cadastrado com esse ISBN.");
        }

        // Criar livro
        Book book = new Book(
                title,
                totalPages,
                status != null ? status : BookStatus.TO_READ);

        book.setIsbn(isbn);
        book.setPublicationYear(publicationYear);

        // Extrair nomes dos autores
        List<String> authorNames = parseAuthorNames(authorsText);

        // Persistir livro, reaproveitando ou criando os autores
        return bookRepository.save(book, authorNames);
    }

    /**
     * Converte um texto como "Autor A, Autor B" em uma lista de nomes únicos
     * (ignorando diferenças de maiúsculas/minúsculas e entradas vazias).
     */
    private List<String> parseAuthorNames(String authorsText) {

        if (authorsText == null || authorsText.isBlank()) {
            return List.of();
        }

        Map<String, String> names = new LinkedHashMap<>();

        for (String name : authorsText.split(",")) {

            String authorName = name.trim();

            if (authorName.isEmpty()) {
                continue;
            }

            names.putIfAbsent(authorName.toLowerCase(), authorName);
        }

        return new ArrayList<>(names.values());
    }

    /**
     * Normaliza ISBN removendo espaços e hífens.
     */
    private String normalizeIsbn(String isbn) {

        if (isbn == null || isbn.isBlank()) {
            return null;
        }

        return isbn.replaceAll("[\\s-]", "");
    }
}