package ifrs.computacao.biblioteca.repository;

import ifrs.computacao.biblioteca.models.AuthorBook;
import ifrs.computacao.biblioteca.models.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class AuthorBookRepository {

    private final EntityManagerFactory emf;

    public AuthorBookRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // ASSOCIAR AUTOR A UM LIVRO
    public void addAuthorToBook(Long bookId, Long authorId) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Book book = em.find(Book.class, bookId);
            AuthorBook author = em.find(AuthorBook.class, authorId);

            if (book == null) {
                throw new IllegalArgumentException(
                        "Livro não encontrado.");
            }

            if (author == null) {
                throw new IllegalArgumentException(
                        "Autor não encontrado.");
            }

            boolean alreadyAssociated = book.getAuthors()
                    .stream()
                    .anyMatch(a -> a.getId().equals(authorId));

            if (!alreadyAssociated) {
                book.getAuthors().add(author);
                author.getBooks().add(book);
            }

            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    // REMOVER AUTOR DE UM LIVRO
    public void removeAuthorFromBook(Long bookId, Long authorId) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Book book = em.find(Book.class, bookId);
            AuthorBook author = em.find(AuthorBook.class, authorId);

            if (book == null || author == null) {
                throw new IllegalArgumentException(
                        "Livro ou autor não encontrado.");
            }

            book.getAuthors().removeIf(
                    a -> a.getId().equals(authorId));

            author.getBooks().removeIf(
                    b -> b.getId().equals(bookId));

            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    // BUSCAR AUTORES DE UM LIVRO
    public List<AuthorBook> findAuthorsByBook(Long bookId) {

        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT a FROM Book b " +
                            "JOIN b.authors a " +
                            "WHERE b.id = :bookId " +
                            "ORDER BY a.name ASC",
                    AuthorBook.class)
                    .setParameter("bookId", bookId)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // BUSCAR LIVROS DE UM AUTOR
    public List<Book> findBooksByAuthor(Long authorId) {

        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT b FROM Book b " +
                            "JOIN b.authors a " +
                            "WHERE a.id = :authorId " +
                            "ORDER BY b.title ASC",
                    Book.class)
                    .setParameter("authorId", authorId)
                    .getResultList();

        } finally {
            em.close();
        }
    }
}
