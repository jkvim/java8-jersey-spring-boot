package test.functional.user;

import com.jayway.restassured.RestAssured;
import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.user.service.UserService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import sun.net.www.HeaderParser;
import test.functional.Helper;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(GaiaApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles({EnvProfile.TEST})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserEndPointFunctionTest {
    @Value("${local.server.port}")
    private int serverPort;

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
        RestAssured.basePath = "/user";
    }

    @Test
    public void should_addUser_return_status_code_200() {
        given().
                contentType("application/json").
                body(Helper.getUserMap(Helper.getValidEmail(),
                        Helper.getValidPassword(),
                        "",
                        true,
                        "",
                        "",
                        "")).
        when().
                post("/adduser").
        then().
                statusCode(200);
    }

    @Test
    public void should_addUser_return_status_code_402_when_given_invalid_email() {
        given().
                contentType("application/json").
                body(Helper.getUserMap(Helper.getInvalidEmail(),
                        Helper.getValidPassword(),
                        "",
                        true,
                        "",
                        "",
                        "")).
                when().
                post("/adduser").
                then().
                statusCode(402);
    }

    @Test
    public void should_addUser_return_status_code_402_when_given_empty_password() {
        given().
                contentType("application/json").
                body(Helper.getUserMap(Helper.getValidEmail(),
                        "",
                        "",
                        true,
                        "",
                        "",
                        "")).
                when().
                post("/adduser").
                then().
                statusCode(402);
    }

    @Test
    public void should_addUser_return_status_code_403_when_given_existing_user() {
        given().
                contentType("application/json").
                body(Helper.getUserMap(Helper.getValidEmail(),
                        Helper.getValidPassword(),
                        "",
                        true,
                        "",
                        "",
                        "")).
        when().
                post("/adduser").
        then().
                statusCode(403);
    }

    @Test
    public void should_getUser_return_status_code_404_when_given_nonexisting_userId() {
        given().pathParam("userId", Helper.getNonExistingUserId()).when().get("/{userId}").then().statusCode(404);
    }

    @Test
    public void should_getUser_return_status_code_200_when_given_existing_userId() {
        given().pathParam("userId", 1L).when().get("/{userId}").then().statusCode(200);
    }

    @Test
    public void should_patchUserProfile_return_status_code_200() {
        //given
        //when
        given().
                pathParam("userId", 1L).
                when().
                get("/{userId}").
        then().
                body("name", equalTo(null)).
                body("gender", equalTo(true)).
                body("school", equalTo(null)).
                body("major", equalTo(null)).
                body("tel", equalTo(null));
        //then
        given().
                pathParam("userId", 1L).
                contentType("application/json").
                body(Helper.getUserMap("",
                        "",
                        Helper.defaultName,
                        Helper.defaultGender,
                        Helper.defaultSchool,
                        Helper.defaultMajor,
                        Helper.defaultTel)).
        when().
                patch("/{userId}/profile").
        then().
                statusCode(200);
        given().
                pathParam("userId", 1L).
        when().
                get("/{userId}").
        then().
                body("name", equalTo(Helper.defaultName)).
                body("gender", equalTo(Helper.defaultGender)).
                body("school", equalTo(Helper.defaultSchool)).
                body("major", equalTo(Helper.defaultMajor)).
                body("tel", equalTo(Helper.defaultTel));
    }

    @Test
    public void should_patchUserProfile_return_status_code_402_when_given_invalid_name() {
        given().
                pathParam("userId", 1L).
                contentType("application/json").
                body(Helper.getUserMap("",
                        "",
                        "Peter#Waltson",
                        Helper.defaultGender,
                        Helper.defaultSchool,
                        Helper.defaultMajor,
                        Helper.defaultTel)).
        when().
                patch("/{userId}/profile").
        then().
                statusCode(402);
        given().
                pathParam("userId", 1L).
        when().
                get("/{userId}").
        then().
                body("name", equalTo(Helper.defaultName));
    }

    @Test
    public void should_patchUserProfile_return_status_code_402_when_given_invalid_school() {
        given().
                pathParam("userId", 1L).
                contentType("application/json").
                body(Helper.getUserMap("",
                        "",
                        Helper.defaultName,
                        Helper.defaultGender,
                        "This is a super long school name which is more than 64 characters.",
                        Helper.defaultMajor,
                        Helper.defaultTel)).
        when().
                patch("/{userId}/profile").
        then().
                statusCode(402);
        given().
                pathParam("userId", 1L).
        when().
                get("/{userId}").
        then().
                body("school", equalTo(Helper.defaultSchool));
    }

    @Test
    public void should_patchUserProfile_return_status_code_402_when_given_invalid_major() {
        given().
                pathParam("userId", 1L).
                contentType("application/json").
                body(Helper.getUserMap("",
                        "",
                        Helper.defaultName,
                        Helper.defaultGender,
                        Helper.defaultSchool,
                        "This is a super long major name which is more than 64 characters.",
                        Helper.defaultTel)).
        when().
                patch("/{userId}/profile").
        then().
                statusCode(402);
        given().
                pathParam("userId", 1L).
        when().
                get("/{userId}").
        then().
                body("major", equalTo(Helper.defaultMajor));
    }

    @Test
    public void should_patchUserProfile_return_status_code_402_when_given_invalid_tel() {
        given().
                pathParam("userId", 1L).
                contentType("application/json").
                body(Helper.getUserMap("",
                        "",
                        Helper.defaultName,
                        Helper.defaultGender,
                        Helper.defaultSchool,
                        Helper.defaultMajor,
                        "invalidTel")).
                when().
                patch("/{userId}/profile").
                then().
                statusCode(402);
        given().
                pathParam("userId", 1L).
        when().
                get("/{userId}").
        then().
                body("tel", equalTo(Helper.defaultTel));
    }
}
