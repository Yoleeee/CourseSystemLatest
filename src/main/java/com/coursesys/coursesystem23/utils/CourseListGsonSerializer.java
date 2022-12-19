package com.coursesys.coursesystem23.utils;

import com.coursesys.coursesystem23.model.Course;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class CourseListGsonSerializer implements JsonSerializer<List<Course>>{

        @Override
        public JsonElement serialize(List<Course> courses, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonArray jsonArray = new JsonArray();
            Gson parser = new GsonBuilder().create();

            for (Course c : courses) {
                jsonArray.add(parser.toJson(c));
            }
            System.out.println(jsonArray);
            return jsonArray;
        }
}
