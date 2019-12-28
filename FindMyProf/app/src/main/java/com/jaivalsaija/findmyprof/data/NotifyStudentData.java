package com.jaivalsaija.findmyprof.data;

public class NotifyStudentData {
    private String id;
    private String studentName;
    private String timeStart;
    private String timeEnd;

    public NotifyStudentData(String id,String studentName, String timeStart, String timeEnd) {
        this.id = id;
        this.studentName = studentName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getstudentName() {
        return studentName;
    }

    public void setstudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}

