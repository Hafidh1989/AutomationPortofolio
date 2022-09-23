package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/features/AdminCategoryTest.feature"},
        glue = {"Steps"}
)

public class Runner extends AbstractTestNGCucumberTests {

}
