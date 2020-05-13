package entities;

import java.util.List;

/**
 * Created by Sarim on 4/17/2020.
 */
public class Student {
    private Integer studentId;
    private String studentFirstName;
    private String studentLastName;
    private String studentContactNo;
    private String studentEmail;
    private String studentPassword;
    private String studentAddress;
    private String paymentStatus;
    private Courses courses;
    private List<Modules> modules;

    public Student() {
    }

    public Student(Integer studentId, String studentFirstName, String studentLastName, String studentContactNo, String studentEmail, String studentPassword, String studentAddress, String paymentStatus, Courses courses, List<Modules> modules) {
        this.studentId = studentId;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.studentContactNo = studentContactNo;
        this.studentEmail = studentEmail;
        this.studentPassword = studentPassword;
        this.studentAddress = studentAddress;
        this.paymentStatus = paymentStatus;
        this.courses = courses;
        this.modules = modules;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
    }

    public String getStudentContactNo() {
        return studentContactNo;
    }

    public void setStudentContactNo(String studentContactNo) {
        this.studentContactNo = studentContactNo;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String srudentPassword) {
        this.studentPassword = srudentPassword;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    public List<Modules> getModules() {
        return modules;
    }

    public void setModules(List<Modules> modules) {
        this.modules = modules;
    }
}
