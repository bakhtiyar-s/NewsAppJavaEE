package security;

import entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class JwtFilter implements ContainerRequestFilter {

    @Inject
    private JwtTokenProvider tokenProvider;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String uri = containerRequestContext.getUriInfo().getPath();
        if (uri.equals("/user/login")) {
            return;
        } else {
            String authorization = containerRequestContext.getHeaders().getFirst("Authorization");
            if (authorization == null) {
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Authorization header is missing").build());
                return;
            }
            List<String> roles = null;
            try {
                Jws<Claims> claimsJws = tokenProvider.parse(authorization);
                roles = (List<String>) claimsJws.getBody().get("roles");
            } catch (JwtException e) {
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build());
                return;
            }
            RolesAllowed rolesAllowed = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
            if (resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class) != null) {
                rolesAllowed = resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class);
            }

            List<String> finalRoles = roles;
            if (rolesAllowed != null && Arrays.stream(rolesAllowed.value()).noneMatch(s -> finalRoles.contains(s))) {
                containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity("User does not have enough privileges to access the resource").build());
            }
        }
    }
}
