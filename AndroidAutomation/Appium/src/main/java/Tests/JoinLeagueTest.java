package Tests;

import Helpers.CommonTestHelper;
import Model.Environment;
import Model.UsersTableDto;
import Pages.Pages;
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

public class JoinLeagueTest {
    public static AndroidDriver androidDriver;
    public static Environment env;
    String jsonEnv ="LabEnvironment.json";

    @BeforeEach
    public void BeforeEach() throws IOException {

        env = CommonTestHelper.Environment(jsonEnv);
        String fileName = env.getApkName();
        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        Path filePath = Paths.get(root.toString(),"src", "main", "resources", fileName);
        CleanLeague();

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
    public void JoinLeagueTest() throws InterruptedException, IOException {
        CommonTestHelper.Login(androidDriver, env.getUsername(),env.getPassword());
        pause(5000);

        androidDriver.findElementById(Pages.BTN_RANKINGS).click();
        pause(5000);
        androidDriver.findElementById(Pages.JoinLeague).click();
        pause(8000);

        //I should see weekly ranking page
        Assertions.assertTrue(androidDriver.
                findElementByXPath("//android.widget.FrameLayout[@content-desc=\"Weekly Ranking\"]").isDisplayed());
        CommonTestHelper.TakeScreenShoot(androidDriver, "WeeklyRanking");
    }

    private void CleanLeague(){
        //Client Join League
        //Need to reset WeeklyLeague into null so we can rejoin the league
        String Query = "update users set WeeklyLeague = null where Email = "
                + "'" + env.getUsername() + "'";
        CommonTestHelper.DeleteUpdateDB(env, Query);
    }
}
