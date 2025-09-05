package ngocthang.configs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig {

	private static EntityManagerFactory factory;

	public static EntityManager getEntityManager() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("dataSource");
		}
		return factory.createEntityManager();
	}

	public static void shutdown() {
		if (factory != null) {
			factory.close();
		}
	}
}
