package Models.Category.Request;

import java.util.Map;

public class CreateCategoryDto {
    private Map<String, String> name;
    private Map<String, String> description;

    public String getLogo_content() {
        return logo_content;
    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public void setLogo_content(String logo_content) {
        this.logo_content = logo_content;
    }

    public String getLogo_extension() {
        return logo_extension;
    }

    public void setLogo_extension(String logo_extension) {
        this.logo_extension = logo_extension;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String logo_content;
    public String logo_extension;
    public Integer parent_id;
    public String startDate;
    public String endDate;
}

