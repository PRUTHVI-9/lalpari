package com.example.lalpari;

public class listitem {
    String Source;
    String Destination;
    String Date;
    String Bustype;
    String Time;


    public listitem(String source, String destination, String date, String bustype, String time) {
        Source = source;
        Destination = destination;
        Date = date;
        Bustype = bustype;
        Time = time;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBustype() {
        return Bustype;
    }

    public void setBustype(String bustype) {
        Bustype = bustype;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
