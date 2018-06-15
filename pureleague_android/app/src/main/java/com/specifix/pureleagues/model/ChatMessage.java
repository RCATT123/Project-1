package com.specifix.pureleagues.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.specifix.pureleagues.api.UserManager;

public class ChatMessage implements Parcelable {
    private String objectId;
    private String author;
    private long parentId;
    private String authorId;
    private String message;
    private String imageUrl;
    private String localImageUrl;
    private long date;
    private long localDate;
    private long teamId;
    private boolean isRead;
    private boolean isLocal;
    private boolean isHidden = false;

    public ChatMessage(String objectId, String author, String authorId, long parentId, String message, String imageUrl, long date, long localDate, long teamId, boolean isRead) {
        this.objectId = objectId;
        this.author = author;
        this.authorId = authorId;
        this.parentId = parentId;
        this.message = message;
        this.imageUrl = imageUrl;
        this.date = date;
        this.localDate = localDate;
        this.teamId = teamId;
        this.isRead = isRead;
        this.isLocal = false;
    }

    public ChatMessage(String message, String imageUrl, long localDate) {
        User currentUser = UserManager.getInstance().getCurrentUser();
        this.author = currentUser.getUsername();
        this.authorId = currentUser.getObjectId();
        this.message = message;
        this.localImageUrl = imageUrl;
        this.localDate = localDate;
        this.date = localDate;
        this.isRead = false;
        this.isLocal = true;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public String getLocalImageUrl() {
        return localImageUrl;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "author='" + author + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", isRead=" + isRead +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.authorId);
        dest.writeString(this.message);
        dest.writeString(this.imageUrl);
        dest.writeString(this.localImageUrl);
        dest.writeLong(this.date);
        dest.writeByte(this.isRead ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isLocal ? (byte) 1 : (byte) 0);
    }

    protected ChatMessage(Parcel in) {
        this.author = in.readString();
        this.authorId = in.readString();
        this.message = in.readString();
        this.imageUrl = in.readString();
        this.localImageUrl = in.readParcelable(Uri.class.getClassLoader());
        this.date = in.readLong();
        this.isRead = in.readByte() != 0;
        this.isLocal = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ChatMessage> CREATOR = new Parcelable.Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel source) {
            return new ChatMessage(source);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };

    public long getLocalDate() {
        return localDate;
    }

    public void setLocalDate(long localDate) {
        this.localDate = localDate;
    }
}
