package com.thoughtworks.gaia.user.endpoint;

import com.thoughtworks.gaia.user.entity.User;
import com.thoughtworks.gaia.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.jaxrs.PATCH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("user")
@Api(value = "user", description = "Access to user resource")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserEndPoint {
    @Autowired
    private UserService userService;

    @Path("/{user_id}")
    @ApiOperation(value = "Get user by id", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get user successfully"),
            @ApiResponse(code = 404, message = "No user matches given id")
    })

    @GET
    public Response getUser(@PathParam("user_id") Long userId) {
        return Response.ok().entity(userService.getUser(userId)).build();
    }

    @Path("/{user_id}/profile")
    @ApiOperation(value = "Patch user profile", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Bad Request")
    })

    @PATCH
    public Response patchUserProfile(@PathParam("user_id") Long userId, @RequestBody User user) {
        try {
            userService.updateUserProfile(userId, user);
        } catch (NotFoundException e) {
            Response.status(404).entity("Not Found");
        }

        return Response.ok().entity("Successfully updated user profile without errors").build();
    }
}
