package com.coursesys.coursesystem23.hibControllers;

import com.coursesys.coursesystem23.model.Company;
import com.coursesys.coursesystem23.model.Course;
import com.coursesys.coursesystem23.model.Person;
import com.coursesys.coursesystem23.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserHibController {

    private EntityManagerFactory emf = null;

    public UserHibController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) {

        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(int id) {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user = null;
            user = em.getReference(User.class, id);
            user.getId();


            for (Course c: user.getMyModeratedCourses()) {
                c.getCourseModerator().remove(user);
            }

            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null)
                em.close();
        }
    }

    public List<User> getAllUsers() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(User.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
             em.close();
        }
        return Collections.emptyList();
    }



    public List<Person> getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Person.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Collections.emptyList();
    }

    public Person getPersonById(int id) {
        Logger logger = Logger.getLogger(UserHibController.class.getName());
        EntityManager em = null;
        Person person = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            person = em.find(Person.class, id);
            em.getTransaction().commit();
        }catch (Exception e) {
            logger.log(Level.WARNING,"No such student by given Id");
        }

        return person;
    }

    public List<Company> getAllCompanies() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Company.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Collections.emptyList();
    }

    public Company getCompanyById(int id) {
        Logger logger = Logger.getLogger(FolderHibController.class.getName());
        EntityManager em = null;
        Company company = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            company = em.find(Company.class, id);
            em.getTransaction().commit();
        }catch (Exception e) {
            logger.log(Level.WARNING,"No such company by given Id");
        }

        return company;
    }

    public User getUserById(int id) {
        Logger logger = Logger.getLogger(FolderHibController.class.getName());
        EntityManager em = null;
        User user = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.find(User.class, id);
            em.getTransaction().commit();
        }catch (Exception e) {
            logger.log(Level.WARNING,"No such user by given Id");
        }

        return user;
    }

    public User getUserByLogin(String login) {
        EntityManager em = getEntityManager();
        Logger logger = Logger.getLogger(FolderHibController.class.getName());
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(cb.like(root.get("login"), login));
        Query q;

        try {
            q = em.createQuery(query);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.WARNING,"No such user exists by given login");
            return null;
        }
    }

    public User getUserByLoginData(String login, String password) {
        EntityManager em = getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(cb.like(root.get("login"), login));
        query.select(root).where(cb.like(root.get("password"), password));
        Query q;

        Logger logger = Logger.getLogger(UserHibController.class.getName());

        try {
            q = em.createQuery(query);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.WARNING, "No such user exists");
            return null;
        }
    }
}
