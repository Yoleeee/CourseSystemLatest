package com.coursesys.coursesystem23.webControllers;

import com.coursesys.coursesystem23.hibControllers.UserHibController;
import com.coursesys.coursesystem23.model.Company;
import com.coursesys.coursesystem23.model.Person;
import com.coursesys.coursesystem23.model.User;
import com.coursesys.coursesystem23.model.UserType;
import com.coursesys.coursesystem23.utils.CompanyGsonSerializer;
import com.coursesys.coursesystem23.utils.LocalDateGsonSerializer;
import com.coursesys.coursesystem23.utils.PersonGsonSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Properties;

@Controller //localhost:8080/application_context/user
public class UserWebController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
    UserHibController userHibController = new UserHibController(entityManagerFactory);

    @RequestMapping(value = "/user/userLogin", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String login(@RequestBody String request) {

        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        GsonBuilder gson = new GsonBuilder();
        Person person = null;
        Company company = null;
        if (data.getProperty("type").equals("P")) {
            person = (Person) userHibController.getUserByLoginData(data.getProperty("login"), data.getProperty("password"));
            gson.registerTypeAdapter(Person.class, new PersonGsonSerializer()).registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        } else if (data.getProperty("type").equals("C")) {
            company = (Company) userHibController.getUserByLoginData(data.getProperty("login"), data.getProperty("password"));
            gson.registerTypeAdapter(Company.class, new CompanyGsonSerializer()).registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        }

        if (person == null && company == null) {
            return "Wrong credentials or no such user";
        }

        Gson builder = gson.create();
        return person != null ? builder.toJson(person) : builder.toJson(company);


    }

    @RequestMapping(value = "/user/allUsers", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllUsers() {
       return userHibController.getAllUsers().toString();
    }

    @RequestMapping(value = "/user/allPerson", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllPerson() {
        return userHibController.getAllPersons().toString();
    }

    @RequestMapping(value = "/user/allCompany", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllCompany() {
        return userHibController.getAllCompanies().toString();
    }

    @RequestMapping(value = "/user/getUser/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getUser(@PathVariable(name = "id") String id) {

        User user = userHibController.getUserById(Integer.parseInt(id));
        GsonBuilder gson = new GsonBuilder();
        if (user.getClass() == Person.class) {
            gson.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(Person.class, new PersonGsonSerializer());
        } else if (user.getClass() == Company.class)
            gson.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(Company.class, new CompanyGsonSerializer());
        Gson parser = gson.create();

        if (user == null) {
            return "No user by given ID";
        }
        return parser.toJson(user);
    }

    @RequestMapping(value = "/user/addPerson", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addNewPerson(@RequestBody String request) {

        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);

        Person person = new Person(properties.getProperty("login"), properties.getProperty("password"), properties.getProperty("name"), properties.getProperty("surname"), properties.getProperty("email"), properties.getProperty("phoneNum"));
        person.setUserType(UserType.STUDENT);
        userHibController.create(person);
        return "Success";
    }

    @RequestMapping(value = "/user/updatePerson/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updatePerson(@RequestBody String request, @PathVariable(name = "id") String id) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);

        Person person = userHibController.getPersonById(Integer.parseInt(id));
        person.setName(properties.getProperty("name"));
        person.setSurname(properties.getProperty("surname"));
        person.setEmail(properties.getProperty("email"));
        person.setPhoneNum(properties.getProperty("phoneNum"));
        userHibController.edit(person);
        return "Success";
    }

    @RequestMapping(value = "/user/addCompany", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addNewCompany(@RequestBody String request) {

        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);

        Company company = new Company(properties.getProperty("login"), properties.getProperty("password"), properties.getProperty("name"), properties.getProperty("companyRep"), properties.getProperty("address"), properties.getProperty("phoneNum"));
        company.setUserType(UserType.STUDENT);
        userHibController.create(company);
        return "Success";
    }

    @RequestMapping(value = "/user/updateCompany/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateCompany(@RequestBody String request, @PathVariable(name = "id") String id) {
        Gson gson = new Gson();
        Properties properties = gson.fromJson(request, Properties.class);

        Company company = userHibController.getCompanyById(Integer.parseInt(id));
        company.setName(properties.getProperty("name"));
        company.setCompanyRep(properties.getProperty("companyRep"));
        company.setAddress(properties.getProperty("address"));
        company.setPhoneNum(properties.getProperty("phoneNum"));
        userHibController.edit(company);
        return "Success";
    }


}
