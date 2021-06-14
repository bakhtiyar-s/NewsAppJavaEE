package controller;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class NewsApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> mySet = new HashSet<>();
        mySet.add(NewsController.class);
        mySet.add(UserController.class);
        mySet.add(com.github.phillipkruger.apiee.ApieeService.class);
        return mySet;
    }
}
