package com.nlapin.youthsongs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnsplashPhoto {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("alt_description")
    @Expose
    private String alt_description;

    @SerializedName("urls")
    @Expose
    private Url urls;


    public class Url {

        @SerializedName("raw")
        @Expose
        private String raw;

        @SerializedName("full")
        @Expose
        private String full;

        @SerializedName("regular")
        @Expose
        private String regular;

        @SerializedName("small")
        @Expose
        private String small;

        @SerializedName("thumb")
        @Expose
        private String thumb;


        @Override
        public String toString() {
            return "Url{" +
                    "raw='" + raw + '\'' +
                    ", full='" + full + '\'' +
                    ", regular='" + regular + '\'' +
                    ", small='" + small + '\'' +
                    ", thumb='" + thumb + '\'' +
                    '}';
        }

        public String getRaw() {
            return raw;
        }

        public String getFull() {
            return full;
        }

        public String getRegular() {
            return regular;
        }

        public String getSmall() {
            return small;
        }

        public String getThumb() {
            return thumb;
        }
    }

    @Override
    public String toString() {
        return "UnsplashPhoto{" +
                "id='" + id + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", alt_description='" + alt_description + '\'' +
                ", urls=" + urls.toString() +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String getAlt_description() {
        return alt_description;
    }

    public Url getUrls() {
        return urls;
    }
}
