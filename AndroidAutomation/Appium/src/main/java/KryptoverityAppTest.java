import Helpers.CommonTestHelper;
import Model.Environment;
import Model.UsersTableDto;
import Pages.Pages;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static Helpers.CommonTestHelper.pause;
import static Helpers.CommonTestHelper.waitUntilVisible;


public class KryptoverityAppTest {
    public static AndroidDriver androidDriver;
    public static Environment env;
    String jsonEnv ="LabEnvironment.json";

    @BeforeEach
    public void BeforeEach() throws IOException {

        env = CommonTestHelper.Environment(jsonEnv);
        String fileName = env.getApkName();
        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        Path filePath = Paths.get(root.toString(),"src", "main", "resources", fileName);
        String xx = "/Users/ahmadhafiidh/Downloads/kriptoversity.apk";

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
        caps.setCapability(MobileCapabilityType.APP, xx);

        URL url = new URL("http://0.0.0.0:4723/wd/hub");

        androidDriver = new AndroidDriver(url, caps);
    }

    @AfterEach
    public void TearDown() throws IOException {
        androidDriver.closeApp();
    }

    @Test
    public void UserCanSearchTheirAccountOnSearchFriendFeature() throws Exception
    {
        try{
            CommonTestHelper.Login(androidDriver, env.getUsername(),env.getPassword());

            //Get User Name
            AndroidElement textView = (AndroidElement) androidDriver.findElementById(Pages.USERNAME);
            String Username = textView.getText();

            //Click on ProfileButton
            CommonTestHelper.waitUntilVisible(androidDriver, 100, Pages.PROFILE_FRAGMENT);
            androidDriver.findElementById(Pages.PROFILE_FRAGMENT).click();

            //Click on Settings
            CommonTestHelper.waitUntilVisible(androidDriver, 100,Pages.BTN_SETTING);
            androidDriver.findElementById(Pages.BTN_SETTING).click();

            //Click on FindFriend
            CommonTestHelper.waitUntilVisible(androidDriver,100, Pages.BTN_SEARCH_FRIEND);
            androidDriver.findElementById(Pages.BTN_SEARCH_FRIEND).click();

            //search own account
            pause(500);
            androidDriver.findElementById(Pages.TXT_SEARCH_FRIEND).sendKeys(Username);
            CommonTestHelper.waitUntilVisible(androidDriver, 1000,Pages.USERNAME);

/*
            // check if we can find username on search dropdown and go to profile page
            pause(1000);
            Assertions.assertTrue(androidDriver.findElementById(Pages.USERNAME).isDisplayed());
            androidDriver.findElementById(Pages.USERNAME).click();
            pause(5000);
            Assertions.assertTrue(androidDriver.findElementById(Pages.BTN_CLAIM_REWARD).isDisplayed());*/

        }catch (AssertionError e)
        {
            String errorLog = String.format("An Error Occured: %s", e);
            System.out.println(errorLog);
        }
    }

    @Test
    public void Tokonews() throws InterruptedException, IOException {
        String TabCategory = "Blockchain";
        CommonTestHelper.Login(androidDriver, env.getUsername(),env.getPassword());
        pause(100);
        androidDriver.findElementById(Pages.BTN_TOKONEWS).isDisplayed();
        androidDriver.findElementById(Pages.BTN_TOKONEWS).click();
        CommonTestHelper.waitUntilVisible(androidDriver,100,Pages.News_Title);
        androidDriver.findElementByAccessibilityId(TabCategory).click();
        pause(900);
        CommonTestHelper.waitUntilVisible(androidDriver,100,Pages.News_Title);
        pause(6000);
        String categoryTab = androidDriver.findElementById(Pages.Category_Title).getText();
        CommonTestHelper.TakeScreenShoot(androidDriver, "TokonewsCategory");
        Assertions.assertEquals(categoryTab, TabCategory);

        //Select Category Tab
        //Click on 3 tab to select category
        androidDriver.findElementById(Pages.Category_Choose).click();
        pause(5000);
        CommonTestHelper.TakeScreenShoot(androidDriver, "BeforeAddCategory");
        List<MobileElement> listCategoryName = (List<MobileElement>) androidDriver.
                findElements(By.id(Pages.Category_List_Title_Name));
        String SelectedCategoryName = listCategoryName.get(4).getText();
        List<MobileElement> listPlusButton = (List<MobileElement>) androidDriver.
                findElements(By.id(Pages.Category_Add));
        listPlusButton.get(4).click();
        pause(1000);
        CommonTestHelper.TakeScreenShoot(androidDriver, "AfterAddCategory");
        androidDriver.findElementById("com.tokoacademy:id/iv_back").click();
        pause(5000);
        for(int i=0 ; i<8;i++){
            TouchAction t = new TouchAction(androidDriver);
            t.press(PointOption.point(975, 988))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                    .moveTo(PointOption.point(52,978))
                    .release()
                    .perform();
        }
        pause(2000);
        CommonTestHelper.TakeScreenShoot(androidDriver,"NewTabIsPresent");

        // Deleting category
        androidDriver.findElementById(Pages.Category_Choose).click();
        pause(1000);
        List<MobileElement> listMinButton = (List<MobileElement>) androidDriver.
                findElements(By.id(Pages.Category_Delete));
        listMinButton.get(0).click();
        pause(1000);
        CommonTestHelper.TakeScreenShoot(androidDriver,"AfterDeleteCategory");
        androidDriver.findElementById(Pages.BackButton).click();
        pause(1000);

        // Search page
        androidDriver.findElementById(Pages.Search).click();
        pause(1000);
        CommonTestHelper.TakeScreenShoot(androidDriver, "SearchArticlePage");
        androidDriver.findElementById(Pages.Search_Box).sendKeys("pajak");
        pause(9000);
        CommonTestHelper.TakeScreenShoot(androidDriver,"SearchResult");

        // search by topics
        androidDriver.findElementById(Pages.BackButton).click();
        pause(1000);
        androidDriver.findElementById(Pages.Category_Choose).click();
        pause(1000);
        List<MobileElement> listCategory = (List<MobileElement>) androidDriver.
                findElements(By.id(Pages.Category_List_Title_Name));
        listCategory.get(0).click();
        pause(9000);
        CommonTestHelper.TakeScreenShoot(androidDriver,"SearchByCategory");
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
