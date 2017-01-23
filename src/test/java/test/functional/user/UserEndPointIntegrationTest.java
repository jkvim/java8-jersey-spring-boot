package test.functional.user;

import com.jayway.restassured.RestAssured;
import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import test.functional.Helper;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(GaiaApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles({EnvProfile.TEST})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserEndPointIntegrationTest {
    @Value("${local.server.port}")
    private int serverPort;

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
        RestAssured.basePath = "/user";
    }

    @Test
    public void a_should_addUser_return_status_code_200() {
        given().
                contentType("application/json").
                body(Helper.getUserMap(
                        Helper.defaultEmail,
                        Helper.defaultPassword
                )).
        when().
                post("/adduser").
        then().
                statusCode(200);
    }

    @Test
    public void a_should_addUser_return_status_code_402_when_given_invalid_email() {
        given().
                contentType("application/json").
                body(Helper.getUserMap(
                        Helper.invalidEmail,
                        Helper.defaultPassword
                )).
        when().
                post("/adduser").
        then().
                statusCode(402);
    }

    @Test
    public void a_should_addUser_return_status_code_402_when_given_empty_password() {
        given().
                contentType("application/json").
                body(Helper.getUserMap(
                        Helper.defaultEmail,
                        ""
                )).
        when().
                post("/adduser").
        then().
                statusCode(402);
    }

    @Test
    public void a_should_addUser_return_status_code_403_when_given_existing_user() {
        given().
                contentType("application/json").
                body(Helper.getUserMap(
                        Helper.defaultEmail,
                        Helper.defaultPassword
                )).
        when().
                post("/adduser").
        then().
                statusCode(403);
    }

    @Test
    public void b_should_getUser_return_status_code_404_when_given_nonexisting_userId() {
        given().pathParam("userId", Helper.nonExistingUserId).when().get("/{userId}").then().statusCode(404);
    }

    @Test
    public void b_should_getUser_return_status_code_200_when_given_existing_userId() {
        given().pathParam("userId", 1L).when().get("/{userId}").then().statusCode(200);
    }

    @Test
    public void c_should_getUserProfile_return_status_code_404_when_given_nonexisting_userId() {
        given().pathParam("userId", Helper.nonExistingUserId).when().get("/{userId}/profile").then().statusCode(404);
    }

    @Test
    public void c_should_getUserProfile_return_status_code_200_when_given_existing_userId() {
        given().pathParam("userId", 1L).when().get("/{userId}/profile").then().statusCode(200);
    }

    @Test
    public void d_should_patchUserProfile_return_status_code_200() {
        //given
        Long userId = 1L;
        //when
        //then
        given().
                pathParam("userId", userId).
                when().
                get("/{userId}/profile").
        then().
                body("name", equalTo(null)).
                body("gender", equalTo(true)).
                body("school", equalTo(null)).
                body("major", equalTo(null)).
                body("tel", equalTo(null));
        given().
                pathParam("userId", userId).
                contentType("application/json").
                body(Helper.getUserProfileMap(
                        Helper.defaultName,
                        Helper.defaultGender,
                        Helper.defaultSchool,
                        Helper.defaultMajor,
                        Helper.defaultTel
                )).
        when().
                patch("/{userId}/profile").
        then().
                statusCode(200);
        given().
                pathParam("userId", userId).
        when().
                get("/{userId}/profile").
        then().
                body("name", equalTo(Helper.defaultName)).
                body("gender", equalTo(Helper.defaultGender)).
                body("school", equalTo(Helper.defaultSchool)).
                body("major", equalTo(Helper.defaultMajor)).
                body("tel", equalTo(Helper.defaultTel));
    }

    @Test
    public void d_should_patchUserProfile_return_status_code_402_when_given_invalid_name() {
        //given
        Long userId = 1L;
        //when
        given().
                pathParam("userId", userId).
                contentType("application/json").
                body(Helper.getUserProfileMap(
                        "Peter#Waltson",
                        Helper.defaultGender,
                        Helper.defaultSchool,
                        Helper.defaultMajor,
                        Helper.defaultTel
                )).
        when().
                patch("/{userId}/profile").
        then().
                statusCode(402);
        //then
        given().
                pathParam("userId", userId).
        when().
                get("/{userId}/profile").
        then().
                body("name", equalTo(Helper.defaultName));
    }

    @Test
    public void d_should_patchUserProfile_return_status_code_402_when_given_invalid_school() {
        //given
        Long userId = 1L;
        //when
        given().
                pathParam("userId", userId).
                contentType("application/json").
                body(Helper.getUserProfileMap(
                        Helper.defaultName,
                        Helper.defaultGender,
                        "This is a super long school name which is more than 64 characters.",
                        Helper.defaultMajor,
                        Helper.defaultTel
                )).
        when().
                patch("/{userId}/profile").
        then().
                statusCode(402);
        //then
        given().
                pathParam("userId", userId).
        when().
                get("/{userId}/profile").
        then().
                body("school", equalTo(Helper.defaultSchool));
    }

    @Test
    public void d_should_patchUserProfile_return_status_code_402_when_given_invalid_major() {
        //given
        Long userId = 1L;
        //when
        given().
                pathParam("userId", userId).
                contentType("application/json").
                body(Helper.getUserProfileMap(
                        Helper.defaultName,
                        Helper.defaultGender,
                        Helper.defaultSchool,
                        "This is a super long major name which is more than 64 characters.",
                        Helper.defaultTel
                )).
        when().
                patch("/{userId}/profile").
        then().
                statusCode(402);
        //then
        given().
                pathParam("userId", userId).
        when().
                get("/{userId}/profile").
        then().
                body("major", equalTo(Helper.defaultMajor));
    }

    @Test
    public void d_should_patchUserProfile_return_status_code_402_when_given_invalid_tel() {
        //given
        Long userId = 1L;
        //when
        given().
                pathParam("userId", userId).
                contentType("application/json").
                body(Helper.getUserProfileMap(
                        Helper.defaultName,
                        Helper.defaultGender,
                        Helper.defaultSchool,
                        Helper.defaultMajor,
                        "invalidTel"
                )).
                when().
                patch("/{userId}/profile").
                then().
                statusCode(402);
        //then
        given().
                pathParam("userId", userId).
        when().
                get("/{userId}/profile").
        then().
                body("tel", equalTo(Helper.defaultTel));
    }

    @Test
    public void e_should_loginUser_return_statusCode_402_when_given_invalid_email() {
        given().
                contentType("application/json").
                body(Helper.getUserMap(
                        Helper.invalidEmail,
                        Helper.defaultPassword
                )).
        when().
                post("/login").
        then().
                statusCode(402);
    }
    
    @Test
    public void e_should_loginUser_return_statusCode_404_when_given_nonexisting_user() {
        given().
                contentType("application/json").
                body(Helper.getUserMap(
                        Helper.nonExistingEmail,
                        Helper.defaultPassword
                )).
        when().
                post("/login").
        then().
                statusCode(404);
    }

    @Test
    public void e_should_loginUser_return_statusCode_402_when_given_empty_password() {
        given().
                contentType("application/json").
                body(Helper.getUserMap(
                        Helper.defaultEmail,
                        ""
                )).
        when().
                post("/login").
        then().
                statusCode(402);
    }
    
    @Test
    public void e_should_loginUser_return_statusCode_200() {
        given().
                contentType("application/json").
                body(Helper.getUserMap(
                        Helper.defaultEmail,
                        Helper.defaultPassword
                )).
        when().
                post("/login").
        then().
                statusCode(200).
                body("id", equalTo(1)).
                body("email", equalTo(Helper.defaultEmail)).
                body("password", equalTo(Helper.defaultPassword));
    }

    @Test
    public void e_should_loginUser_return_statusCode_403_when_given_incorrect_password() {
        given().
                contentType("application/json").
                body(Helper.getUserMap(
                        Helper.defaultEmail,
                        "incorrectpassword"
                )).
                when().
                post("/login").
                then().
                statusCode(403);
    }
    
    @Test
    public void f_should_patchUserPassword_return_statusCode_404_when_given_nonexisting_id() {
        //given
        Long userId = Helper.nonExistingUserId;
        //when
        //then
        given().
                pathParam("userId", userId).
                contentType("application/json").
                body(Helper.getUserPasswordMap(
                        userId,
                        Helper.defaultPassword,
                        Helper.defaultNewPassword
                )).
        when().
                patch("/{userId}/password").
        then().
                statusCode(404);
    }

    @Test
    public void f_should_patchUserPassword_return_statusCode_402_when_given_empty_newpassword() {
        //given
        Long userId = 1L;
        //when
        //then
        given().
                pathParam("userId", userId).
                contentType("application/json").
                body(Helper.getUserPasswordMap(
                        userId,
                        Helper.defaultPassword,
                        ""
                )).
        when().
                patch("/{userId}/password").
        then().
                statusCode(402);
    }

    @Test
    public void f_should_patchUserPassword_return_statusCode_200() {
        //given
        Long userId = 1L;
        //when
        //then
        given().
                pathParam("userId", userId).
                contentType("application/json").
                body(Helper.getUserPasswordMap(
                        userId,
                        Helper.defaultPassword,
                        Helper.defaultNewPassword
                )).
                when().
                patch("/{userId}/password").
                then().
                body("id", equalTo(userId.intValue())).
                body("email", equalTo(Helper.defaultEmail)).
                body("password", equalTo(Helper.defaultNewPassword)).
                statusCode(200);
    }

    @Test
    public void f_should_patchUserPassword_return_statusCode_402_when_given_same_passwords() {
        //given
        Long userId = 1L;
        //when
        //then
        given().
                pathParam("userId", userId).
                contentType("application/json").
                body(Helper.getUserPasswordMap(
                        userId,
                        Helper.defaultNewPassword,
                        Helper.defaultNewPassword
                )).
                when().
                patch("/{userId}/password").
                then().
                statusCode(402);
    }

    @Test
    public void f_should_patchUserPassword_return_statusCode_403_when_given_incorrect_oldpassword() {
        //given
        Long userId = 1L;
        //when
        //then
        given().
                pathParam("userId", userId).
                contentType("application/json").
                body(Helper.getUserPasswordMap(
                        userId,
                        Helper.defaultPassword,
                        Helper.defaultNewPassword
                )).
                when().
                patch("/{userId}/password").
                then().
                statusCode(403);
    }
}
