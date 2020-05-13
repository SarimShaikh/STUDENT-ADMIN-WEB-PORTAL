package dao;

import com.sun.org.apache.xpath.internal.operations.Mod;
import entities.Admin;
import entities.Courses;
import entities.Modules;
import entities.Student;
import entities.dto.CourseModuleDto;
import entities.dto.UserLoginResponseDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sarim on 4/18/2020.
 */
public class DBManager {

    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement = null;


    public static UserLoginResponseDto studentLogIn(String username, String password) throws ClassNotFoundException, SQLException {
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        try {
            preparedStatement = DBConnection.getConnection().prepareStatement("select * from students where STUDENT_EMAIL =? and STUDENT_PASSWORD=?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("STUDENT_ID"));
                student.setStudentFirstName(resultSet.getString("STUDENT_FIRST_NAME"));
                student.setStudentLastName(resultSet.getString("STUDENT_LAST_NAME"));
                student.setStudentEmail(resultSet.getString("STUDENT_EMAIL"));
                student.setPaymentStatus(resultSet.getString("STUDENT_PAYMENT_STATUS"));
                userLoginResponseDto.setResponseCode("200");
                userLoginResponseDto.setStatus("Success");
                userLoginResponseDto.setMessage("Login Successfully!");
                userLoginResponseDto.setStudent(student);
            } else {
                userLoginResponseDto.setResponseCode("401");
                userLoginResponseDto.setStatus("Failure");
                userLoginResponseDto.setMessage("Incorrect User and Password");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userLoginResponseDto;
    }

    public static UserLoginResponseDto adminLogIn(String username, String password) throws ClassNotFoundException, SQLException {
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        try {
            preparedStatement = DBConnection.getConnection().prepareStatement("select * from admin where ADMIN_EMAIL =? and ADMIN_PASSWORD=?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Admin admin = new Admin();
                admin.setAdminId(resultSet.getInt("ADMIN_ID"));
                admin.setAdminName(resultSet.getString("ADMIN_NAME"));
                admin.setAdminEmail(resultSet.getString("ADMIN_EMAIL"));
                admin.setAdminContactNo(resultSet.getString("ADMIN_CONTACT"));
                userLoginResponseDto.setResponseCode("200");
                userLoginResponseDto.setStatus("Success");
                userLoginResponseDto.setMessage("Login Successfully!");
                userLoginResponseDto.setAdmin(admin);
            } else {
                userLoginResponseDto.setResponseCode("401");
                userLoginResponseDto.setStatus("Failure");
                userLoginResponseDto.setMessage("Incorrect User and Password");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userLoginResponseDto;
    }

    //insert data in students
    public static int saveStudents(Student student) {
        int row = 0;
        try {
            String query = "INSERT INTO students (" +
                    " STUDENT_FIRST_NAME," +
                    " STUDENT_LAST_NAME," +
                    " STUDENT_CONTACT," +
                    " STUDENT_EMAIL," +
                    " STUDENT_PASSWORD," +
                    " STUDENT_ADDRESS," +
                    " STUDENT_PAYMENT_STATUS)" +
                    " VALUES (?,?,?,?,?,?,?)";
            preparedStatement = DBConnection.getConnection().prepareStatement(query);

            preparedStatement.setString(1, student.getStudentFirstName());
            preparedStatement.setString(2, student.getStudentLastName());
            preparedStatement.setString(3, student.getStudentContactNo());
            preparedStatement.setString(4, student.getStudentEmail());
            preparedStatement.setString(5, student.getStudentPassword());
            preparedStatement.setString(6, student.getStudentAddress());
            preparedStatement.setString(7, student.getPaymentStatus());

            row = preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return row;
    }

    //insert data in admins
    public static int saveAdmins(Admin adminObj) {
        int row = 0;
        try {
            String query = "INSERT INTO admin (" +
                    " ADMIN_NAME," +
                    " ADMIN_EMAIL," +
                    " ADMIN_PASSWORD," +
                    " ADMIN_CONTACT," +
                    " ADMIN_ADDRESS)" +
                    " VALUES (?,?,?,?,?)";
            preparedStatement = DBConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, adminObj.getAdminName());
            preparedStatement.setString(2, adminObj.getAdminEmail());
            preparedStatement.setString(3, adminObj.getAdminPassword());
            preparedStatement.setString(4, adminObj.getAdminContactNo());
            preparedStatement.setString(5, adminObj.getAdminAddress());

            row = preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return row;
    }

    //insert data in courses
    public static int addCourse(Courses courses) {
        int row = 0;
        try {
            String query = "INSERT INTO courses (" +
                    " COURSE_NAME," +
                    " COURSE_DESC," +
                    " COURSE_LIMIT," +
                    " IS_ACTIVE," +
                    " ADMIN_ID)" +
                    " VALUES (?,?,?,?,?)";

            preparedStatement = DBConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, courses.getCourseName());
            preparedStatement.setString(2, courses.getCourseDescription());
            preparedStatement.setInt(3, courses.getCourseLimit());
            preparedStatement.setByte(4, (byte) 1);
            preparedStatement.setInt(5, courses.getAdminId());

            row = preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return row;
    }

    //Update Course
    public static int updateCourses(Courses courses) {
        int row = 0;
        try {
            String query = "UPDATE courses SET " +
                    " COURSE_NAME=?," +
                    " COURSE_DESC=?," +
                    " COURSE_LIMIT=? " +
                    "where COURSE_ID =? ";
            System.out.println("=======>" + query);
            preparedStatement = DBConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, courses.getCourseName());
            preparedStatement.setString(2, courses.getCourseDescription());
            preparedStatement.setInt(3, courses.getCourseLimit());
            preparedStatement.setInt(4, courses.getCourseId());
            row = preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return row;
    }

    //Delete Course
    public static int deleteCourse(int courseId) {
        int row = 0;
        try {
            String query = "UPDATE courses SET IS_ACTIVE = ?  where COURSE_ID = ? ";
            preparedStatement = DBConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, courseId);
            row = preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return row;
    }

    public static List<Courses> getAllCoursedByAdminID(String adminId) {
        List<Courses> courses = new ArrayList<>();
        try {
            preparedStatement = DBConnection.getConnection().prepareStatement("select * from courses where IS_ACTIVE=? AND ADMIN_ID=?");
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, Integer.parseInt(adminId));
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Courses courses1 = new Courses();
                courses1.setCourseId(resultSet.getInt("COURSE_ID"));
                courses1.setCourseName(resultSet.getString("COURSE_NAME"));
                courses1.setCourseDescription(resultSet.getString("COURSE_DESC"));
                courses1.setCourseLimit(resultSet.getInt("COURSE_LIMIT"));
                courses.add(courses1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return courses;
    }

    public static List<Courses> getAllCoursesForCombo(String adminId) {
        List<Courses> courses = new ArrayList<>();
        try {
            preparedStatement = DBConnection.getConnection().prepareStatement("select COURSE_ID , COURSE_NAME from courses where IS_ACTIVE=? AND ADMIN_ID=?");
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, Integer.parseInt(adminId));
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Courses courses1 = new Courses();
                courses1.setCourseId(resultSet.getInt("COURSE_ID"));
                courses1.setCourseName(resultSet.getString("COURSE_NAME"));
                courses.add(courses1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return courses;
    }

    //insert data in modules
    public static int addModules(Modules modules) {
        int row = 0;
        try {
            String query = "INSERT INTO modules (" +
                    " COURSE_ID," +
                    " ADMIN_ID," +
                    " MODULE_NAME," +
                    " IS_MENDATORY," +
                    " IS_ACTIVE " +
                    " VALUES (?,?,?,?,?)";

            preparedStatement = DBConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, modules.getCourseId());
            preparedStatement.setInt(2, modules.getAdminId());
            preparedStatement.setString(3, modules.getModuleName());
            preparedStatement.setString(4, modules.getIsMandatory());
            preparedStatement.setByte(5, (byte) 1);

            row = preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return row;
    }

    //Update modules
    public static int updateModules(Modules modules) {
        int row = 0;
        try {
            String query = "UPDATE modules SET " +
                    " COURSE_ID=?," +
                    " ADMIN_ID=?," +
                    " MODULE_NAME=?," +
                    " IS_MENDATORY=? " +
                    "where MODULE_ID =? ";
            System.out.println("=======>" + query);
            preparedStatement = DBConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, modules.getCourseId());
            preparedStatement.setInt(2, modules.getAdminId());
            preparedStatement.setString(3, modules.getModuleName());
            preparedStatement.setString(4, modules.getIsMandatory());
            preparedStatement.setInt(5, modules.getModuleId());
            row = preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return row;
    }

    //Delete module
    public static int deleteModule(int moduleId) {
        int row = 0;
        try {
            String query = "UPDATE modules SET IS_ACTIVE = ?  where MODULE_ID = ? ";
            preparedStatement = DBConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, moduleId);
            row = preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return row;
    }

    //get all courses with modules for students
    public static List<Courses> getAllCoursesWithModules() {
        List<Courses> courses = new ArrayList<>();
        List<Courses> coursesList = new ArrayList<>();
        try {
            preparedStatement = DBConnection.getConnection().prepareStatement("select COURSE_ID , COURSE_NAME , COURSE_LIMIT from courses where IS_ACTIVE=?");
            preparedStatement.setInt(1, 1);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Courses courses1 = new Courses();
                courses1.setCourseId(resultSet.getInt("COURSE_ID"));
                courses1.setCourseName(resultSet.getString("COURSE_NAME"));
                courses1.setCourseLimit(resultSet.getInt("COURSE_LIMIT"));
                courses.add(courses1);
            }

            for (Courses crs : courses) {
                Courses courses2 = new Courses();
                courses2.setCourseId(crs.getCourseId());
                courses2.setCourseName(crs.getCourseName());
                courses2.setCourseLimit(crs.getCourseLimit());
                courses2.setModules(getAllModulesWithCourseId(crs.getCourseId()));
                coursesList.add(courses2);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return coursesList;
    }

    //get courses for Admin with AdminId
    public static List<Courses> getAllCoursesWithModulesByAdminID(String adminId) {
        List<Courses> courses = new ArrayList<>();
        List<Courses> coursesList = new ArrayList<>();
        try {
            preparedStatement = DBConnection.getConnection().prepareStatement("select COURSE_ID , COURSE_NAME , COURSE_LIMIT from courses where IS_ACTIVE=? AND ADMIN_ID=?");
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, Integer.parseInt(adminId));
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Courses courses1 = new Courses();
                courses1.setCourseId(resultSet.getInt("COURSE_ID"));
                courses1.setCourseName(resultSet.getString("COURSE_NAME"));
                courses1.setCourseLimit(resultSet.getInt("COURSE_LIMIT"));
                courses.add(courses1);
            }

            for (Courses crs : courses) {
                Courses courses2 = new Courses();
                courses2.setCourseName(crs.getCourseName());
                courses2.setCourseLimit(crs.getCourseLimit());
                courses2.setModules(getAllModulesWithCourseId(crs.getCourseId()));
                coursesList.add(courses2);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return coursesList;
    }

    public static List<Courses> getAllAssignedModules(int studentId) {
        List<Courses> courses = new ArrayList<>();
        List<Modules> modules = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("SELECT cs.`COURSE_NAME` , cs.`COURSE_LIMIT`, md.`MODULE_ID` , md.`MODULE_NAME` , md.`IS_MENDATORY` FROM modules md JOIN student_course sc\n" +
                    "ON(md.`MODULE_ID` = sc.`MODULE_ID`) JOIN courses cs ON(cs.`COURSE_ID` = sc.`COURSE_ID`)\n" +
                    "AND sc.`STUDENT_ID` = ?\n");
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet1 = preparedStatement.executeQuery();
            Courses courses1 = new Courses();
            while (resultSet1.next()) {
                Modules modules1 = new Modules();
                courses1.setCourseName(resultSet1.getString("COURSE_NAME"));
                courses1.setCourseLimit(resultSet1.getInt("COURSE_LIMIT"));
                modules1.setModuleId(resultSet1.getInt("MODULE_ID"));
                modules1.setModuleName(resultSet1.getString("MODULE_NAME"));
                modules1.setIsMandatory(resultSet1.getString("IS_MENDATORY"));
                modules.add(modules1);
            }
            courses1.setModules(modules);
            courses.add(courses1);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return courses;
    }

    public static List<Modules> getAllModulesWithCourseId(int courseId) {
        List<Modules> modules = new ArrayList<>();
        try {
            preparedStatement = DBConnection.getConnection().prepareStatement("SELECT * FROM modules WHERE IS_ACTIVE = ?  AND COURSE_ID = ?;");
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, courseId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Modules modules1 = new Modules();
                modules1.setModuleId(resultSet.getInt("MODULE_ID"));
                modules1.setModuleName(resultSet.getString("MODULE_NAME"));
                modules1.setIsMandatory(resultSet.getString("IS_MENDATORY"));
                modules.add(modules1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return modules;
    }

    //insert data in student_course
    public static int assignCourseAndModules(CourseModuleDto courseModuleDto) {
        int row = 0;
        List<Modules> modules = courseModuleDto.getModules();
        if (!modules.isEmpty()) {
            for (Modules modules1 : modules) {
                try {
                    String query = "INSERT INTO student_course (" +
                            "STUDENT_ID," +
                            " COURSE_ID," +
                            " MODULE_ID," +
                            " IS_SELECT)" +
                            "VALUES (?,?,?,?)";
                    System.out.println("------->" + query);
                    preparedStatement = DBConnection.getConnection().prepareStatement(query);
                    preparedStatement.setInt(1, courseModuleDto.getStudentId());
                    preparedStatement.setInt(2, courseModuleDto.getCoursesId());
                    preparedStatement.setInt(3, modules1.getModuleId());
                    preparedStatement.setString(4, courseModuleDto.getIsSelect());

                    row += preparedStatement.executeUpdate();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return row;
    }

    public static List<Student> getAllStudentsWithCourse(int adminId) {
        List<Student> students = new ArrayList<>();
        try {
            preparedStatement = DBConnection.getConnection().prepareStatement("select st.`STUDENT_FIRST_NAME` , st.`STUDENT_EMAIL` , st.`STUDENT_CONTACT` , st.`STUDENT_PAYMENT_STATUS`, cs.`COURSE_NAME`,\n" +
                    "cs.`COURSE_LIMIT` from students st\n" +
                    "join student_course sc on (st.`STUDENT_ID` = sc.`STUDENT_ID`) join courses cs on(cs.`COURSE_ID` = sc.`COURSE_ID`) join admin\n" +
                    "on(admin.`ADMIN_ID` = cs.`ADMIN_ID`) where admin.`ADMIN_ID`=? GROUP BY st.`STUDENT_FIRST_NAME`;");
            preparedStatement.setInt(1, adminId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Courses courses = new Courses();
                courses.setCourseName(resultSet.getString("COURSE_NAME"));
                courses.setCourseLimit(resultSet.getInt("COURSE_LIMIT"));
                students.add(new Student(null,
                        resultSet.getString("STUDENT_FIRST_NAME"),
                        null,
                        resultSet.getString("STUDENT_CONTACT"),
                        resultSet.getString("STUDENT_EMAIL"),
                        null,
                        null,
                        resultSet.getString("STUDENT_PAYMENT_STATUS"),
                        courses,
                        null));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return students;
    }

    public static List<Student> getAllStudentsWithModules(int adminId) {
        List<Student> students = new ArrayList<>();
        List<Modules> modulesList = new ArrayList<>();
        try {
            preparedStatement = DBConnection.getConnection().prepareStatement("SELECT st.`STUDENT_FIRST_NAME`, md.`MODULE_NAME`,md.`IS_MENDATORY` FROM modules md JOIN student_course sc ON(md.`MODULE_ID` = sc.`MODULE_ID`) JOIN\n" +
                    "students st ON (st.`STUDENT_ID` = sc.`STUDENT_ID`) JOIN admin\n" +
                    "ON(admin.`ADMIN_ID` = md.`ADMIN_ID`) WHERE admin.`ADMIN_ID`=?");
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Student student =new Student();
            while (resultSet.next()) {
                Modules modules = new Modules();

                student.setStudentFirstName(resultSet.getString("STUDENT_FIRST_NAME"));
                modules.setModuleName(resultSet.getString("MODULE_NAME"));
                modules.setIsMandatory(resultSet.getString("IS_MENDATORY"));
                modulesList.add(modules);
                student.setModules(modulesList);
            }
            students.add(student);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return students;
    }

}
