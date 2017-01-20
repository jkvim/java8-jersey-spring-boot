package com.thoughtworks.gaia.user.endpoint;

import com.thoughtworks.gaia.user.entity.User;
import com.thoughtworks.gaia.user.service.UserService;
import com.thoughtworks.xstream.converters.javabean.JavaBeanConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.jaxrs.PATCH;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
            @ApiResponse(code = 404, message = "User not found with ID xxxx")
    })
    @GET
    public Response getUser(@PathParam("user_id") Long userId) {
        try{
            User user = userService.getUserById(userId);
            return Response.status(200).entity(user).build();
        }
        catch (com.thoughtworks.gaia.common.exception.NotFoundException ex) {
            return Response.status(404).entity("User not found with ID " + userId).build();
        }
    }

    @Path("/{user_id}/profile")
    @ApiOperation(value = "Patch user profile", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patch user successfully"),
            @ApiResponse(code = 402, message = "Illegal parameter detected"),
            @ApiResponse(code = 404, message = "User not found with ID xxxx")
    })
    @PATCH
    public Response patchUserProfile(@PathParam("user_id") Long userId, @RequestBody User user) {
        boolean result = false;
        try {
            result = userService.updateUserProfile(userId, user);
        } catch (NotFoundException e) {
            return Response.status(404).entity("User not found with ID " + userId).build();
        }
        if (result) {
            return Response.status(200).entity("Patch user profile successfully").build();
        }
        else {
            return Response.status(402).entity("Illegal parameter detected").build();
        }
    }

    @Path("/adduser")
    @ApiOperation(value = "Add a new user", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Add user successfully"),
            @ApiResponse(code = 402, message = "Illegal parameter detected"),
            @ApiResponse(code = 403, message = "User account exists")
    })
    @POST
    public Response addUser(@RequestBody User user) {
        int statusCode = 200;
        try {
            userService.addUser(user.getEmail(), user.getPassword());
        } catch (InvalidPropertyException ex) {
            return Response.status(402).entity(ex.getMessage()).build();
        }
        catch (JavaBeanConverter.DuplicatePropertyException ex) {
            return Response.status(403).entity(ex.getMessage()).build();
        }
        return Response.ok().build();
    }
}
