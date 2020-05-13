package entities.dto;

import entities.Courses;
import entities.Modules;

import java.util.List;

/**
 * Created by Sarim on 4/24/2020.
 */
public class CourseModuleDto {

    private Integer studentId;
    private String isSelect;
    private Integer coursesId;
    private List<Modules> modules;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(String isSelect) {
        this.isSelect = isSelect;
    }

    public Integer getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(Integer coursesId) {
        this.coursesId = coursesId;
    }

    public List<Modules> getModules() {
        return modules;
    }

    public void setModules(List<Modules> modules) {
        this.modules = modules;
    }
}
