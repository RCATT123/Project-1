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

    /* private String id;
    private long match_id;
    private long division_id;
    private String address;
    private long clubOneId;
    private long clubTwoId;
    private String clubOneName;
    private String clubTwoName;
    private String date;
    //private String time;
    private LatLng location;
    private String weather;
    private String weatherUrl;
    private List<ChatMessage> messages; */

    private String league_id;
    private String location;
    private String away_name;
    private String home_name;
    private String round;
    private String time;
    private String date;
    private Calendar calendar;

    public Fixture(String league_id, String location, String away_name, String home_name, String round, String time, String date) {
        this.league_id = league_id;
        this.location = location;
        this.away_name = away_name;
        this.home_name = home_name;
        this.round = round;
        this.time = time;
        this.date = date;
    }

    public String getLeague_id() {
        return league_id;
    }

    public void setLeague_id(String id) {
        this.league_id = id;
    }

    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location = location;
    }

    public String getAway_name() {
        return away_name;
    }

    public void setAway_name(String away_name) {
        this.away_name = away_name;
    }

    public String getHome_name() {
        return home_name;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
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
        /*if (date == null) {
            date = "";
        }*/
//        if (!date.contains("T")) return date;

        /*int separatorIndex = date.indexOf(" ");
        if (separatorIndex + 1 + 5 > date.length()) {
            return date;
        }*/

        //return date.substring(separatorIndex + 1, separatorIndex + 1 + 5);
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
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

    /*public List<ChatMessage> getMessages() {
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
    }*/

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fixture fixture = (Fixture) o;

        return league_id != null ? league_id.equals(fixture.league_id) : fixture.league_id == null;

    }

    @Override
    public int hashCode() {
        return league_id != null ? league_id.hashCode() : 0;
    }




    @Override
    public String toString() {
        return "Fixture{" +
                "id='" + league_id + '\'' +
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
    };*/

   @Override
   public int describeContents() {
       return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
       dest.writeString(this.league_id);
       dest.writeString(this.location);
       dest.writeString(this.away_name);
       dest.writeString(this.home_name);
       dest.writeString(this.round);
       dest.writeString(this.time);
       dest.writeString(this.date);
   }

    protected Fixture(Parcel in) {
        this.league_id = in.readString();
        this.location = in.readString();
        this.away_name = in.readString();
        this.home_name = in.readString();
        this.round = in.readString();
        this.time = in.readString();
        this.date = in.readString();
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
