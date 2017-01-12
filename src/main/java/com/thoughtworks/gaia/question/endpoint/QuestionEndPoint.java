package com.thoughtworks.gaia.question.endpoint;

import com.thoughtworks.gaia.question.entity.Question;
import com.thoughtworks.gaia.question.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("question")
@Api(value = "question", description = "Access to question resource")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuestionEndPoint {
    @Autowired
    private QuestionService questionService;

    @Path("/{question_id}")
    @ApiOperation(value = "Get question by id", response = Question.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get question successfully"),
            @ApiResponse(code = 404, message = "No question matches given id")
    })
    @GET
    public Response getQuestion(@PathParam("question_id") Long questionId) {
        Question question = questionService.getQuestion(questionId);
        return Response.ok().entity(question).build();
    }
}
