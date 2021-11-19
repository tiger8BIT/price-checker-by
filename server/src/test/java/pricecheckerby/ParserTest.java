package pricecheckerby;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import pricecheckerby.Application;
import pricecheckerby.parser.ParsersExecutorServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:data.sql")
public class ParserTest {
    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public ParsersExecutorServiceImpl parsersExecutor() {
            return new ParsersExecutorServiceImpl();
        }
    }
    @Autowired
    ParsersExecutorServiceImpl parsersExecutor;
    @Test
    public void test() {
        parsersExecutor.execute();
    }
}
