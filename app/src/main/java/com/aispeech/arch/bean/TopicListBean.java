package com.aispeech.arch.bean;

import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.JsonClass;

import java.util.List;
@JsonClass(generateAdapter = true)
public class TopicListBean {
    @SerializedName("code")
    private Integer code;
    @SerializedName("success")
    private Boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private ResultDTO result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public static class ResultDTO {
        @SerializedName("showSubTitle")
        private Boolean showSubTitle;
        @SerializedName("styleType")
        private Integer styleType;
        @SerializedName("contentList")
        private List<ContentListDTO> contentList;
        @SerializedName("recommendClaim")
        private String recommendClaim;

        public Boolean getShowSubTitle() {
            return showSubTitle;
        }

        public void setShowSubTitle(Boolean showSubTitle) {
            this.showSubTitle = showSubTitle;
        }

        public Integer getStyleType() {
            return styleType;
        }

        public void setStyleType(Integer styleType) {
            this.styleType = styleType;
        }

        public List<ContentListDTO> getContentList() {
            return contentList;
        }

        public void setContentList(List<ContentListDTO> contentList) {
            this.contentList = contentList;
        }

        public String getRecommendClaim() {
            return recommendClaim;
        }

        public void setRecommendClaim(String recommendClaim) {
            this.recommendClaim = recommendClaim;
        }

        public static class ContentListDTO {
            @SerializedName("index")
            private Integer index;
            @SerializedName("title")
            private String title;
            @SerializedName("subTitle")
            private Object subTitle;
            @SerializedName("imageUrl")
            private String imageUrl;
            @SerializedName("type")
            private String type;

            public Integer getIndex() {
                return index;
            }

            public void setIndex(Integer index) {
                this.index = index;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(Object subTitle) {
                this.subTitle = subTitle;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
