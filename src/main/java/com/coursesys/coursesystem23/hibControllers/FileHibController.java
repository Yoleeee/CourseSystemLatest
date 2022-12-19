package com.coursesys.coursesystem23.hibControllers;

import com.coursesys.coursesystem23.model.File;
import com.coursesys.coursesystem23.model.Folder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collections;
import java.util.List;

public class FileHibController {
    protected EntityManagerFactory emf = null;

    public FileHibController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createFile(File file) {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(file);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editFile(File file) {
        EntityManager em = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(file);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<File> getAllFiles() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(File.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return Collections.emptyList();
    }

    public void removeFile(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            File file = null;

            file = em.getReference(File.class, id);

            Folder folder = em.getReference(Folder.class, file.getFolder().getId());

            if (folder != null) {
                folder.getFolderFiles().remove(file);
                em.merge(folder);
            }

            em.remove(file);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
