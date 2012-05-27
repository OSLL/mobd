package edu.eltech.mobview.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
	private static final EntityManagerFactory emf;	
	private static final EntityManager em;
	
	static {
		emf = Persistence.createEntityManagerFactory("pu");
		em = emf.createEntityManager();
	}
	
	public static EntityManager getEntityManager() {
		return em;
	}
}
