package test.functional.user;

import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import com.thoughtworks.gaia.user.dao.UserDao;
import com.thoughtworks.gaia.user.entity.User;
import com.thoughtworks.gaia.user.model.UserModel;
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

    @Test(expected = NotFoundException.class)
    public void should_getUser_throw_exception_when_given_nonexisting_Id() {
        userService.getUserById(Helper.getNonExistingUserId());
    }

    @Test
    public void should_getUser_return_matched_user_when_given_existing_Id() {
        //given
        String email = Helper.getValidEmail();
        UserModel userModel = Helper.getUserModelbyUsernameAndPassword(email, Helper.getValidPassword());
        //when
        userDao.save(userModel);
        Long expectedUserId = userModel.getId();
        Long actualUserId = userService.getUserById(expectedUserId).getId();
        //then
        assertThat(actualUserId).isEqualTo(expectedUserId);
    }

    @Test(expected = NotFoundException.class)
    public void should_getUserByEmail_throw_exception_when_given_nonexisting_email() {
        userService.getUserByEmail(Helper.getNonExistingEmail());
    }

    @Test
    public void should_getUserByEmail_return_matched_user_when_given_existing_email() {
        //given
        String email = Helper.getValidEmail();
        UserModel userModel = Helper.getUserModelbyUsernameAndPassword(email, Helper.getValidPassword());
        //when
        userDao.save(userModel);
        Long userId = userService.getUserByEmail(email).getId();
        //then
        assertThat(userId).isEqualTo(userModel.getId());
    }

    @Test(expected = InvalidPropertyException.class)
    public void should_getUserByEmail_throw_exception_given_invalid_email() {
        User user = userService.getUserByEmail(Helper.getInvalidEmail());
    }

    @Test(expected = InvalidPropertyException.class)
    public void should_addUser_throw_exception_given_invalid_email() {
        userService.addUser(Helper.getInvalidEmail(), Helper.getValidPassword());
    }

    @Test(expected = JavaBeanConverter.DuplicatePropertyException.class)
    public void should_addUser_throw_exception_when_given_existing_user() {
        //given
        String email = Helper.getValidEmail();
        String password = Helper.getValidPassword();
        //when
        userDao.save(Helper.getUserModelbyUsernameAndPassword(email, password));
        //then
        userService.addUser(email, password);
    }

    @Test(expected = InvalidPropertyException.class)
    public void should_addUser_throw_exception_when_given_empty_password() {
        userService.addUser(Helper.getValidEmail(), "");
    }

    @Test
    public void should_addUser_return_true() {
        assertThat(userService.addUser(Helper.getValidEmail(), Helper.getValidPassword())).isTrue();
    }

    @Test(expected = NotFoundException.class)
    public void should_updateUserProfile_throw_exception_when_given_nonexisting_id() {
        Long userId = Helper.getNonExistingUserId();
        userService.updateUserProfile(userId, Helper.getUserByUserId(userId));
    }

    @Test
    public void should_updateUserProfile_return_true() {
        //given
        UserModel userModel = Helper.getUserModel();
        //when
        userDao.save(userModel);
        long userId = userModel.getId();
        User user = Helper.getUserByUserId(userId);
        boolean result = userService.updateUserProfile(userId, user);
        User userUpdated = userService.getUserById(userId);
        //then
        assertThat(result).isTrue();
        assertThat(userUpdated.getName()).isEqualTo(user.getName());
        assertThat(userUpdated.getGender()).isEqualTo(user.getGender());
        assertThat(userUpdated.getTel()).isEqualTo(user.getTel());
        assertThat(userUpdated.getSchool()).isEqualTo(user.getSchool());
        assertThat(userUpdated.getMajor()).isEqualTo(user.getMajor());
    }

    @Test
    public void should_updateUserProfile_return_false_when_given_invalid_name() {
        //given
        UserModel userModel = Helper.getUserModel();
        //when
        userDao.save(userModel);
        Long userId = userModel.getId();
        User user = Helper.getUserByUserId(userId);
        user.setName("Peter#Waltson");
        //then
        assertThat(userService.updateUserProfile(userId, user)).isEqualTo(false);
    }

    @Test
    public void should_updateUserProfile_return_false_when_given_invalid_tel() {
        //given
        UserModel userModel = Helper.getUserModel();
        //when
        userDao.save(userModel);
        long userId = userModel.getId();
        User user = Helper.getUserByUserId(userId);
        user.setTel("invalidTel");
        //then
        assertThat(userService.updateUserProfile(userId, user)).isEqualTo(false);
    }

    @Test
    public void should_updateUserProfile_return_false_when_given_invalid_school() {
        //given
        UserModel userModel = Helper.getUserModel();
        //when
        userDao.save(userModel);
        long userId = userModel.getId();
        User user = Helper.getUserByUserId(userId);
        user.setSchool("This is a super long school name which is more than 64 characters.");
        //then
        assertThat(userService.updateUserProfile(userId, user)).isEqualTo(false);
    }

    @Test
    public void should_updateUserProfile_return_false_when_given_invalid_major() {
        //given
        UserModel userModel = Helper.getUserModel();
        //when
        userDao.save(userModel);
        long userId = userModel.getId();
        User user = Helper.getUserByUserId(userId);
        user.setMajor("This is a super long major name which is more than 64 characters.");
        //then
        assertThat(userService.updateUserProfile(userId, user)).isEqualTo(false);
    }
}
