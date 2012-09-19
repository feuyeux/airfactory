package creative.air.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * @author feuyeux@gmail.com
 * @version 2.0
 */
public class AirJPAFactory {
	private static AirJPAFactory instance;
	private static EntityManagerFactory emf;

	private AirJPAFactory() {
	}

	public synchronized static AirJPAFactory getInstance() {
		if (instance == null) {
			instance = new AirJPAFactory();
			emf = Persistence.createEntityManagerFactory("jpa2_kms");
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
