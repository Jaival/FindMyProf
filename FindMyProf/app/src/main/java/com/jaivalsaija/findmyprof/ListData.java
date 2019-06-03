package com.jaivalsaija.findmyprof;

public class ListData {
    private String profName;
    private String Desc;

    public ListData(String profName, String Desc) {
        this.profName = profName;
        this.Desc = Desc;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }
}

