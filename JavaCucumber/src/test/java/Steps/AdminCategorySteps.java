package Steps;

import Helpers.CommonTestHelpers;
import Models.Category.Request.CreateCategoryDto;
import Models.Category.Response.CategoryDto;
import Models.Category.Response.CategoryListDto;
import Models.Category.Response.Datum;
import Models.Environment;
import com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.*;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class AdminCategorySteps {

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    public String AppSettings = "appSettings.json";
    public String BearerToken;
    public Environment env;
    public CreateCategoryDto createCategoryDto;
    public Response rspn;
    public String json;
    public CategoryListDto categoryListDto;
    public CategoryDto categoryDto;
    public String requestUrlCategoryDetails;
    public String leagueId;


    public AdminCategorySteps() throws Exception {

        requestUrlCategoryDetails = "kv-category/category/";

        env = CommonTestHelpers.Environment(AppSettings);
        BearerToken = CommonTestHelpers.GetAdminToken(env);
        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        Path filePath = Paths.get(root.toString(),"src", "main", "resources", "Image.png");
        File file = new File(String.valueOf(filePath));
        String Base64Img = CommonTestHelpers.encodeFileToBase64Binary(file);

        Map<String, String> name = new HashMap(){
            {
                put("id","CategoryAutomation");
                put("en","CategoryAutomation");
            }
        };

        Map<String, String> description = new HashMap(){
            {
            put("id", "description");
            put("en", "description");
            }
        };

        createCategoryDto = new CreateCategoryDto();
        createCategoryDto.setLogo_content(Base64Img);
        createCategoryDto.setLogo_extension("jpg");
        createCategoryDto.setStartDate("2022-01-01");
        createCategoryDto.setEndDate("2023-01-01");
        createCategoryDto.setName(name);
        createCategoryDto.setDescription(description);
        createCategoryDto.setParent_id(null);
    }

    @Given("I want to add new category with out providing category name")
    public void iWantToAddNewCategoryWithOutProvidingCategoryName() {
        Map<String, String> name = new HashMap(){
            {
                put("id"," ");
                put("en"," ");
            }
        };
        createCategoryDto.setName(name);
    }

    @When("I add new category")
    public void iAddNewCategory() throws IOException {
        Gson gson = new Gson();
        String requestBody = gson.toJson(createCategoryDto);

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url(env.baseUrl + "kv-category/category")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("Authorization", "Bearer " + BearerToken)
                .build();

        rspn = client.newCall(request).execute();
        json = rspn.body().string();
        System.out.println(json);
    }

    @Then("The add category should return HTTP status code 400 Bad Request")
    public void theAddCategoryShouldReturnHTTPStatusCodeBadRequest() {

        Assert.assertEquals(400, rspn.code());
    }

    @Given("I want to add new category with invalid logo extention")
    public void iWantToAddNewCategoryWithInvalidLogoExtention() {
        createCategoryDto.setLogo_extension("PPD");
    }

    @Given("I want to add new category with non existent parent id")
    public void iWantToAddNewCategoryWithNonExistentParentId() {
        createCategoryDto.setParent_id(0);
    }

    @Given("I want to add new category with invalid date format on start date")
    public void iWantToAddNewCategoryWithInvalidDateFormatOnStartDate() {
        createCategoryDto.setStartDate("0000-15-40");
    }

    @Given("I want to add new category with invalid date format on end date")
    public void iWantToAddNewCategoryWithInvalidDateFormatOnEndDate() {
        createCategoryDto.setEndDate("0000-01-01");
    }

    @Given("I want to add new category with start date higher than end date")
    public void iWantToAddNewCategoryWithStartDateHigherThanEndDate() {
        createCategoryDto.setStartDate("2024-01-01");
        createCategoryDto.setEndDate("2023-01-01");
    }

    @Then("The add category should return HTTP status code 200 Ok")
    public void theAddCategoryShouldReturnHTTPStatusCodeOk() throws IOException {
        Assert.assertEquals(200, rspn.code());
    }

    @When("I get list category")
    public void iGetListCategory() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(env.baseUrl + "kv-category/category/list")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + BearerToken)
                .build();

        rspn = client.newCall(request).execute();
        json = rspn.body().string();
        System.out.println(json);
    }

    @Then("The get category list should return HTTP status code 200 Ok")
    public void theGetCategoryListShouldReturnHTTPStatusCodeOk() {
        Gson gson = new Gson();
        categoryListDto = gson.fromJson(json, CategoryListDto.class);
        Assert.assertEquals(200, rspn.code());
    }

    @Given("I want to get category details with non existent id")
    public void iWantToGetCategoryDetailsWithNonExistentId() {
        requestUrlCategoryDetails += "0/detail";
    }

    @When("I get category details")
    public void iGetCategoryDetails() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(env.baseUrl + requestUrlCategoryDetails)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + BearerToken)
                .build();

        rspn = client.newCall(request).execute();
        json = rspn.body().string();
        System.out.println(json);
    }

    @Then("the get category details return 400 Bad Request")
    public void theGetCategoryDetailsReturnBadRequest() {
        Assert.assertEquals(400, rspn.code());
    }

    @Given("I want to get category details with existent id")
    public void iWantToGetCategoryDetailsWithExistentId() {
        List<Datum> datas = new ArrayList<Datum>();
        datas = categoryListDto.data;
        for(Datum val : datas){
            if(val.parentId == null){
                leagueId = String.valueOf(val.id);
                break;
            }

        }
        requestUrlCategoryDetails += leagueId + "/detail";
        System.out.println(requestUrlCategoryDetails);
    }

    @Then("the get category details return 200 Ok")
    public void theGetCategoryDetailsReturnOk() {
        Gson gson = new Gson();
        categoryDto = gson.fromJson(json, CategoryDto.class);
        Assert.assertEquals(200, rspn.code());
        Assert.assertEquals(leagueId, String.valueOf(categoryDto.data.id));
        
    }

    @When("I get list of parent category")
    public void iGetListOfParentCategory() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(env.baseUrl + "kv-category/admin/parent")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + BearerToken)
                .build();

        rspn = client.newCall(request).execute();
        json = rspn.body().string();
        System.out.println(json);
    }

    @Then("the get parent category list should return HTTP status code 200 Ok")
    public void theGetParentCategoryListShouldReturnHTTPStatusCodeOk() {
        Assert.assertEquals(200,rspn.code());
        Gson gson = new Gson();
        categoryListDto = gson.fromJson(json, CategoryListDto.class);
        List<Datum> datas = new ArrayList<Datum>();
        datas = categoryListDto.data;

        for(Datum val : datas){
            Assert.assertNull(val.parentId);
        }
    }
}
