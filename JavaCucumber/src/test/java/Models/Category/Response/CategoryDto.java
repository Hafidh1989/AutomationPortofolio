package Models.Category.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CategoryDto {
    public int status;
    public String message;
    public Data data;
    public Date timestamp;
    public String version;
    public String trace_id;
}
