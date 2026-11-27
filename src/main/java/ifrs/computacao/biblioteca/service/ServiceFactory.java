package ifrs.computacao.biblioteca.service;

import ifrs.computacao.biblioteca.bdConections.JpaUtil;
import ifrs.computacao.biblioteca.repository.BookRepository;

public class ServiceFactory {

    private ServiceFactory() {
    }

    public static BookService createBookService() {

        BookRepository repository = new BookRepository(
                JpaUtil.getEntityManagerFactory());

        return new BookService(repository);
    }
}
