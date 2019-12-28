package com.jaivalsaija.findmyprof.data;

public class NotifyProfessorData {

    private String id;
    private String studentId;
    private String timeStart;
    private String timeEnd;

    public NotifyProfessorData(String id, String studentId, String timeStart, String timeEnd) {
        this.id = id;
        this.studentId = studentId;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getstudentId() {
        return studentId;
    }

    public void setstudentId(String studentName) {
        this.studentId = studentName;
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

