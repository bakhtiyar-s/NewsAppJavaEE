package controller;

import dto.LoginDto;
import entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import security.JwtTokenProvider;
import service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/user")
@Api(value = "User Controller")
public class UserController {

    @Inject
    private UserService userService;

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Inject
    private JwtTokenProvider tokenProvider;

    @Inject
    private Logger logger;


    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Retrieve user by email")
    @RolesAllowed("ADMIN")
    public Response getUser(@QueryParam("email") String email) {
        User user = userService.findUserByEmail(email);
        if (user != null) {
            return Response.status(Response.Status.OK).entity(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User with email " + email + " was not found").build();
        }
    }

    @POST
    @Path("/login")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response login(final LoginDto loginDto) {
        CredentialValidationResult result = identityStoreHandler.validate(new UsernamePasswordCredential(loginDto.getEmail(), loginDto.getPassword()));

        if (result.getStatus() == CredentialValidationResult.Status.VALID) {
            String token = tokenProvider.createToken(result);
            Map<Object, Object> response = new HashMap<>();
            response.put("email", loginDto.getEmail());
            response.put("token", token);
            logger.info("Authentication successful {}", token);
            return Response.status(Response.Status.OK).entity(response).build();

        } else {
            logger.error("Wrong credentials for user with email {} or user not found", loginDto.getEmail());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
