/**
 * Created by Sarim on 4/20/2020.
 */

var app = angular.module("courseApp", []);

// Controller Part
app.controller("courseController", function ($scope, $http) {

    $scope.courses = [];
    $scope.courseForm = {
        courseId: -1,
        courseName: "",
        courseDescription: "",
        courseLimit: "",
        adminId: ""
    };


    if (localStorage.getItem('adminObject')) {
        $scope.adminObject = JSON.parse(localStorage.getItem('adminObject'));
        $scope.courseForm.adminId = $scope.adminObject.adminId;
        console.log("Admin ID----->" + $scope.courseForm.adminId);
    }

    // Now load the data from server
    _refreshCoursesData();


    // HTTP POST/PUT methods for add/edit
    $scope.sendData = function () {

        var url = "";

        if ($scope.courseForm.courseId == -1) {
            url = 'http://localhost:8080/coursesServlet/add-course';
        } else {
            url = 'http://localhost:8080/coursesServlet/update-course';
        }

        $http({
            method: "POST",
            url: url,
            data: angular.toJson($scope.courseForm),
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        }).then(_success, _error);
    };

    // In case of edit
    $scope.editCourse = function (course) {
        $scope.courseForm.courseId = course.courseId;
        $scope.courseForm.courseName = course.courseName;
        $scope.courseForm.courseDescription = course.courseDescription;
        $scope.courseForm.courseLimit = course.courseLimit;
    };

    // HTTP DELETE- delete course by Id
    $scope.deleteCourse = function (course) {
        $http({
            method: 'POST',
            url: 'http://localhost:8080/coursesServlet/delete-course',
            params: {courseId: course.courseId}
        }).then(_success, _error);
    };

    // HTTP GET- get all courses collection
    function _refreshCoursesData() {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/coursesServlet/getCourses',
            params: {adminId: $scope.courseForm.adminId}
        }).then(
            function (res) { // success
                $scope.courses = res.data;
            },
            function (res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }

    function _success(res) {
        debugger;
        if (res.data.responseCode == 201) {
            _clearFormData();
            _refreshCoursesData();
        } else if (res.data.responseCode == 401) {
            alert(res.data.message);
        }
    }

    function _error(res) {
        console.log('error', res);
        alert("Error: " + res.statusText);
        $scope.resetForm();
    }

    // Clear the form
    function _clearFormData() {
        $scope.courseForm.courseId = -1;
        $scope.courseForm.courseName = "";
        $scope.courseForm.courseDescription = "";
        $scope.courseForm.courseLimit = "";
    };

});