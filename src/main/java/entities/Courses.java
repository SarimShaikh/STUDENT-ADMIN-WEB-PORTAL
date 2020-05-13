package entities;

import java.util.List;

/**
 * Created by Syed Asher Ahmed on 4/18/2020.
 */
public class Courses {

    private Integer courseId;
    private String courseName;
    private String courseDescription;
    private Integer courseLimit;
    private Byte isActive;
    private Integer adminId;
    private Student student;
    private Admin admin;
    private List<Modules> modules;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Integer getCourseLimit() {
        return courseLimit;
    }

    public void setCourseLimit(Integer courseLimit) {
        this.courseLimit = courseLimit;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Modules> getModules() {
        return modules;
    }

    public void setModules(List<Modules> modules) {
        this.modules = modules;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
