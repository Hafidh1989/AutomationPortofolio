package Helpers;

import Models.Environment;
import Models.Login.Requests.CreateLoginDto;
import Models.Login.Responses.LoginDto;
import com.google.gson.Gson;
import okhttp3.*;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommonTestHelpers {
    public static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public static Environment Environment(String jsonFile) throws IOException {
        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        Path filePath = Paths.get(root.toString(),"src", "main", "resources", jsonFile);

        String json = readFileAsString(filePath.toString());
        Gson gjson = new Gson();
        Environment env = gjson.fromJson(json, Environment.class);
        return env;
    }

    public static String GetAdminToken(Environment env) throws IOException {
        String Token;
        Gson gson = new Gson();
        CreateLoginDto CreateLoginDto = new CreateLoginDto(env.adminUserName, env.adminPassword);
        String requestBody = gson.toJson(CreateLoginDto);

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url(env.baseUrl + "auth/admin/login")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("Token", env.qaseApiKey)
                .build();

        Response rspn = client.newCall(request).execute();
        String respons = rspn.body().string();
        LoginDto loginDto = gson.fromJson(respons, LoginDto.class);

        return Token = loginDto.data.idToken.jwtToken;
    }

    public static String encodeFileToBase64Binary(File file) throws Exception{
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
    }

}
