package Tests;

import Helpers.CommonTestHelper;
import Model.Environment;
import Pages.Pages;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
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
import java.time.Duration;
import java.util.List;

import static Helpers.CommonTestHelper.pause;

public class TokonewsTest {
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


}
