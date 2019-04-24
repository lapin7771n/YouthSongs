package com.nlapin.youthsongs.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "song_covers")
public class PixelsResponseModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("page")
    @Expose
    private String page;

    @SerializedName("per_page")
    @Expose
    private String per_page;

    @SerializedName("total_results")
    @Expose
    private String total_results;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("next_page")
    @Expose
    private String nextPage;

    @SerializedName("photos")
    @Expose
    private Photos[] photos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public Photos getPhotos() {
        return photos[0];
    }

    public void setPhotos(Photos[] photos) {
        this.photos = photos;
    }

    public class Photos {

        @SerializedName("width")
        @Expose
        private int width;

        @SerializedName("height")
        @Expose
        private int height;

        @SerializedName("url")
        @Expose
        private String url;

        @SerializedName("photographer")
        @Expose
        private String photographer;

        @SerializedName("src")
        @Expose
        private PhotoImageUrls src;

        public PhotoImageUrls getSrc() {
            return src;
        }

        public void setSrc(PhotoImageUrls src) {
            this.src = src;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPhotographer() {
            return photographer;
        }

        public void setPhotographer(String photographer) {
            this.photographer = photographer;
        }


        public class PhotoImageUrls {
            @SerializedName("original")
            @Expose
            private String original;

            @SerializedName("large")
            @Expose
            private String large;

            @SerializedName("large2x")
            @Expose
            private String large2x;

            @SerializedName("medium")
            @Expose
            private String medium;

            @SerializedName("small")
            @Expose
            private String small;

            @SerializedName("portrait")
            @Expose
            private String portrait;

            @SerializedName("landscape")
            @Expose
            private String landscape;

            @SerializedName("tiny")
            @Expose
            private String tiny;

            public String getOriginal() {
                return original;
            }

            public void setOriginal(String original) {
                this.original = original;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getLarge2x() {
                return large2x;
            }

            public void setLarge2x(String large2x) {
                this.large2x = large2x;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public String getLandscape() {
                return landscape;
            }

            public void setLandscape(String landscape) {
                this.landscape = landscape;
            }

            public String getTiny() {
                return tiny;
            }

            public void setTiny(String tiny) {
                this.tiny = tiny;
            }
        }
    }
}
