package com.example.rob.try3;

public class RecyclerViewItem {

    private String mEvevtPoster;
    private String mEventName;
    private String mEventDescription;
    private String mEventDate;


    public RecyclerViewItem(String evevtPoster, String eventName, String eventDate, String eventDescription){
        mEvevtPoster = evevtPoster;
        mEventName = eventName;
        mEventDate = eventDate;
        mEventDescription = eventDescription;
    }

    public String getEvevtPoster() {
        return mEvevtPoster;
    }

    public String getEventName() {
        return mEventName;
    }

    public String getEventDate(){ return mEventDate; }

    public String getEventDescription(){ return mEventDescription; }
}