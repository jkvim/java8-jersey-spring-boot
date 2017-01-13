package test.functional.user;

import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.user.dao.UserDao;
import com.thoughtworks.gaia.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    public void should_user_notexists_function_when_given_1() {
        //given
        //when
        //then
        userService.getUser((long) -1);
    }


    @Test
    public void should_user_exists_function_when_given_1() {
        //given
        //when
        //then
        assertThat(new UserService().getUser((long) 1).getId()).isEqualTo((long) 1);
    }
}
