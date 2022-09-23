package Model;

public class UsersTableDto {
    private Integer id;
    private String UserId;
    private String Name;
    private String Avatar;
    private String Email;
    private String UserName;
    private String KriptoversityUsername;

    public void setPassword(String password) {
        Password = password;
    }

    public String getPassword() {
        return Password;
    }

    private String Password;
    private Double TotalExp;
    private Integer last_iat;
    private Integer LeagueId;
    private Double WeeklyExp;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setKriptoversityUsername(String kriptoversityUsername) {
        KriptoversityUsername = kriptoversityUsername;
    }

    public void setTotalExp(Double totalExp) {
        TotalExp = totalExp;
    }

    public void setLast_iat(Integer last_iat) {
        this.last_iat = last_iat;
    }

    public void setLeagueId(Integer leagueId) {
        LeagueId = leagueId;
    }

    public void setWeeklyExp(Double weeklyExp) {
        WeeklyExp = weeklyExp;
    }

    public void setWeeklyLeague(Integer weeklyLeague) {
        WeeklyLeague = weeklyLeague;
    }

    public void setWeeklyWinner(Integer weeklyWinner) {
        WeeklyWinner = weeklyWinner;
    }

    public void setTokoUid(Integer tokoUid) {
        TokoUid = tokoUid;
    }

    public void setEmailVerified(String emailVerified) {
        EmailVerified = emailVerified;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return UserId;
    }

    public String getName() {
        return Name;
    }

    public String getAvatar() {
        return Avatar;
    }

    public String getEmail() {
        return Email;
    }

    public String getUserName() {
        return UserName;
    }

    public String getKriptoversityUsername() {
        return KriptoversityUsername;
    }

    public Double getTotalExp() {
        return TotalExp;
    }

    public Integer getLast_iat() {
        return last_iat;
    }

    public Integer getLeagueId() {
        return LeagueId;
    }

    public Double getWeeklyExp() {
        return WeeklyExp;
    }

    public Integer getWeeklyLeague() {
        return WeeklyLeague;
    }

    public Integer getWeeklyWinner() {
        return WeeklyWinner;
    }

    public Integer getTokoUid() {
        return TokoUid;
    }

    public String getEmailVerified() {
        return EmailVerified;
    }

    public String getStatus() {
        return Status;
    }

    private Integer WeeklyLeague;
    private Integer WeeklyWinner;
    private Integer TokoUid;
    private String EmailVerified;
    private String Status;
}
