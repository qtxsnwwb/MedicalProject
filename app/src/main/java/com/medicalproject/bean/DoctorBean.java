package com.medicalproject.bean;


/**
 * 医生信息实体类
 * @author 汪文博
 */
public class DoctorBean {

    private String doctorName;
    private String doctorIntro;

    public DoctorBean(String doctorName, String doctorIntro){
        this.doctorName = doctorName;
        this.doctorIntro = doctorIntro;
    }

    public String getDoctorName(){
        return doctorName;
    }

    public void setDoctorName(String doctorName){
        this.doctorName = doctorName;
    }

    public String getDoctorIntro(){
        return doctorIntro;
    }

    public void setDoctorIntro(String doctorIntro){
        this.doctorIntro = doctorIntro;
    }
}
