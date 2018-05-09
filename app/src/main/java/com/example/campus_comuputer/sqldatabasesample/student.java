package com.example.campus_comuputer.sqldatabasesample;

import java.io.Serializable;

/**
 * Created by cmpe_mac_1 on 08/05/18.
 */

public class student implements Serializable {
    private String studentNumber;
    private String studentGrade;

    public student(String studentNumber, String studentGrade) {
        this.studentNumber = studentNumber;
        this.studentGrade = studentGrade;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade;
    }
}
