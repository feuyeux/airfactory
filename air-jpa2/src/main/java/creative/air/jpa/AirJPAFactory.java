package creative.air.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AirJPAFactory {
	private static AirJPAFactory instance;
	private static EntityManagerFactory emf;

	private AirJPAFactory() {
	}

	public synchronized static AirJPAFactory getInstance(String unitName) {
		if (instance == null) {
			instance = new AirJPAFactory();
			emf = Persistence.createEntityManagerFactory(unitName);
		}
		return instance;
	}

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}
}
