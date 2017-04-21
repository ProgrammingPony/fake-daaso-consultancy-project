package Daaso.Hibernate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * JPA Entity Manager class with a convenient method to get Session Factory
 * object.
 * 
 * Reference Singleton Pattern: http://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples#thread-safe-singleton 
 *
 * @author Omar
 */
public class EntityManagerHelper {

    @PersistenceContext
    private static EntityManagerFactory emf;
    
    private EntityManagerHelper () {}
    
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            try {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");          
            } catch (Throwable ex) {
                // Log the exception. 
                System.err.println("Initial EntityFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        
        return emf;
    }
}
