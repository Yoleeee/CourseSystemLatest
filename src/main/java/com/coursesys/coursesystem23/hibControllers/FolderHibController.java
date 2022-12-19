package com.coursesys.coursesystem23.hibControllers;

import com.coursesys.coursesystem23.model.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class FolderHibController {

    private EntityManagerFactory emf = null;

    public FolderHibController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

//    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
//    FolderHibController folderHibController = new FolderHibController(entityManagerFactory);

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
            try {
                folder = em.getReference(Folder.class, id);
            } catch (Exception e) {
                System.out.println("No such folder by given Id");
            }

            Course course = em.getReference(Course.class, folder.getParentCourse().getId());

            if (course != null) {
                course.getCourseFolders().remove(folder);
                em.merge(course);
            }

//            folder.getSubFolders().clear();

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

    public List<Folder> getAllFolders() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Folder.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
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
            return null;
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
            System.out.println("No such folder by given Id");
        }

        return folder;
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
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(File.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public void removeFile(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            File file = null;
            try {
                file = em.getReference(File.class, id);
            } catch (Exception e) {
                System.out.println("No such file by given Id");
            }

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
