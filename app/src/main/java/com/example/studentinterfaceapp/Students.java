package com.example.studentinterfaceapp;

public class Students {


    private String name, program, classname, instructor, username, studentid, password;

    public Students(String studentid, String name, String program, String classname, String instructor, String username, String password) { this.studentid = studentid;
        this.name = name;
        this.program = program;
        this.classname = classname;
        this.instructor = instructor;
        this.username = username;
        this.password = password;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}

//    public Students()
//    {
//
//    }
//
//    public Students(int studentid, String name, String program, String classname, String instructor, String username, String password) {
//        this.studentid = studentid;
//        this.name = name;
//        this.program = program;
//        this.classname = classname;
//        this.instructor = instructor;
//        this.username = username;
//        this.password = password;
//    }
//
//    public int getStudentid() {
//        return studentid;
//    }
//
//    public void setStudentid(int studentid) {
//        this.studentid = studentid;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getProgram() {
//        return program;
//    }
//
//    public void setProgram(String program) {
//        this.program = program;
//    }
//
//    public String getClassname() {
//        return classname;
//    }
//
//    public void setClassname(String classname) {
//        this.classname = classname;
//    }
//
//    public String getInstructor() {
//        return instructor;
//    }
//
//    public void setInstructor(String instructor) {
//        this.instructor = instructor;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//}
