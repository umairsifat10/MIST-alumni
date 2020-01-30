package com.example.imtiaz.mist;

public class student {
    String sname, sid, scontact, sprofession, sdesignation, slocation,semail;

    public student() {
    }

    public student(String sname, String sid, String scontact, String sprofession, String sdesignation, String slocation, String semail) {
        this.sname = sname;
        this.sid = sid;
        this.scontact = scontact;
        this.sprofession = sprofession;
        this.sdesignation = sdesignation;
        this.slocation = slocation;
        this.semail = semail;
    }

    public String getSname() {
        return sname;
    }

    public String getSid() {
        return sid;
    }

    public String getScontact() {
        return scontact;
    }

    public String getSprofession() {
        return sprofession;
    }

    public String getSdesignation() {
        return sdesignation;
    }

    public String getSlocation() {
        return slocation;
    }

    public String getSemail(){ return semail; }
}
