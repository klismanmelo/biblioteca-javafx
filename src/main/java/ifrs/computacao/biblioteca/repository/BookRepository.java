package ifrs.computacao.biblioteca.repository;

import ifrs.computacao.biblioteca.models.AuthorBook;
import ifrs.computacao.biblioteca.models.Book;
import ifrs.computacao.biblioteca.models.BookStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class BookRepository {

    private final EntityManagerFactory emf;

    public BookRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // CADASTRAR LIVRO COM AUTORES
    public Book save(Book book, List<String> authorNames) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            for (String name : authorNames) {

                List<AuthorBook> found = em.createQuery(
                        "SELECT a FROM AuthorBook a " +
                                "WHERE LOWER(a.name) = LOWER(:name)",
                        AuthorBook.class)
                        .setParameter("name", name)
                        .setMaxResults(1)
                        .getResultList();

                AuthorBook author;

                if (found.isEmpty()) {
                    author = new AuthorBook(name, null, null);
                    em.persist(author);
                } else {
                    author = found.get(0);
                }

                book.getAuthors().add(author);
            }

            em.persist(book);

            transaction.commit();

            return book;

        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    // ATUALIZAR LIVRO
    public Book update(Book book) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Book updatedBook = em.merge(book);

            transaction.commit();

            return updatedBook;

        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    // BUSCAR LIVRO POR ID
    public Optional<Book> findById(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            Book book = em.find(Book.class, id);

            return Optional.ofNullable(book);

        } finally {
            em.close();
        }
    }

    // LISTAR TODOS OS LIVROS
    public List<Book> findAll() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT DISTINCT b FROM Book b ORDER BY b.title ASC",
                    Book.class).getResultList();

        } finally {
            em.close();
        }
    }

    // PESQUISAR POR TÍTULO
    public List<Book> findByTitle(String title) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT DISTINCT b FROM Book b " +
                            "WHERE LOWER(b.title) LIKE LOWER(:title) " +
                            "ORDER BY b.title ASC",
                    Book.class)
                    .setParameter("title", "%" + title + "%")
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // BUSCAR POR STATUS
    public List<Book> findByStatus(BookStatus status) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT DISTINCT b FROM Book b " +
                            "WHERE b.status = :status " +
                            "ORDER BY b.title ASC",
                    Book.class)
                    .setParameter("status", status)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // BUSCAR POR ISBN
    public Optional<Book> findByIsbn(String isbn) {
        EntityManager em = emf.createEntityManager();

        try {
            List<Book> books = em.createQuery(
                    "SELECT b FROM Book b WHERE b.isbn = :isbn",
                    Book.class)
                    .setParameter("isbn", isbn)
                    .setMaxResults(1)
                    .getResultList();

            return books.stream().findFirst();

        } finally {
            em.close();
        }
    }

    // EXCLUIR LIVRO
    public void deleteById(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Book book = em.find(Book.class, id);

            if (book != null) {
                em.remove(book);
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

    // CONTAR LIVROS CADASTRADOS
    public Long count() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT COUNT(b) FROM Book b",
                    Long.class).getSingleResult();

        } finally {
            em.close();
        }
    }
}
