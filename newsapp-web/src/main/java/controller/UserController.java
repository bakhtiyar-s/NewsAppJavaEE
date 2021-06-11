package controller;

import entity.User;
import service.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserController {

    @Inject
    private UserService userService;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response getUser(@QueryParam("email") String email) {
        User user = userService.findUserByEmail(email);
        if (user != null) {
            return Response.status(Response.Status.OK).entity(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User with email " + email + " was not found").build();
        }
    }
}
