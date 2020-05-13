package entities;

import java.util.List;

/**
 * Created by Syed Asher Ahmed on 4/18/2020.
 */
public class Admin {
    private Integer adminId;
    private String adminName;
    private String adminEmail;
    private String adminPassword;
    private String adminContactNo;
    private String adminAddress;
    private List<Courses> courses;
    private List<Modules> modules;

    public Admin(){}
    public Admin(Integer adminId, String adminName, String adminEmail, String adminPassword, String adminContactNo, String adminAddress, List<Courses> courses, List<Modules> modules) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.adminContactNo = adminContactNo;
        this.adminAddress = adminAddress;
        this.courses = courses;
        this.modules = modules;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminContactNo() {
        return adminContactNo;
    }

    public void setAdminContactNo(String adminContactNo) {
        this.adminContactNo = adminContactNo;
    }


    public String getAdminAddress() {
        return adminAddress;
    }

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }

    public List<Modules> getModules() {
        return modules;
    }

    public void setModules(List<Modules> modules) {
        this.modules = modules;
    }
}
