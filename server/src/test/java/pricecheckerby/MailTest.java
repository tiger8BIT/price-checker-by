import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pricecheckerby.Application;
import pricecheckerby.model.*;
import pricecheckerby.service.EmailService;
import pricecheckerby.subscription.mail.EmailMessageService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MailTest {
    @Autowired
    private EmailMessageService emailMessageService;
    private double expression(double x, double y){
        return 10*x-10*y+x*y;
    }
    @Test
    public void mailTest() {
        Assert.assertEquals("Check with values", expression(1, 1), 1);
    }
}
