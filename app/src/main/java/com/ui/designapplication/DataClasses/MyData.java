
package com.ui.designapplication.DataClasses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("streetAddress")
    @Expose
    private Object streetAddress;
    @SerializedName("streetAddress2")
    @Expose
    private Object streetAddress2;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("zipCode")
    @Expose
    private Object zipCode;
    @SerializedName("directions")
    @Expose
    private String directions;
    @SerializedName("type")
    @Expose
    private Object type;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("hoursOfOperation")
    @Expose
    private Object hoursOfOperation;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("stayLimit")
    @Expose
    private Object stayLimit;
    @SerializedName("acres")
    @Expose
    private Object acres;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("tagCount")
    @Expose
    private Integer tagCount;
    @SerializedName("contentImage")
    @Expose
    private Object contentImage;
    @SerializedName("file")
    @Expose
    private Object file;
    @SerializedName("fileString")
    @Expose
    private Object fileString;
    @SerializedName("lastUpdated")
    @Expose
    private Object lastUpdated;
    @SerializedName("origin")
    @Expose
    private Object origin;
    @SerializedName("approved")
    @Expose
    private Boolean approved;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("likeCount")
    @Expose
    private Integer likeCount;
    @SerializedName("imageUrl")
    @Expose
    private Object imageUrl;
    @SerializedName("weatherForecast")
    @Expose
    private List<WeatherForecast> weatherForecast = null;
    @SerializedName("reviews")
    @Expose
    private List<Object> reviews = null;
    @SerializedName("photos")
    @Expose
    private List<Object> photos = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(Object streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Object getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(Object streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getZipCode() {
        return zipCode;
    }

    public void setZipCode(Object zipCode) {
        this.zipCode = zipCode;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getHoursOfOperation() {
        return hoursOfOperation;
    }

    public void setHoursOfOperation(Object hoursOfOperation) {
        this.hoursOfOperation = hoursOfOperation;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Object getStayLimit() {
        return stayLimit;
    }

    public void setStayLimit(Object stayLimit) {
        this.stayLimit = stayLimit;
    }

    public Object getAcres() {
        return acres;
    }

    public void setAcres(Object acres) {
        this.acres = acres;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Integer getTagCount() {
        return tagCount;
    }

    public void setTagCount(Integer tagCount) {
        this.tagCount = tagCount;
    }

    public Object getContentImage() {
        return contentImage;
    }

    public void setContentImage(Object contentImage) {
        this.contentImage = contentImage;
    }

    public Object getFile() {
        return file;
    }

    public void setFile(Object file) {
        this.file = file;
    }

    public Object getFileString() {
        return fileString;
    }

    public void setFileString(Object fileString) {
        this.fileString = fileString;
    }

    public Object getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Object lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Object getOrigin() {
        return origin;
    }

    public void setOrigin(Object origin) {
        this.origin = origin;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<WeatherForecast> getWeatherForecast() {
        return weatherForecast;
    }

    public void setWeatherForecast(List<WeatherForecast> weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    public List<Object> getReviews() {
        return reviews;
    }

    public void setReviews(List<Object> reviews) {
        this.reviews = reviews;
    }

    public List<Object> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Object> photos) {
        this.photos = photos;
    }

}
