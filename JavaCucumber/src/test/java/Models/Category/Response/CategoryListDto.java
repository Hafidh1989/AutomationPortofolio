package Models.Category.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class CategoryListDto {
    public int status;
    public String message;
    public ArrayList<Datum> data;
    public Date timestamp;
    public String version;
    public String trace_id;
}

