package com.coursesys.coursesystem23.utils;

import com.coursesys.coursesystem23.model.Course;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class CourseGsonSerializer implements JsonSerializer<Course> {

    @Override
    public JsonElement serialize(Course course, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject personJson = new JsonObject();
        personJson.addProperty("id", course.getId());
        personJson.addProperty("title", course.getTitle());
        personJson.addProperty("description", course.getDescription());
        personJson.addProperty("dateCreated",course.getDateCreated().toString());
        personJson.addProperty("dateModified", course.getDateModified().toString());
        personJson.addProperty("startDate", course.getStartDate().toString());
        personJson.addProperty("endDate", course.getEndDate().toString());
        return personJson;
    }
}
