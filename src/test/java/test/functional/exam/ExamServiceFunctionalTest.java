package test.functional.exam;

import com.thoughtworks.gaia.GaiaApplication;
import com.thoughtworks.gaia.common.constant.EnvProfile;
import com.thoughtworks.gaia.common.exception.NotFoundException;
import com.thoughtworks.gaia.exam.dao.ExamDao;
import com.thoughtworks.gaia.exam.service.ExamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.util.AssertionErrors.assertEquals;


/**
 * Created by jkwang on 1/12/17.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(GaiaApplication.class)
@Rollback
@Transactional
@ActiveProfiles({EnvProfile.TEST})
public class ExamServiceFunctionalTest {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamDao examDao;

    @Test(expected = NotFoundException.class)
    public void should_exam_notexists_function_when_given_1() {
        examService.getExam(1L);
    }


    @Test
    public  void should_exam_exists_function_when_given_1(){
        //given
        //when
        //then
        assertEquals("", examService.getExam((long) 2),null);
    }
}
