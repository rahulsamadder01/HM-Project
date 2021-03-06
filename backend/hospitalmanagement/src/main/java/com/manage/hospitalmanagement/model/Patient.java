package com.manage.hospitalmanagement.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;




@Entity
public class Patient{
    @Id
    private int pid;
    private String pname;
    private String visiteddoctor;
    private Date dateofvisit;

    public Patient() {
    }

    public Patient(int pid, String pname, String visiteddoctor, Date dateofvisit) {
        this.pid = pid;
        this.pname = pname;
        this.visiteddoctor = visiteddoctor;
        this.dateofvisit = dateofvisit;
    }

    public int getPid() {
        return this.pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return this.pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getVisiteddoctor() {
        return this.visiteddoctor;
    }

    public void setVisiteddoctor(String visiteddoctor) {
        this.visiteddoctor = visiteddoctor;
    }

    public Date getDateofvisit() {
        return this.dateofvisit;
    }

    public void setDateofvisit(Date dateofvisit) {
        this.dateofvisit = dateofvisit;
    }


    @Override
    public String toString() {
        return "{" +
            " pid='" + getPid() + "'" +
            ", pname='" + getPname() + "'" +
            ", visiteddoctor='" + getVisiteddoctor() + "'" +
            ", dateofvisit='" + getDateofvisit() + "'" +
            "}";
    }

}