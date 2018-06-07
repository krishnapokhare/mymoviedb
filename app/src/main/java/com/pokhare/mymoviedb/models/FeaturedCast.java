package com.pokhare.mymoviedb.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.pokhare.mymoviedb.helpers.Convert;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class FeaturedCast implements Parcelable {
    private String name;
    private String character;
    private String imageUrl;
    public static final Creator<FeaturedCast> CREATOR = new Parcelable.Creator<FeaturedCast>() {

        @Override
        public FeaturedCast createFromParcel(Parcel source) {
            return new FeaturedCast(source);
        }

        @Override
        public FeaturedCast[] newArray(int size) {
            return new FeaturedCast[0];
        }
    };
    private Date birthDay;
    private int id;
    private String alsoKnownAs;
    private int gender;
    private String biography;
    private Double popularity;
    private String birthPlace;
    private boolean isAdult;
    private String imdb_id;
    private String homepage;

    public FeaturedCast() {

    }

    public FeaturedCast(int id, String name, String character, String imageUrl) {
        this.id = id;
        this.name = name;
        this.character = character;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public FeaturedCast(Parcel in) {
        name = in.readString();
        character = in.readString();
        imageUrl = in.readString();
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(String alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public String toString() {
        return "FeaturedCast{" +
                "name='" + name + '\'' +
                ", character='" + character + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", birthDay=" + birthDay +
                ", id=" + id +
                ", alsoKnownAs='" + alsoKnownAs + '\'' +
                ", gender=" + gender +
                ", biography='" + biography + '\'' +
                ", popularity=" + popularity +
                ", birthPlace='" + birthPlace + '\'' +
                ", isAdult=" + isAdult +
                ", imdb_id='" + imdb_id + '\'' +
                ", homepage='" + homepage + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(character);
        dest.writeString(imageUrl);
    }

    public static class Factory {
        public static FeaturedCast NewFeaturedCast(JSONObject jsonObject) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            FeaturedCast featuredCast = new FeaturedCast();
            Map<String, Object> map = Convert.fromJson(jsonObject);
            try {
                featuredCast.setName(jsonObject.getString("name"));
                featuredCast.setImageUrl(jsonObject.getString("profile_path"));
                featuredCast.setId(jsonObject.getInt("id"));
                featuredCast.setPopularity(jsonObject.getDouble("popularity"));
                featuredCast.setAdult(jsonObject.getBoolean("adult"));
                featuredCast.setAlsoKnownAs(jsonObject.getString("also_known_as"));
                featuredCast.setBiography(jsonObject.getString("biography"));
                featuredCast.setGender(jsonObject.getInt("gender"));
                featuredCast.setHomepage(jsonObject.getString("homepage"));
                featuredCast.setImdb_id(jsonObject.getString("imdb_id"));
                featuredCast.setBirthPlace(jsonObject.getString("place_of_birth"));
                try {
                    featuredCast.setBirthDay(format.parse(jsonObject.getString("birthday")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.i("Featured Cast", featuredCast.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return featuredCast;
        }
    }
}
