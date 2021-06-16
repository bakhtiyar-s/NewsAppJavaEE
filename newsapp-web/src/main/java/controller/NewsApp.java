package controller;

import security.JwtFilter;
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
        mySet.add(JwtFilter.class);
        mySet.add(com.github.phillipkruger.apiee.ApieeService.class);
        return mySet;
    }
}
