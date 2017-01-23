package com.thoughtworks.gaia.user.endpoint;

import com.thoughtworks.gaia.user.entity.User;
import com.thoughtworks.gaia.user.entity.UserPassword;
import com.thoughtworks.gaia.user.entity.UserProfile;
import com.thoughtworks.gaia.user.service.UserService;
import com.thoughtworks.xstream.converters.javabean.JavaBeanConverter.DuplicatePropertyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.jaxrs.PATCH;
import org.springframework.beans.InvalidPropertyException;
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
            @ApiResponse(code = 404, message = "User not found with ID xxxx")
    })
    @GET
    public Response getUser(@PathParam("user_id") Long userId) {
        try{
            User user = userService.getUserById(userId);
            return Response.status(200).entity(user).build();
        } catch (com.thoughtworks.gaia.common.exception.NotFoundException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        }
    }

    @Path("/{user_id}/profile")
    @ApiOperation(value = "Get user by id", response = UserProfile.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get user profile successfully"),
            @ApiResponse(code = 404, message = "User not found with ID xxxx")
    })
    @GET
    public Response getUserProfile(@PathParam("user_id") Long userId) {
        try{
            UserProfile userProfile = userService.getUserProfileById(userId);
            return Response.status(200).entity(userProfile).build();
        } catch (com.thoughtworks.gaia.common.exception.NotFoundException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        }
    }

    @Path("/adduser")
    @ApiOperation(value = "Add a new user", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Add user successfully"),
            @ApiResponse(code = 402, message = "Illegal parameter detected"),
            @ApiResponse(code = 403, message = "User email xxxx exists")
    })
    @POST
    public Response addUser(@RequestBody User user) {
        try {
            userService.addUser(user.getEmail(), user.getPassword());
        } catch (InvalidPropertyException ex) {
            return Response.status(402).entity("Illegal parameter detected").build();
        } catch (DuplicatePropertyException ex) {
            return Response.status(403).entity(ex.getMessage()).build();
        }
        return Response.status(200).entity("Add user successfully").build();
    }

    @Path("/{user_id}/profile")
    @ApiOperation(value = "Patch user profile", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Patch user profile successfully"),
            @ApiResponse(code = 402, message = "Illegal parameter detected"),
            @ApiResponse(code = 404, message = "User not found with ID xxxx")
    })
    @PATCH
    public Response patchUserProfile(@PathParam("user_id") Long userId, @RequestBody UserProfile userProfile) {
        boolean result = false;
        try {
            result = userService.updateUserProfile(userId, userProfile);
        } catch (NotFoundException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        }
        if (result) {
            return Response.status(200).entity("Patch user profile successfully").build();
        }
        else {
            return Response.status(402).entity("Illegal parameter detected").build();
        }
    }

    @Path("/login")
    @ApiOperation(value = "Login user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User login successfully"),
            @ApiResponse(code = 402, message = "Illegal parameter detected"),
            @ApiResponse(code = 403, message = "Incorrect password"),
            @ApiResponse(code = 404, message = "User not found with email xxxx")
    })
    @POST
    public Response loginUser(@RequestBody User user) {
        try {
            User loggedUser = userService.loginUser(user.getEmail(), user.getPassword());
            if (loggedUser == null) {
                return Response.status(403).entity("Incorrect password").build();
            } else {
                return Response.status(200).entity(loggedUser).build();
            }
        } catch (InvalidPropertyException ex) {
            return Response.status(402).entity("Illegal parameter detected").build();
        } catch (NotFoundException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        }
    }

    @Path("/{user_id}/password")
    @ApiOperation(value = "Change user password", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Change user password successfully"),
            @ApiResponse(code = 402, message = "Invalid new password"),
            @ApiResponse(code = 403, message = "Incorrect old password"),
            @ApiResponse(code = 404, message = "User not found with ID xxxx")
    })
    @PATCH
    public Response patchUserPassword(@PathParam("user_id") Long userId, @RequestBody UserPassword userPassword) {
        try {
            User user = userService.changePassword(userId, userPassword.getOldPassword(), userPassword.getNewPassword());
            return Response.status(200).entity(user).build();
        } catch (NotFoundException ex) {
            return Response.status(404).entity(ex.getMessage()).build();
        } catch (InvalidPropertyException ex) {
            return Response.status(402).entity(ex.getMessage()).build();
        } catch (ForbiddenException ex) {
            return Response.status(403).entity(ex.getMessage()).build();
        }
    }
}
