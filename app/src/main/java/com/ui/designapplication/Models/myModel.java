package com.ui.designapplication.Models;

import java.util.List;

public class myModel {
    String id, name, description, streetAddress, streetAddress2, state, city, zipCode, directions;
    String type, phoneNumber, hoursOfOperation, website, email, longitude, latitude, stayLimit;
    String acres, tags,tagCount, contentImage, file, fileString, lastUpdated, origin, approved;
    String status, likeCount, imageUrl, weatherForecast, reviews, photos;
    List<tags> tagsList;
    List<weatherForecast> weatherForecastList;


    static class tags
    {
        String biking, walking, hiking;

    }
    static class weatherForecast
    {
        String dt, sunrise, sunset, pressure, humidity, dewPoint, windSpeed, windDeg;
        String clouds, pop, uvi, rain, snow;
        List<temp> tempList;
        List<feelsLike> feelsLikeList;
        List<weather> weatherList;

        static class temp{
            String day, min, max, night, eve, morn;
        }
        static class feelsLike
        {
            String day, night, eve, morn;
        }
        static class weather
        {
            String id, main, description, icon;
        }
    }

    public myModel() {
    }

    public myModel(String id, String name, String description, String streetAddress, String streetAddress2, String state, String city, String zipCode, String directions, String type, String phoneNumber, String hoursOfOperation, String website, String email, String longitude, String latitude, String stayLimit, String acres, String tags, String tagCount, String contentImage, String file, String fileString, String lastUpdated, String origin, String approved, String status, String likeCount, String imageUrl, String weatherForecast, String reviews, String photos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.streetAddress = streetAddress;
        this.streetAddress2 = streetAddress2;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.directions = directions;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.hoursOfOperation = hoursOfOperation;
        this.website = website;
        this.email = email;
        this.longitude = longitude;
        this.latitude = latitude;
        this.stayLimit = stayLimit;
        this.acres = acres;
        this.tags = tags;
        this.tagCount = tagCount;
        this.contentImage = contentImage;
        this.file = file;
        this.fileString = fileString;
        this.lastUpdated = lastUpdated;
        this.origin = origin;
        this.approved = approved;
        this.status = status;
        this.likeCount = likeCount;
        this.imageUrl = imageUrl;
        this.weatherForecast = weatherForecast;
        this.reviews = reviews;
        this.photos = photos;
        this.tagsList = tagsList;
        this.weatherForecastList = weatherForecastList;
    }

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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHoursOfOperation() {
        return hoursOfOperation;
    }

    public void setHoursOfOperation(String hoursOfOperation) {
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStayLimit() {
        return stayLimit;
    }

    public void setStayLimit(String stayLimit) {
        this.stayLimit = stayLimit;
    }

    public String getAcres() {
        return acres;
    }

    public void setAcres(String acres) {
        this.acres = acres;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTagCount() {
        return tagCount;
    }

    public void setTagCount(String tagCount) {
        this.tagCount = tagCount;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileString() {
        return fileString;
    }

    public void setFileString(String fileString) {
        this.fileString = fileString;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWeatherForecast() {
        return weatherForecast;
    }

    public void setWeatherForecast(String weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public List<myModel.tags> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<myModel.tags> tagsList) {
        this.tagsList = tagsList;
    }

    public List<myModel.weatherForecast> getWeatherForecastList() {
        return weatherForecastList;
    }

    public void setWeatherForecastList(List<myModel.weatherForecast> weatherForecastList) {
        this.weatherForecastList = weatherForecastList;
    }
}
