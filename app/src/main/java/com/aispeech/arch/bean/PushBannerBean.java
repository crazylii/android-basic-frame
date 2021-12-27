package com.aispeech.arch.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PushBannerBean {
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private List<BannerBean> result;
    @SerializedName("success")
    private Boolean success;
    @SerializedName("type")
    private Integer type;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BannerBean> getResult() {
        return result;
    }

    public void setResult(List<BannerBean> result) {
        this.result = result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PushBannerBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                ", success=" + success +
                ", type=" + type +
                '}';
    }
}
