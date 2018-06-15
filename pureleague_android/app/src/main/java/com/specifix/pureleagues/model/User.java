package com.specifix.pureleagues.model;

import android.net.Uri;

import java.util.List;

public class User {
    private String object_id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String dateOfBirth;
    private String gender;
    private String type;
    private String height;
    private String weight;
    private String position;
    private String profile;
    private String photoUrl;
    private byte[] photoByteArray;
    private List<Team> teams;

    public User(String object_id, String name, String username, String password, String email, String dateOfBirth, String gender, String type) {
        this.object_id = object_id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.type = type;
        this.height = "";
        this.weight = "";
        this.position = "";
        this.profile = "";
    }

    public String getName() {
        return name;
    }

    public String getObjectId() {
        return object_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photo_url) {
        this.photoUrl = photo_url;
    }

    public byte[] getPhotoByteArray() {
        return photoByteArray;
    }

    public void setPhotoByteArray(byte[] photoByteArray) {
        this.photoByteArray = photoByteArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!name.equals(user.name)) return false;
        if (!username.equals(user.username)) return false;
        if (!password.equals(user.password)) return false;
        if (!email.equals(user.email)) return false;
        if (!dateOfBirth.equals(user.dateOfBirth)) return false;
        if (!gender.equals(user.gender)) return false;
        return type.equals(user.type);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + dateOfBirth.hashCode();
        result = 31 * result + gender.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "object_id='" + object_id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", type='" + type + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", position='" + position + '\'' +
                ", profile='" + profile + '\'' +
                ", photo_url='" + photoUrl + '\'' +
                ", teams=" + teams +
                '}';
    }
}
