package Models.Category.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Datum {
    public int id;
    @JsonProperty("CreatedBy")
    public String createdBy;
    @JsonProperty("UpdatedBy")
    public String updatedBy;
    @JsonProperty("DeletedBy")
    public Object deletedBy;
    @JsonProperty("NumberOfCourse")
    public int numberOfCourse;
    @JsonProperty("Status")
    public int status;
    @JsonProperty("Logo")
    public String logo;
    public Date createdAt;
    public Date updatedAt;
    public Object deletedAt;
    @JsonProperty("Name_id")
    public String name_id;
    @JsonProperty("Name_en")
    public String name_en;
    @JsonProperty("Description_id")
    public Object description_id;
    @JsonProperty("Description_en")
    public String description_en;
    @JsonProperty("ParentId")
    public Integer parentId;
    public Date startDate;
    public Object endDate;
}
