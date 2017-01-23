package test.functional.user;

import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import com.thoughtworks.gaia.user.dao.UserDao;
import com.thoughtworks.gaia.user.dao.UserProfileDao;
import com.thoughtworks.gaia.user.entity.User;
import com.thoughtworks.gaia.user.entity.UserPassword;
import com.thoughtworks.gaia.user.entity.UserProfile;
import com.thoughtworks.gaia.user.model.UserModel;
import com.thoughtworks.gaia.user.model.UserProfileModel;
import com.thoughtworks.gaia.user.service.UserService;
import com.thoughtworks.xstream.converters.javabean.JavaBeanConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import test.functional.Helper;

import javax.ws.rs.ForbiddenException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(GaiaApplication.class)
@Rollback
@Transactional
@ActiveProfiles({EnvProfile.TEST})
public class UserServiceFunctionTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Test(expected = NotFoundException.class)
    public void should_getUser_throw_exception_when_given_nonexisting_Id() {
        userService.getUserById(Helper.nonExistingUserId);
    }

    @Test
    public void should_getUser_return_matched_user_when_given_existing_Id() {
        //given
        UserModel userModel = Helper.getUserModelbyUsernameAndPassword(Helper.defaultEmail, Helper.defaultPassword);
        //when
        userDao.save(userModel);
        Long expectedUserId = userModel.getId();
        Long actualUserId = userService.getUserById(expectedUserId).getId();
        //then
        assertThat(actualUserId).isEqualTo(expectedUserId);
    }

    @Test(expected = NotFoundException.class)
    public void should_getUserByEmail_throw_exception_when_given_nonexisting_email() {
        userService.getUserByEmail(Helper.nonExistingEmail);
    }

    @Test
    public void should_getUserByEmail_return_matched_user_when_given_existing_email() {
        //given
        String email = Helper.defaultEmail;
        UserModel userModel = Helper.getUserModelbyUsernameAndPassword(email, Helper.defaultPassword);
        //when
        userDao.save(userModel);
        Long userId = userService.getUserByEmail(email).getId();
        //then
        assertThat(userId).isEqualTo(userModel.getId());
    }

    @Test(expected = InvalidPropertyException.class)
    public void should_getUserByEmail_throw_exception_given_invalid_email() {
        User user = userService.getUserByEmail(Helper.invalidEmail);
    }

    @Test(expected = NotFoundException.class)
    public void should_getUserProfile_throw_exception_when_given_nonexisting_Id() {
        userService.getUserProfileById(Helper.nonExistingUserId);
    }

    @Test
    public void should_getUserProfile_return_matched_user_profile_when_given_existing_Id() {
        //given
        UserModel userModel = Helper.getUserModelbyUsernameAndPassword(Helper.defaultEmail, Helper.defaultPassword);
        UserProfileModel userProfileModel = new UserProfileModel();
        //when
        userDao.save(userModel);
        userProfileModel.setUserModel(userModel);
        userProfileModel.setGender(true);
        userProfileDao.save(userProfileModel);
        Long userId = userProfileModel.getId();
        UserProfile userProfile = userService.getUserProfileById(userId);
        //then
        assertThat(userProfile.getId()).isEqualTo(userId);
        assertThat(userProfile.getName()).isNull();
        assertThat(userProfile.getGender()).isTrue();
        assertThat(userProfile.getSchool()).isNull();
        assertThat(userProfile.getMajor()).isNull();
        assertThat(userProfile.getTel()).isNull();
    }

    @Test(expected = InvalidPropertyException.class)
    public void should_addUser_throw_exception_given_invalid_email() {
        userService.addUser(Helper.invalidEmail, Helper.defaultPassword);
    }

    @Test(expected = JavaBeanConverter.DuplicatePropertyException.class)
    public void should_addUser_throw_exception_when_given_existing_user() {
        //given
        String email = Helper.defaultEmail;
        String password = Helper.defaultPassword;
        //when
        userDao.save(Helper.getUserModelbyUsernameAndPassword(email, password));
        //then
        userService.addUser(email, password);
    }

    @Test(expected = InvalidPropertyException.class)
    public void should_addUser_throw_exception_when_given_empty_password() {
        userService.addUser(Helper.defaultEmail, "");
    }

    @Test
    public void should_addUser_return_true() {
        assertThat(userService.addUser(Helper.defaultEmail, Helper.defaultPassword)).isTrue();
    }

    @Test(expected = NotFoundException.class)
    public void should_updateUserProfile_throw_exception_when_given_nonexisting_id() {
        Long userId = Helper.nonExistingUserId;
        userService.updateUserProfile(userId, Helper.getUserProfileByUserId(userId));
    }

    @Test
    public void should_updateUserProfile_return_true() {
        //given
        UserModel userModel = Helper.getUserModelbyUsernameAndPassword(Helper.defaultEmail, Helper.defaultPassword);
        UserProfileModel userProfileModel = new UserProfileModel();
        //when
        userDao.save(userModel);
        userProfileModel.setUserModel(userModel);
        userProfileModel.setGender(true);
        userProfileDao.save(userProfileModel);
        Long userId = userProfileModel.getId();
        UserProfile userProfile = Helper.getUserProfileByUserId(userId);
        boolean result = userService.updateUserProfile(userId, userProfile);
        UserProfile userProfileUpdated = userService.getUserProfileById(userId);
        //then
        assertThat(result).isTrue();
        assertThat(userProfileUpdated.getName()).isEqualTo(userProfile.getName());
        assertThat(userProfileUpdated.getGender()).isEqualTo(userProfile.getGender());
        assertThat(userProfileUpdated.getTel()).isEqualTo(userProfile.getTel());
        assertThat(userProfileUpdated.getSchool()).isEqualTo(userProfile.getSchool());
        assertThat(userProfileUpdated.getMajor()).isEqualTo(userProfile.getMajor());
    }

    @Test
    public void should_updateUserProfile_return_false_when_given_invalid_name() {
        //given
        UserModel userModel = Helper.getUserModel();
        //when
        userDao.save(userModel);
        Long userId = userModel.getId();
        UserProfile userProfile = Helper.getUserProfileByUserId(userId);
        userProfile.setName("Peter#Waltson");
        //then
        assertThat(userService.updateUserProfile(userId, userProfile)).isFalse();
    }

    @Test
    public void should_updateUserProfile_return_false_when_given_invalid_tel() {
        //given
        UserModel userModel = Helper.getUserModel();
        //when
        userDao.save(userModel);
        Long userId = userModel.getId();
        UserProfile userProfile = Helper.getUserProfileByUserId(userId);
        userProfile.setTel("invalidTel");
        //then
        assertThat(userService.updateUserProfile(userId, userProfile)).isFalse();
    }

    @Test
    public void should_updateUserProfile_return_false_when_given_invalid_school() {
        //given
        UserModel userModel = Helper.getUserModel();
        //when
        userDao.save(userModel);
        Long userId = userModel.getId();
        UserProfile userProfile = Helper.getUserProfileByUserId(userId);
        userProfile.setSchool("This is a super long school name which is more than 64 characters.");
        //then
        assertThat(userService.updateUserProfile(userId, userProfile)).isFalse();
    }

    @Test
    public void should_updateUserProfile_return_false_when_given_invalid_major() {
        //given
        UserModel userModel = Helper.getUserModel();
        //when
        userDao.save(userModel);
        Long userId = userModel.getId();
        UserProfile userProfile = Helper.getUserProfileByUserId(userId);
        userProfile.setMajor("This is a super long major name which is more than 64 characters.");
        //then
        assertThat(userService.updateUserProfile(userId, userProfile)).isEqualTo(false);
    }

    @Test(expected = InvalidPropertyException.class)
    public void should_loginUser_throw_exception_when_given_invalid_email() {
        User user = userService.loginUser(Helper.invalidEmail, Helper.defaultPassword);
    }

    @Test(expected = NotFoundException.class)
    public void should_loginUser_throw_exception_when_given_nonexisting_user() {
        User user = userService.loginUser(Helper.nonExistingEmail, Helper.defaultPassword);
    }

    @Test(expected = InvalidPropertyException.class)
    public void should_loginUser_throw_exception_when_given_empty_password() {
        User user = userService.loginUser(Helper.defaultEmail, "");
    }

    @Test
    public void should_loginUser_return_mapped_user() {
        //given
        UserModel userModel = Helper.getUserModelbyUsernameAndPassword(Helper.defaultEmail, Helper.defaultPassword);
        //when
        userDao.save(userModel);
        User user = userService.loginUser(Helper.defaultEmail, Helper.defaultPassword);
        //then
        assertThat(user).isNotEqualTo(null);
    }

    @Test
    public void should_loginUser_return_null_when_given_incorrect_password() {
        //given
        UserModel userModel = Helper.getUserModelbyUsernameAndPassword(Helper.defaultEmail, Helper.defaultPassword);
        //when
        userDao.save(userModel);
        User user = userService.loginUser(Helper.defaultEmail, "abc");
        //then
        assertThat(user).isEqualTo(null);
    }

    @Test(expected = NotFoundException.class)
    public void should_changePassword_throw_exception_when_given_nonexisting_id() {
        userService.changePassword(Helper.nonExistingUserId, Helper.defaultPassword, "newpassword");
    }

    @Test(expected = InvalidPropertyException.class)
    public void should_changePassword_throw_exception_when_given_empty_newpassword() {
        //given
        UserModel userModel = Helper.getUserModelbyUsernameAndPassword(Helper.defaultEmail, Helper.defaultPassword);
        //when
        userDao.save(userModel);
        userService.changePassword(userModel.getId(), Helper.defaultPassword, "");
        //then
    }

    @Test
    public void should_changePassword_return_mapped_user() {
        //given
        UserModel userModel = Helper.getUserModelbyUsernameAndPassword(Helper.defaultEmail, Helper.defaultPassword);
        //when
        userDao.save(userModel);
        Long expectedUserId = userModel.getId();
        User user = userService.changePassword(expectedUserId, Helper.defaultPassword, Helper.defaultNewPassword);
        //then
        assertThat(user.getId()).isEqualTo(expectedUserId);
        assertThat(user.getPassword()).isEqualTo(Helper.defaultNewPassword);
    }

    @Test(expected = ForbiddenException.class)
    public void should_changePassword_throw_exception_when_given_incorrect_oldpassword() {
        //given
        UserModel userModel = Helper.getUserModelbyUsernameAndPassword(Helper.defaultEmail, Helper.defaultPassword);
        //when
        userDao.save(userModel);
        Long UserId = userModel.getId();
        //then
        userService.changePassword(UserId, "incorrectpassword", Helper.defaultPassword);
    }

    @Test(expected = InvalidPropertyException.class)
    public void should_changePassword_throw_exception_when_given_same_passwords() {
        //given
        UserModel userModel = Helper.getUserModelbyUsernameAndPassword(Helper.defaultEmail, Helper.defaultPassword);
        //when
        userDao.save(userModel);
        Long UserId = userModel.getId();
        //then
        userService.changePassword(UserId, Helper.defaultPassword, Helper.defaultPassword);
    }
}
