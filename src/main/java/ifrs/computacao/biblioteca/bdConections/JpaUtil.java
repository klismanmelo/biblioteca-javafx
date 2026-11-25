package ifrs.computacao.biblioteca.bdConections;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaUtil {

    private static final EntityManagerFactory FACTORY = Persistence
            .createEntityManagerFactory("bdConections_biblioteca");

    private JpaUtil() {
    }

    public static EntityManager criarEntityManager() {
        return FACTORY.createEntityManager();
    }

    public static void fechar() {
        if (FACTORY.isOpen()) {
            FACTORY.close();
        }
    }
}