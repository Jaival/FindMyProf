package com.jaivalsaija.findmyprof.data;

public class HistoryData {
    private String studentName;
    private String professorName;
    private String timeStart;
    private String timeEnd;
    private String acceptReject;
    private String responseMessage;

    public HistoryData(String studentName, String timeStart, String timeEnd, String acceptReject, String responseMessage) {
        this.studentName = studentName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.acceptReject = acceptReject;
        this.responseMessage = responseMessage;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
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

    public String getAcceptReject() {
        return acceptReject;
    }

    public void setAcceptReject(String acceptReject) {
        this.acceptReject = acceptReject;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }


}
