package com.specifix.pureleagues.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PushMessage implements Parcelable {
    private String teamId;
    private String title;
    private String message;
    private String eventType;
    private String listPosition;
    private String senderId;

    public PushMessage(String teamId, String title, String message, String eventType, String listPosition, String senderId) {
        this.teamId = teamId;
        this.title = title;
        this.message = message;
        this.eventType = eventType;
        this.listPosition = listPosition;
        this.senderId = senderId;
    }

    public PushMessage(String teamId, String title, String message, String eventType, String listPosition) {
        this.teamId = teamId;
        this.title = title;
        this.message = message;
        this.eventType = eventType;
        this.listPosition = listPosition;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getListPosition() {
        return listPosition;
    }

    public void setListPosition(String listPosition) {
        this.listPosition = listPosition;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "PushMessage{" +
                "teamId='" + teamId + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", eventType='" + eventType + '\'' +
                ", listPosition='" + listPosition + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.teamId);
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeString(this.eventType);
        dest.writeString(this.listPosition);
        dest.writeString(this.senderId);
    }

    protected PushMessage(Parcel in) {
        this.teamId = in.readString();
        this.title = in.readString();
        this.message = in.readString();
        this.eventType = in.readString();
        this.listPosition = in.readString();
        this.senderId = in.readString();
    }

    public static final Parcelable.Creator<PushMessage> CREATOR = new Parcelable.Creator<PushMessage>() {
        @Override
        public PushMessage createFromParcel(Parcel source) {
            return new PushMessage(source);
        }

        @Override
        public PushMessage[] newArray(int size) {
            return new PushMessage[size];
        }
    };
}
