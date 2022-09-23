package Helpers;

import Model.Environment;
import Model.UsersTableDto;
import Pages.Pages;
import com.google.gson.Gson;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CommonTestHelper {
    private Environment env;
    public static MobileElement mobileElement;
    public static boolean isElementPresent;

    public static void pause(int milli) throws InterruptedException {
        pause(milli, TimeUnit.MILLISECONDS);
    }

    public static void pause(int time, TimeUnit timeunit) throws InterruptedException {
        timeunit.sleep(time);
    }

    public static void waitUntilVisible(AndroidDriver driver, int timeLimitInSeconds, String targetResourceId) throws InterruptedException {
        do{
            isElementPresent = false;
            try{
                mobileElement = (MobileElement) driver.findElementById(targetResourceId);
                WebDriverWait wait = new WebDriverWait(driver, timeLimitInSeconds);
                wait.until(ExpectedConditions.visibilityOf(mobileElement));
                driver.findElementById(targetResourceId).isDisplayed();
                isElementPresent = true;
            }catch (Exception e)
            {
                isElementPresent = false;
            }

        }while(!isElementPresent);
    }

    public static Environment Environment(String jsonFile) throws IOException {
        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        Path filePath = Paths.get(root.toString(),"src", "main", "resources", jsonFile);

        String json = readFileAsString(filePath.toString());
        Gson gjson = new Gson();
        Environment env = gjson.fromJson(json, Environment.class);
        return env;
    }

    public static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public static void TakeScreenShoot(AndroidDriver androidDriver, String TestName) throws IOException {
        File srcFile=androidDriver.getScreenshotAs(OutputType.FILE);
        String filename= TestName + "-" + java.time.LocalDate.now() +"-"+ GetRandomString();

        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        Path filePath = Paths.get(root.toString(),"TestResult", filename);
        File targetFile=new File(filePath.toString() +".jpg");
        FileUtils.copyFile(srcFile,targetFile);
    }

    public static String GetRandomString(){
        int length = 7;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = (RandomStringUtils.random(length, useLetters, useNumbers)).toLowerCase();
        return generatedString;
    }

    public static void Login(AndroidDriver androidDriver, String Email, String Password) throws InterruptedException
    {

        waitUntilVisible(androidDriver, 60, Pages.ENGLISH_LANGUAGE);
        androidDriver.findElementById(Pages.ENGLISH_LANGUAGE).click();
        pause(1000);

        waitUntilVisible(androidDriver, 60, Pages.BTN_LANGUAGE_SELECT);
        androidDriver.findElementById(Pages.BTN_LANGUAGE_SELECT).click();
        pause(1000);

        waitUntilVisible(androidDriver, 60, Pages.BTN_SIGN_IN);
        androidDriver.findElementById(Pages.BTN_SIGN_IN).click();
        pause(1000);

        waitUntilVisible(androidDriver, 60, Pages.TXT_EMAIL);
        androidDriver.findElementById(Pages.TXT_EMAIL).sendKeys(Email);
        pause(1000);

        waitUntilVisible(androidDriver, 60, Pages.TXT_PASSWORD);
        androidDriver.findElementById(Pages.TXT_PASSWORD).sendKeys(Password);
        pause(1000);

        waitUntilVisible(androidDriver, 60, Pages.BTN_LOGIN);
        androidDriver.findElementById(Pages.BTN_LOGIN).click();
        pause(18000);

        //check if login succesfully
        Assertions.assertTrue(true, String.valueOf(androidDriver.findElementById(Pages.USERNAME).isDisplayed()));
    }

    public static void DeleteUpdateDB(Environment env,String Query){
        try (Connection conn = DriverManager.getConnection(
                env.getDbHost(), env.getDbUser(), env.getDbPassword())) {

            Statement stmt = conn.createStatement();
            String query1 = Query;
            stmt.executeUpdate(query1);
            System.out.println(Query +" executed succesfully");

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UsersTableDto GetUserInformation(Environment env, String Email){
        String Query ="";
        if(Email == null){
            Query = "select id, userId, Name, Avatar, Email, UserName, KriptoversityUserName," +
                    " Password, TotalExp,  LeagueId, WeeklyExp, WeeklyLeague, WeeklyWinner," +
                    " TokoUid, EmailVerified, Status from users where status = 'active'" +
                    " and EmailVerified = 'true'" +
                    " limit 1";
        }
        else{
            Query = "select id, userId, Name, Avatar, Email, UserName, KriptoversityUserName," +
                    " Password, TotalExp,  LeagueId, WeeklyExp, WeeklyLeague, WeeklyWinner," +
                    " TokoUid, EmailVerified, Status " +
                    " from users where Email = '" + Email + "'";
        }

        UsersTableDto UserInformation = new UsersTableDto();
        try (Connection conn = DriverManager.getConnection(
                env.getDbHost(), env.getDbUser(), env.getDbPassword());
             Statement statement = conn.createStatement()) {

            if (conn != null) {
            } else {
                System.out.println("Failed to make connection!");
            }

            ResultSet resultSet = statement.executeQuery(Query);
            while(resultSet.next()) {
                UserInformation.setId(resultSet.getInt("id"));
                UserInformation.setUserId(resultSet.getString("UserId"));
                UserInformation.setName(resultSet.getString("Name"));
                UserInformation.setAvatar(resultSet.getString("Avatar"));
                UserInformation.setEmail(resultSet.getString("Email"));
                UserInformation.setUserName(resultSet.getString("UserName"));
                UserInformation.setKriptoversityUsername(resultSet.getString("KriptoversityUsername"));
                UserInformation.setPassword(resultSet.getString("Password"));
                UserInformation.setTotalExp(resultSet.getDouble("TotalExp"));
                UserInformation.setLeagueId(resultSet.getInt("LeagueId"));
                UserInformation.setWeeklyExp(resultSet.getDouble("WeeklyExp"));
                UserInformation.setWeeklyLeague(resultSet.getInt("WeeklyLeague"));
                UserInformation.setWeeklyWinner(resultSet.getInt("WeeklyWinner"));
                UserInformation.setTokoUid(resultSet.getInt("TokoUid"));
                UserInformation.setEmailVerified(resultSet.getString("EmailVerified"));
                UserInformation.setStatus(resultSet.getString("Status"));
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UserInformation;
    }

}
