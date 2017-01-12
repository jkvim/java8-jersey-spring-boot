package com.thoughtworks.gaia.exam.endpoint;

import com.thoughtworks.gaia.exam.entity.Exam;
import com.thoughtworks.gaia.exam.service.ExamService;
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
@Path("exam")
@Api(value = "exam", description = "Access to exam resource")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamEndPoint {
    @Autowired
    private ExamService examService;

    @Path("/{exam_id}")
    @ApiOperation(value = "Get exam by id", response = Exam.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get exam successfully"),
            @ApiResponse(code = 404, message = "No exam matches given id")
    })
    @GET
    public Response getExam(@PathParam("exam_id") Long examId) {
        Exam exam = examService.getExam(examId);
        return Response.ok().entity(exam).build();
    }
}
