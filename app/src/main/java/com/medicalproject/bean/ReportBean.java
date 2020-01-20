package com.medicalproject.bean;

/**
 * 报告单信息实体类
 * @author 汪文博
 */
public class ReportBean {

    private String userName;
    private String userSex;
    private String userAge;
    private String office;
    private String doctor;
    private String diagnoseRsult;
    private String medicine;

    public ReportBean(String userName, String userSex, String userAge, String office, String doctor,
                      String diagnoseRsult, String medicine){
        this.userName = userName;
        this.userSex = userSex;
        this.userAge = userAge;
        this.office = office;
        this.doctor = doctor;
        this.diagnoseRsult = diagnoseRsult;
        this.medicine = medicine;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserSex(){
        return userSex;
    }

    public void setUserSex(String userSex){
        this.userSex = userSex;
    }

    public String getUserAge(){
        return userAge;
    }

    public void setUserAge(String userAge){
        this.userAge = userAge;
    }

    public String getOffice(){
        return office;
    }

    public void setOffice(String office){
        this.office = office;
    }

    public String getDoctor(){
        return doctor;
    }

    public void setDoctor(String doctor){
        this.doctor = doctor;
    }

    public String getDiagnoseRsult(){
        return diagnoseRsult;
    }

    public void setDiagnoseRsult(String diagnoseRsult){
        this.diagnoseRsult = diagnoseRsult;
    }

    public String getMedicine(){
        return medicine;
    }

    public void setMedicine(String medicine){
        this.medicine = medicine;
    }
}
