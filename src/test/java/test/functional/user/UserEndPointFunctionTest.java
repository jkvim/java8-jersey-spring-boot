package test.functional.user;

import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.user.endpoint.UserEndPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by jkwang on 1/13/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(GaiaApplication.class)
@Rollback
@Transactional
@ActiveProfiles({EnvProfile.TEST})
public class UserEndPointFunctionTest {

    @Autowired
    private UserEndPoint userEndPoint;

    @Test(expected = NotFoundException.class)
    public void should_user_notexists_when_given_1() {
        userEndPoint.getUser((long) -1);
    }

    @Test
    public void should_return_status_is_200() {
        assertThat(userEndPoint.getUser(1L).getStatus()).isEqualTo(200);
    }
}
