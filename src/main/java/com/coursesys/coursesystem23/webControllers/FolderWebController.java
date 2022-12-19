package com.coursesys.coursesystem23.webControllers;

import com.coursesys.coursesystem23.hibControllers.CourseHibController;
import com.coursesys.coursesystem23.hibControllers.FolderHibController;
import com.coursesys.coursesystem23.hibControllers.UserHibController;
import com.coursesys.coursesystem23.model.Course;
import com.coursesys.coursesystem23.model.Folder;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

@Controller //localhost:8080/application_context/folder
public class FolderWebController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
    UserHibController userHibController = new UserHibController(entityManagerFactory);
    CourseHibController courseHibController = new CourseHibController(entityManagerFactory);
    FolderHibController folderHibController = new FolderHibController(entityManagerFactory);

    @RequestMapping(value = "/folder/addFolder/{courseId}/{parentFolderId}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addNewPerson(@RequestBody String request, @PathVariable(name = "courseId") String courseId, @PathVariable(name = "parentFolderId") String parentFolderId) {

        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);

        Course course = courseHibController.getCourseById(Integer.parseInt(courseId));

        Folder folder = new Folder(properties.getProperty("foldersName"), course, folderHibController.getFolderById(Integer.parseInt(parentFolderId)), course.getCourseModerator());
        folderHibController.create(folder);
        return "Success";
    }

    @RequestMapping(value = "/folder/courseFolders/{courseId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String courseFolders(@RequestBody String request, @PathVariable(name = "courseId") String courseId) {
        return courseHibController.getCourseById(Integer.parseInt(courseId)).getCourseFolders().toString();
    }

    @RequestMapping(value = "/folder/deleteFolder/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteProject(@PathVariable(name = "id") String id) {

        folderHibController.remove(Integer.parseInt(id));

        Folder folder = folderHibController.getFolderById(Integer.parseInt(id));
        if (folder == null) {
            return "Success";
        } else {
            return "Not deleted";
        }

    }

}
