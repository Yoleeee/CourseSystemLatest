module com.coursesys.coursesystem23 {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires spring.web;
    requires spring.context;
    requires spring.core;
    requires com.google.gson;


    opens com.coursesys.coursesystem23 to javafx.fxml;
    exports com.coursesys.coursesystem23;

    opens com.coursesys.coursesystem23.model to javafx.fxml, org.hibernate.orm.core, java.persistence;
    exports com.coursesys.coursesystem23.model;
    exports com.coursesys.coursesystem23.fxControllers;
    opens com.coursesys.coursesystem23.fxControllers to javafx.fxml;
    exports com.coursesys.coursesystem23.utils to  org.hibernate.orm.core;
    exports com.coursesys.coursesystem23.hibControllers;
    opens com.coursesys.coursesystem23.hibControllers to javafx.fxml;
}