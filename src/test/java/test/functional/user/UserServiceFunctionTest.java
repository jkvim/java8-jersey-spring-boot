package test.functional.user;

import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.user.dao.UserDao;
import com.thoughtworks.gaia.user.entity.User;
import com.thoughtworks.gaia.user.model.UserModel;
import com.thoughtworks.gaia.user.service.UserService;
import com.thoughtworks.xstream.converters.javabean.JavaBeanConverter;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(GaiaApplication.class)
@Rollback
@Transactional
@ActiveProfiles({EnvProfile.TEST})
/**
 * Created by jkwang on 1/12/17.
 */
public class UserServiceFunctionTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Test(expected = NotFoundException.class)
    public void should_user_notexists_when_given_1() {
        //given
        //when
        //then
        userService.getUser(-1L);
    }

    @Test
    public void should_user_exists_when_given_2() {
        //given
        UserModel usermodel = new UserModel();
        usermodel.setUserTypeId(1L);
        usermodel.setName("student1");
        usermodel.setEmail("test@11.com");
        usermodel.setGender(true);
        usermodel.setMajor("math");
        usermodel.setPassword("test");
        usermodel.setSchool("xam");
        usermodel.setTel("11111111111");
        usermodel.setTimeCreated(DateTime.now().toDate());
        userDao.save(usermodel);
        //when
        long userid = usermodel.getId();
        long newuserid = userService.getUser(userid).getId();
        //then
        assertThat(userid).isEqualTo(newuserid);
    }

    @Test(expected = InvalidPropertyException.class)
    public void should_addUser_throw_exception_given_invalid_email() {
        userService.addUser("email","password");
    }

    @Test(expected = JavaBeanConverter.DuplicatePropertyException.class)
    public void should_addUser_throw_exception_given_existing_user() {
        //given
        String email = "fail@thoughtworks.com";
        //when
        UserModel userModel = new UserModel();
        userModel.setUserTypeId(1L);
        userModel.setEmail(email);
        userModel.setPassword("password");
        userModel.setGender(true);
        userDao.save(userModel);
        //then
        userService.addUser(email,"password");
    }

    @Test(expected = InvalidPropertyException.class)
    public void should_addUser_throw_exception_given_empty_password() {
        userService.addUser("fail@thoughtworks.com","");
    }

    @Test
    public void should_addUser_return_true() {
        userService.addUser("success@thoughtworks.com","password");
    }

    @Test
    public void should_update_user_fail_when_given_not_exist_user_id() {
        User user = new User();
        user.setName("Tim");
        user.setGender(true);
        user.setSchool("Jiaotong");
        user.setMajor("Electronic");
        user.setTel("12222");

        boolean notFoundException = false;
        try {
            userService.updateUserProfile(1L, user);
        } catch (NotFoundException e) {
            notFoundException = true;
        }

        assertThat(notFoundException).isEqualTo(true);
    }

    @Test
    public void should_update_user_success_when_given_correct_data() {
        UserModel userModel = new UserModel();
        userModel.setUserTypeId(1L);
        userModel.setEmail("test123@qq.com");
        userModel.setPassword("123");
        userModel.setName("Josh");
        userModel.setGender(true);
        userModel.setSchool("Xidian");
        userModel.setMajor("Computer");
        userModel.setTel("13298394937");
        userModel.setTimeCreated(DateTime.now().toDate());

        userDao.save(userModel);
        long userId = userModel.getId();

        User user = new User();
        user.setId(userId);
        user.setName("Tim");
        user.setGender(true);
        user.setSchool("Jiaotong");
        user.setMajor("Electronic");
        user.setTel("13877387648");

        assertThat(userService.updateUserProfile(userId, user)).isEqualTo(true);

        User user1 = userService.getUser(userId);
        assertThat(user1.getName()).isEqualTo(user.getName());
        assertThat(user1.getGender()).isEqualTo(user.getGender());
        assertThat(user1.getSchool()).isEqualTo(user.getSchool());
        assertThat(user1.getMajor()).isEqualTo(user.getMajor());
    }

    @Test
    public void should_update_user_fail_when_given_incorrect_data() {
        UserModel userModel = new UserModel();
        userModel.setUserTypeId(1L);
        userModel.setEmail("test123@qq.com");
        userModel.setPassword("123");
        userModel.setName("Josh");
        userModel.setGender(true);
        userModel.setSchool("Xidian");
        userModel.setMajor("Computer");
        userModel.setTel("13298394937");
        userModel.setTimeCreated(DateTime.now().toDate());

        userDao.save(userModel);
        long userId = userModel.getId();

        User user = new User();
        user.setId(userId);
        user.setName("Tim");
        user.setGender(true);
        user.setSchool("Jiaotong");
        user.setMajor("Electronic");
        user.setTel("12222");

        assertThat(userService.updateUserProfile(userId, user)).isEqualTo(false);
    }
}
