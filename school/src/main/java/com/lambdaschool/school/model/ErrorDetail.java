package com.lambdaschool.school.model;

import com.lambdaschool.school.exceptions.ValidationError;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value="Error Detail", description = "Error Handling Message")
public class ErrorDetail
{
    @ApiModelProperty(name="Title", example = "Resource Not Found")
    private String title;
    @ApiModelProperty(name="Http Status", example="404")
    private int status;
    @ApiModelProperty(name="Details", example = "14")
    private String detail;
    @ApiModelProperty(name="TimeStamp", example = "20 Aug 2019 17:29:23 -0400")
    private String timestamp;
    @ApiModelProperty(name="Developer Message", example = "com.lambdaschool.school.exceptions.ResourceNotFoundException")
    private String developerMessage;
    private Map<String, List<ValidationError>> errors = new HashMap<>();

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(long timestamp)
    {
        this.timestamp = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z").format(new Date(timestamp));
    }

    public String getDeveloperMessage()
    {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage)
    {
        this.developerMessage = developerMessage;
    }

    public Map<String, List<ValidationError>> getErrors()
    {
        return errors;
    }

    public void setErrors(Map<String, List<ValidationError>> errors)
    {
        this.errors = errors;
    }
}