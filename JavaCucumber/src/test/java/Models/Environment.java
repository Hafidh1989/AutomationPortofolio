package Models;

public class Environment {
    public boolean isProduction;
    public String adminUserName;
    public String adminPassword;
    public String clientUserName;

    public Environment(boolean isProduction, String adminUserName, String adminPassword,
                       String clientUserName, String clientPassword, String baseUrl, String dbHost,
                       String dbUser, String dbPassword, String redisConnect, String qaseApiKey) {
        this.isProduction = isProduction;
        this.adminUserName = adminUserName;
        this.adminPassword = adminPassword;
        this.clientUserName = clientUserName;
        this.clientPassword = clientPassword;
        this.baseUrl = baseUrl;
        this.dbHost = dbHost;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.redisConnect = redisConnect;
        this.qaseApiKey = qaseApiKey;
    }

    public String clientPassword;
    public String baseUrl;
    public String dbHost;
    public String dbUser;
    public String dbPassword;
    public String redisConnect;
    public String qaseApiKey;
}
