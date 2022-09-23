package Model;

public class Environment {
    private String username;
    private String password;
    private String apkName;
    private String mobileUdid;

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    private String dbHost;

    public String getDbHost() {
        return dbHost;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    private String dbUser;
    private String dbPassword;

    public String getUsername(){return this.username;}
    public void setUsername(String username){this.username = username;}

    public String getPassword(){return this.password;}
    public void setPassword(String password){this.password = password;};

    public String getApkName(){return this.apkName;}
    public void setApkName(String apkName){ this.apkName = apkName;}

    public String getMobileUdid(){return this.mobileUdid;}
    public void setMobileUdid(String mobileUdid){this.mobileUdid = mobileUdid;}

}

//Test