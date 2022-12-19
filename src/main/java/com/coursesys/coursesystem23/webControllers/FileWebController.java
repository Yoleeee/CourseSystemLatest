package com.coursesys.coursesystem23.webControllers;

import com.coursesys.coursesystem23.hibControllers.CourseHibController;
import com.coursesys.coursesystem23.hibControllers.FolderHibController;
import com.coursesys.coursesystem23.hibControllers.UserHibController;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Controller //localhost:8080/application_context/file
public class FileWebController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
    UserHibController userHibController = new UserHibController(entityManagerFactory);
    CourseHibController courseHibController = new CourseHibController(entityManagerFactory);
    FolderHibController folderHibController = new FolderHibController(entityManagerFactory);

//    @RequestMapping(value = "/file/addFile/{folderId}", method = RequestMethod.POST)
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    public String addFile(@RequestBody String request, @PathVariable(name = "folderId") String folderId) {
//
//        Gson gson = new Gson();
//        Properties properties = gson.fromJson(request, Properties.class);
//
//        File file = new File(properties.getProperty("name"), properties.getProperty("location"), folderHibController.getFolderById(Integer.parseInt(folderId)));
//        folderHibController.createFile(file);
//        return "Success";
//    }
//
//    @RequestMapping(value = "/file/updateFile/{folderId}", method = RequestMethod.PUT)
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    public String editFile(@RequestBody String request, @PathVariable(name = "folderId") String folderId) {
//
//        Gson gson = new Gson();
//        Properties properties = gson.fromJson(request, Properties.class);
//
//        File file = new File(properties.getProperty("name"), properties.getProperty("location"), folderHibController.getFolderById(Integer.parseInt(folderId)));
//        folderHibController.editFile(file);
//        return "Success";
//    }
//
//    @RequestMapping(value = "/file/deleteFile/{id}", method = RequestMethod.DELETE)
//    @ResponseStatus(value = HttpStatus.OK)
//    @ResponseBody
//    public String deleteProject(@PathVariable(name = "id") String id) {
//
//        folderHibController.removeFile(Integer.parseInt(id));
//
//        return "Success";
//    }
}
