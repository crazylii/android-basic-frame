package com.aispeech.arch.bean;

import com.google.gson.annotations.SerializedName;

public class BannerBean {
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("isDelete")
    private Integer isDelete;
    @SerializedName("orderNum")
    private Integer orderNum;
    @SerializedName("shufflingId")
    private String shufflingId;
    @SerializedName("shufflingImageClaim")
    private String shufflingImageClaim;
    @SerializedName("shufflingImageUrl")
    private String shufflingImageUrl;
    @SerializedName("shufflingTopic")
    private String shufflingTopic;
    @SerializedName("shufflingImageName")
    private String shufflingImageName;

    private String shufflingLocalPath;

    public void setShufflingLocalPath(String shufflingLocalPath) {
        this.shufflingLocalPath = shufflingLocalPath;
    }

    public String getShufflingLocalPath() {
        return shufflingLocalPath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getShufflingId() {
        return shufflingId;
    }

    public void setShufflingId(String shufflingId) {
        this.shufflingId = shufflingId;
    }

    public String getShufflingImageClaim() {
        return shufflingImageClaim;
    }

    public void setShufflingImageClaim(String shufflingImageClaim) {
        this.shufflingImageClaim = shufflingImageClaim;
    }

    public String getShufflingImageUrl() {
        return shufflingImageUrl;
    }

    public void setShufflingImageUrl(String shufflingImageUrl) {
        this.shufflingImageUrl = shufflingImageUrl;
    }

    public String getShufflingTopic() {
        return shufflingTopic;
    }

    public void setShufflingTopic(String shufflingTopic) {
        this.shufflingTopic = shufflingTopic;
    }

    public String getShufflingImageName() {
        return shufflingImageName;
    }

    public void setShufflingImageName(String shufflingImageName) {
        this.shufflingImageName = shufflingImageName;
    }
}
