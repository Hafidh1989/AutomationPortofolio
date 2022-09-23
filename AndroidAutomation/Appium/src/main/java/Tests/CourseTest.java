package Tests;

import Helpers.CommonTestHelper;
import Model.Environment;
import Model.UsersTableDto;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static Helpers.CommonTestHelper.pause;

public class CourseTest {
    public static AndroidDriver androidDriver;
    public static Environment env;
    String jsonEnv ="LabEnvironment.json";

    @BeforeEach
    public void BeforeEach() throws IOException {

        env = CommonTestHelper.Environment(jsonEnv);
        String fileName = env.getApkName();
        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        Path filePath = Paths.get(root.toString(),"src", "main", "resources", fileName);

        //Clean course
        deleteCourseHistory();

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel");
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        caps.setCapability(MobileCapabilityType.UDID, env.getMobileUdid());
        caps.setCapability("ignoreHiddenApiPolicyError", true);
        caps.setCapability("avd","Pixel");
        caps.setCapability(MobileCapabilityType.NO_RESET,"false");
        caps.setCapability("avdLaunchTimeOut", 10980000);
        caps.setCapability("avdReadyTimeout", 10980000);
        caps.setCapability(MobileCapabilityType.APP, filePath.toString());

        URL url = new URL("http://0.0.0.0:4723/wd/hub");

        androidDriver = new AndroidDriver(url, caps);
    }

    @AfterEach
    public void TearDown() throws IOException {
        androidDriver.closeApp();
    }

    @Test
    public void TakeCourse() throws InterruptedException, IOException {
        CommonTestHelper.Login(androidDriver, env.getUsername(),env.getPassword());
        pause(5000);

        List<MobileElement> courseCategory = (List<MobileElement>) androidDriver.
                findElements(By.id("com.tokoacademy:id/tv_category_name"));

        courseCategory.get(4).click();
        pause(9000);
        List<MobileElement> courseTitle = (List<MobileElement>) androidDriver
                .findElements(By.id("com.tokoacademy:id/tv_course_title_complete"));
        courseTitle.get(0).click();
        pause(10000);
        List<MobileElement> courses = (List<MobileElement>) androidDriver
                .findElements(By.id("com.tokoacademy:id/tv_course_title"));
        courses.get(0).click();

        pause(9000);
        CommonTestHelper.TakeScreenShoot(androidDriver, "firstQuestion");

        // Start answering a question
        CommonTestHelper.waitUntilVisible(androidDriver,60,"com.tokoacademy:id/btn_next_note");
        androidDriver.findElementById("com.tokoacademy:id/btn_next_note").click();
        pause(5000);
        List<MobileElement> answers = (List<MobileElement>) androidDriver.
                findElements(By.id("com.tokoacademy:id/tv_answer"));
        answers.get(2).click();
        pause(1000);
        androidDriver.findElementById("com.tokoacademy:id/btn_next_mct").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_continue_true").click();
        pause(1000);

        androidDriver.findElementById("com.tokoacademy:id/btn_next_note").click();
        pause(1000);
        androidDriver.findElementById("com.tokoacademy:id/et_fib").sendKeys("bappebti");
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_next_fib").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_continue_true").click();

        androidDriver.findElementById("com.tokoacademy:id/btn_next_note").click();
        pause(1000);
        androidDriver.findElementById("com.tokoacademy:id/et_fib").
                sendKeys("the global economy");
        androidDriver.findElementById("com.tokoacademy:id/btn_next_fib").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_continue_true").click();
        pause(5000);

