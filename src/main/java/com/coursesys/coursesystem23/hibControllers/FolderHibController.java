package com.coursesys.coursesystem23.hibControllers;

import com.coursesys.coursesystem23.model.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FolderHibController extends FileHibController {

    public FolderHibController(EntityManagerFactory emf) {
        super(emf);
    }

    public void create(Folder folder) {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(folder);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Folder folder) {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(folder);
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
            Folder folder = null;

            folder = em.getReference(Folder.class, id);

            Course course = em.getReference(Course.class, folder.getParentCourse().getId());

            if (course != null) {
                course.getCourseFolders().remove(folder);
                em.merge(course);
            }

            em.remove(folder);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Folder> getCourseFolders(int id) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Folder> query = cb.createQuery(Folder.class);

        Root<Folder> root = query.from(Folder.class);

        CriteriaBuilder.In<Integer> inClause = cb.in(root.get("id"));
        Course course = em.getReference(Course.class, id);
        for (Folder f: course.getCourseFolders()) {
            inClause.value(f.getId());
        }
        query.select(root).where(inClause);
        Query q;
        try {
            q = em.createQuery(query);
            return q.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public Folder getFolderById(int id) {
        EntityManager em = null;
        Folder folder = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            folder = em.find(Folder.class, id);
            em.getTransaction().commit();
        }catch (Exception e) {
            Logger logger = Logger.getLogger(FolderHibController.class.getName());
            logger.log(Level.WARNING, "No such folder by given Id");
        }

        return folder;
    }

}
