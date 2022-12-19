package com.coursesys.coursesystem23.hibControllers;

import com.coursesys.coursesystem23.model.Course;
import com.coursesys.coursesystem23.model.Folder;
import com.coursesys.coursesystem23.model.Person;
import com.coursesys.coursesystem23.model.User;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CourseHibController {

    private EntityManagerFactory emf = null;

    public CourseHibController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {return emf.createEntityManager();}

    public void createCourse(Course course) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null)
                em.close();
        }
    }

    public void edit(Course course) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(course);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void removeStudent(int personId, int courseId) {

        EntityManager em = null;
        Person person = null;
        Course course = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            person = em.getReference(Person.class, personId);
            course = em.getReference(Course.class, courseId);
            person.getMyEnrolledCourses().remove(course);
            course.getStudents().remove(person);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void removeModerator(int userId, int courseId) {

        EntityManager em = null;
        User user = null;
        Course course = null;

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.getReference(User.class, userId);
            course = em.getReference(Course.class, courseId);
            user.getMyModeratedCourses().remove(course);
            course.getCourseModerator().remove(user);
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

        FolderHibController folderHibController = new FolderHibController(emf);
        UserHibController userHibController = new UserHibController(emf);
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Course course = null;
            try {
                course = em.getReference(Course.class, id);

                for (Folder f : course.getCourseFolders()) {
                    folderHibController.remove(f.getId());
                }

                course.getCourseFolders().clear();
                em.merge(course);

                for (User u: course.getCourseModerator()) {
                    u.getMyModeratedCourses().remove(course);
                    em.merge(u);
                }

                course.getCourseModerator().clear();
                em.merge(course);

            } catch (Exception e) {
                System.out.println("No such user by given Id");
            }


            em.remove(course);
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Course> getAllCourses() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Course.class));
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

    public Course getCourseById(int id) {
        EntityManager em = null;
        Course course = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            course = em.find(Course.class, id);
            em.getTransaction().commit();
        }catch (Exception e) {
            System.out.println("No such course by given Id");
        }

        return course;
    }

    public Course getCourseByTitle(String title) {
        EntityManager em = getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> query = cb.createQuery(Course.class);
        Root<Course> root = query.from(Course.class);
        query.select(root).where(cb.like(root.get("title"), title));
        Query q;

        try {
            q = em.createQuery(query);
            return (Course) q.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No such course exists");
            return null;
        }
    }

    public List<Course> getCoursesByUserId(int id) {

        EntityManager em = getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> query = cb.createQuery(Course.class);

        Root<Course> root = query.from(Course.class);

        CriteriaBuilder.In<Integer> inClause = cb.in(root.get("id"));
        Person person = em.getReference(Person.class, id);

        for (Course c: person.getMyEnrolledCourses()) {
            inClause.value(c.getId());
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
}
