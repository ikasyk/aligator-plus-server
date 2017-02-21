import com.aligatorplus.db.service.UserService;
import com.aligatorplus.model.User;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import javax.persistence.EntityNotFoundException;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/config/test.xml"})
@Transactional
public class UserServiceTest {
    final static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
    @Autowired
    private UserService userService;

    @Test
    public void crudTest() {
        Random random = new Random(System.nanoTime());

        String uniqueName = "test" + random.nextInt(9999);
        User testUser = new User(uniqueName, uniqueName + "@example.com", "123456");
        userService.create(testUser);

        User foundByLogin = userService.findByLogin(uniqueName);
        Assert.assertEquals("User by login not found.", testUser, foundByLogin);

        User foundByEmail = userService.findByEmail(uniqueName + "@example.com");
        Assert.assertEquals("User by email not found.", testUser, foundByEmail);

        foundByLogin.setEmail(uniqueName + "@example2.com");
        userService.update(foundByLogin);
        User checkUpdate = userService.findByEmail(uniqueName + "@example2.com");
        Assert.assertNotNull(checkUpdate);

        userService.delete(checkUpdate);
        try {
            userService.findById(checkUpdate.getId());
            Assert.fail("No EntityNotFoundException thrown.");
        } catch (JpaObjectRetrievalFailureException exception) {
            Assert.assertNotNull(exception.getMessage());
        }
    }
}
