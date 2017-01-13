package test.functional.user;

import com.exmertec.yaz.core.Query;
import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.user.dao.UserDao;
import com.thoughtworks.gaia.user.entity.User;
import com.thoughtworks.gaia.user.model.UserModel;
import com.thoughtworks.gaia.user.service.UserService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sun.plugin.util.UserProfile;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.List;

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
    private UserDao userDao1;

    @Test(expected = NotFoundException.class)
    public void should_user_notexists_function_when_given_1() {
        //given
        //when
        //then
        userService.getUser((long) -1);
    }

    @Test
    public void should_user_exists_function_when_given_2() {
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
        long userid =usermodel.getId();
        long newuserid = userService.getUser(userid).getId();
        //when
        //then
        assertThat(userid).isEqualTo(newuserid);
    }

    @Test
    public void should_patch_user_111_profile_fail() {

    }

    @Test
    public void should_find_user_by_name_return_true() {

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

        List<UserModel> mlist = userDao.findUserByName("Josh");
        assertThat(mlist.size()).isEqualTo(1);
        assertThat(mlist.get(0).getName()).isEqualTo("Josh");
    }
}
