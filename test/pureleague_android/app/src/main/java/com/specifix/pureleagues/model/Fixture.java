package com.specifix.pureleagues.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Fixture implements Parcelable {
    private String id;
    private long match_id;
    private long division_id;
    private String address;
    private long clubOneId;
    private long clubTwoId;
    private String clubOneName;
    private String clubTwoName;
    private String date;
    private Calendar calendar;
    //private String time;
    private LatLng location;
    private String weather;
    private String weatherUrl;
    private List<ChatMessage> messages;

    public Fixture(String id, long match_id, long division_id, String address, long clubOneId, long clubTwoId, String clubOneName, String clubTwoName, String date) {
        this.id = id;
        this.match_id = match_id;
        this.division_id = division_id;
        this.address = address;
        this.clubOneId = clubOneId;
        this.clubTwoId = clubTwoId;
        this.clubOneName = clubOneName;
        this.clubTwoName = clubTwoName;
        this.date = date;
        this.messages = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getMatch_id() {
        return match_id;
    }

    public void setMatch_id(long match_id) {
        this.match_id = match_id;
    }

    public long getDivision_id() {
        return division_id;
    }

    public void setDivision_id(long division_id) {
        this.division_id = division_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getClubOneId() {
        return clubOneId;
    }

    public void setClubOneId(long clubOneId) {
        this.clubOneId = clubOneId;
    }

    public long getClubTwoId() {
        return clubTwoId;
    }

    public void setClubTwoId(long clubTwoId) {
        this.clubTwoId = clubTwoId;
    }

    public String getClubOneName() {
        return clubOneName;
    }

    public void setClubOneName(String clubOneName) {
        this.clubOneName = clubOneName;
    }

    public String getClubTwoName() {
        return clubTwoName;
    }

    public void setClubTwoName(String clubTwoName) {
        this.clubTwoName = clubTwoName;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeatherUrl() {
        return weatherUrl;
    }

    public void setWeatherUrl(String weatherUrl) {
        this.weatherUrl = weatherUrl;
    }

    public String getDate() {
        if (date == null) {
            date = "";
        }
//        if (!date.contains("T")) return date;

        return date.replace('-', '.');
//        return date.substring(0, date.indexOf("T")).replace('-', '.');
    }

    public String getFormatDate() {
        Calendar date = getCalendar();
        if (date == null)
            return "";

        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
        return format1.format(date.getTime());
    }

    public String getFullDate() {
        return date;
    }

    public String getTime() {
        if (date == null) {
            date = "";
        }
//        if (!date.contains("T")) return date;

        int separatorIndex = date.indexOf(" ");
        if (separatorIndex + 1 + 5 > date.length()) {
            return date;
        }

        return date.substring(separatorIndex + 1, separatorIndex + 1 + 5);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
        Collections.sort(messages, new Comparator<ChatMessage>() {
            @Override
            public int compare(ChatMessage o1, ChatMessage o2) {
                if (o1.getDate() < o2.getDate())
                    return 1;
                if (o1.getDate() > o2.getDate())
                    return -1;
                return 0;
            }
        });
    }

    public void addMessage(ChatMessage newMessage) {
        if (messages == null)
            messages = new ArrayList<>();

        boolean alreadyReceived = false;
        for (int i = 0; i < messages.size(); i++) {
            ChatMessage item = messages.get(i);
            if (item.getLocalDate() == newMessage.getLocalDate()
                    && item.getAuthorId().equals(newMessage.getAuthorId())){
                item.setLocal(false);
                alreadyReceived = true;
            }
        }

        if (!alreadyReceived) {
            messages.add(newMessage);
        }

        setMessages(messages);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fixture fixture = (Fixture) o;

        return id != null ? id.equals(fixture.id) : fixture.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


    public Calendar getCalendar() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.SATURDAY);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

            try {
                calendar.setTime(sdf.parse(getDate()));
            } catch (ParseException e) {
                calendar = null;
            }
        }
        return calendar;
    }

    @Override
    public String toString() {
        return "Fixture{" +
                "id='" + id + '\'' +
                ", match_id=" + match_id +
                ", division_id=" + division_id +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeLong(this.match_id);
        dest.writeLong(this.division_id);
        dest.writeString(this.address);
        dest.writeLong(this.clubOneId);
        dest.writeLong(this.clubTwoId);
        dest.writeString(this.clubOneName);
        dest.writeString(this.clubTwoName);
        dest.writeString(this.date);
        dest.writeSerializable(this.calendar);
        dest.writeParcelable(this.location, flags);
        dest.writeString(this.weather);
        dest.writeString(this.weatherUrl);
        dest.writeTypedList(this.messages);
    }

    protected Fixture(Parcel in) {
        this.id = in.readString();
        this.match_id = in.readLong();
        this.division_id = in.readLong();
        this.address = in.readString();
        this.clubOneId = in.readLong();
        this.clubTwoId = in.readLong();
        this.clubOneName = in.readString();
        this.clubTwoName = in.readString();
        this.date = in.readString();
        this.calendar = (Calendar) in.readSerializable();
        this.location = in.readParcelable(LatLng.class.getClassLoader());
        this.weather = in.readString();
        this.weatherUrl = in.readString();
        this.messages = in.createTypedArrayList(ChatMessage.CREATOR);
    }

    public static final Creator<Fixture> CREATOR = new Creator<Fixture>() {
        @Override
        public Fixture createFromParcel(Parcel source) {
            return new Fixture(source);
        }

        @Override
        public Fixture[] newArray(int size) {
            return new Fixture[size];
        }
    };
}