        androidDriver.findElementById("com.tokoacademy:id/btn_next_note").click();
        answers.clear();
        answers = (List<MobileElement>) androidDriver.
                findElements(By.id("com.tokoacademy:id/tv_answer"));
        answers.get(1).click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_next_mct").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_continue_true").click();
        pause(1000);

        androidDriver.findElementById("com.tokoacademy:id/btn_next_note").click();
        pause(1000);
        androidDriver.findElementById("com.tokoacademy:id/et_fib").
                sendKeys("binance cloud");
        androidDriver.findElementById("com.tokoacademy:id/btn_next_fib").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_continue_true").click();
        pause(10000);

        //Lesson Complete
        CommonTestHelper.TakeScreenShoot(androidDriver,"lessonComplete");
        androidDriver.findElementById("com.tokoacademy:id/btn_continue").click();
        pause(5000);
        String courseStatus = androidDriver.
                findElementById("com.tokoacademy:id/tv_course").getText();
        Assertions.assertEquals("1/2 Courses", courseStatus);

        androidDriver.findElementById("com.tokoacademy:id/iv_back").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/iv_back").click();
        pause(5000);

        courseCategory = (List<MobileElement>) androidDriver.
                findElements(By.id("com.tokoacademy:id/tv_category_name"));

        courseCategory.get(6).click();
        pause(9000);
        courseTitle = (List<MobileElement>) androidDriver
                .findElements(By.id("com.tokoacademy:id/tv_course_title_complete"));
        courseTitle.get(0).click();
        pause(10000);
        courses = (List<MobileElement>) androidDriver
                .findElements(By.id("com.tokoacademy:id/tv_course_title"));
        courses.get(0).click();
        pause(10000);

        // Start taking the second course
        // transparent
        androidDriver.findElementById("com.tokoacademy:id/btn_next_note").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/et_fib").
                sendKeys("transparent");
        androidDriver.findElementById("com.tokoacademy:id/btn_next_fib").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_continue_true").click();
        pause(5000);

        // A
        androidDriver.findElementById("com.tokoacademy:id/btn_next_note").click();
        pause(5000);
        answers.clear();
        answers = (List<MobileElement>) androidDriver.
                findElements(By.id("com.tokoacademy:id/tv_answer"));
        answers.get(0).click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_next_mct").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_continue_true").click();
        pause(1000);

        // lowest
        androidDriver.findElementById("com.tokoacademy:id/btn_next_note").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/et_fib").
                sendKeys("lowest");
        androidDriver.findElementById("com.tokoacademy:id/btn_next_fib").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_continue_true").click();
        pause(5000);

        // partners
        androidDriver.findElementById("com.tokoacademy:id/btn_next_note").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/et_fib").
                sendKeys("partners");
        androidDriver.findElementById("com.tokoacademy:id/btn_next_fib").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_continue_true").click();
        pause(5000);

        // binance idr
        androidDriver.findElementById("com.tokoacademy:id/btn_next_note").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/et_fib").
                sendKeys("binance idr");
        androidDriver.findElementById("com.tokoacademy:id/btn_next_fib").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_continue_true").click();
        pause(5000);

        // C
        androidDriver.findElementById("com.tokoacademy:id/btn_next_note").click();
        pause(5000);
        answers.clear();
        answers = (List<MobileElement>) androidDriver.
                findElements(By.id("com.tokoacademy:id/tv_answer"));
        answers.get(2).click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_next_mct").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/btn_continue_true").click();
        pause(10000);

        //Lesson Complete
        CommonTestHelper.TakeScreenShoot(androidDriver,"lessonComplete");
        androidDriver.findElementById("com.tokoacademy:id/btn_continue").click();
        pause(5000);

        androidDriver.findElementById("com.tokoacademy:id/iv_back").click();
        pause(5000);
        androidDriver.findElementById("com.tokoacademy:id/iv_back").click();
        pause(5000);

        androidDriver.findElementById("com.tokoacademy:id/myCourseNewFragment").click();
        pause(5000);
        androidDriver.findElementByXPath("//android.widget.LinearLayout[@content-desc=\"COMPLETED\"]").click();
        pause(5000);
        CommonTestHelper.TakeScreenShoot(androidDriver,"CompletedCourse");
        String completeCourse = androidDriver.findElementById("com.tokoacademy:id/tv_category_title_complete").getText();
        System.out.println(completeCourse);

        // filter search on my course
        androidDriver.findElementById("com.tokoacademy:id/iv_filter").click();
        pause(5000);
        courseCategory = (List<MobileElement>) androidDriver.
                findElements(By.id("com.tokoacademy:id/tv_title"));

        for(MobileElement course : courseCategory){
            pause(1000);
            if(course.getText().contains("Toko Token")){
                course.click();
                pause(5000);
                break;
            }
        }
        Assertions.assertEquals(completeCourse,
                androidDriver.findElementById("com.tokoacademy:id/tv_category_title_complete").getText());
        pause(5000);
    }

    //Local helper
    private void deleteCourseHistory(){
        //Get Client ID
        UsersTableDto userDetails = CommonTestHelper.GetUserInformation(env, env.getUsername());
        String Id = String.valueOf(userDetails.getId());

        String Query = "delete from attempt_lessons al where attemptId in( " +
                "select id from attempts a where userId = " + Id +")";

        CommonTestHelper.DeleteUpdateDB(env, Query);

        Query = "delete from attempts a where userId = " + Id;
        CommonTestHelper.DeleteUpdateDB(env, Query);
    }

}
