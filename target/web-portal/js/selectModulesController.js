/**
 * Created by Sarim on 4/25/2020.
 */


/**
 * Created by Sarim on 4/20/2020.
 */

var app = angular.module("selectModulesApp", []);

//Controller Part
app.controller("selectModulesController", function ($scope, $http) {

    $scope.studentId = "";
    $scope.courses = [];
    $scope.selection = [];
    $scope.filterCourses = [];
    $scope.selectedItems = [];
    $scope.selectModules = [];
    $scope.moduleForm = {
        studentId: "",
        coursesId: "",
        modules: []
    };


    if (localStorage.getItem('studentObject')) {
        $scope.studentObject = JSON.parse(localStorage.getItem('studentObject'));
        $scope.studentId = $scope.studentObject.studentId;
        $scope.moduleForm.studentId = $scope.studentId;
        console.log("Student ID----->" + $scope.studentId);
    }

    $scope.compareFn = function(obj1, obj2){
        return obj1.id === obj2.id;
    };
    // Now load the data from server
    _refreshCoursesData();
    _refreshModulesData();

    $scope.toggleSelection = function toggleSelection(module) {
        var idx = $scope.selection.indexOf(module);

        // Is currently selected
        if (idx > -1) {
            $scope.selection.splice(idx, 1);
        }
        // Is newly selected
        else {
            $scope.selection.push(module);
        }
        console.log('$scope.selection', $scope.selection);
    };


    // HTTP POST/PUT methods for add/edit
    $scope.sendData = function () {
        var modules = [];
        $scope.selection.forEach(function (module, index) {
           modules.push({ moduleId:  module.moduleId});
        });
        var temp = {
            studentId: $scope.moduleForm.studentId,
            isSelect: 'Y',
            coursesId: $scope.courseSelected.courseId,
            modules: modules
        };
        console.log('temp', temp);
        $http({
            method: "POST",
            url: "http://localhost:8080/studentServlet/assign-courses-modules",
            data: angular.toJson(temp),
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        }).then(_success, _error);
    };



    $scope.onCourseChange = function () {
        if ($scope.courseSelected && $scope.courseSelected.courseId){
            $scope.filterCourses = [];
            $scope.courses.forEach(function (course, index) {
                if (course.courseId == $scope.courseSelected.courseId) {
                    $scope.filterCourses.push($scope.courses[index]);
                }
            });
            console.log($scope.filterCourses);
        }
    };

    // HTTP GET- get all courses collection
    function _refreshCoursesData() {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/studentServlet/getCoursesModules'
        }).then(
            function (res) { // success
                $scope.courses = res.data;
                console.log("----->" + $scope.courses)
            },
            function (res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }

    function _refreshModulesData() {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/studentServlet/getAssignModules',
            params: {studentId: $scope.studentId}
        }).then(
            function (res) { // success
                debugger;
                $scope.selectModules = res.data;
                console.log("MOD----->" + $scope.selectModules)
            },
            function (res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }

    function _success(res) {
        if (res.data.responseCode == 201) {
            // _clearFormData();
            _refreshCoursesData();
            _refreshModulesData();
            alert(res.data.status);
        } else if (res.data.responseCode == 401) {
            alert(res.data.message);
        }
    }

    function _error(res) {
        console.log('error', res);
        alert("Error: " + res.statusText);
        $scope.resetForm();
    }

    // Logout Function
    $scope._logOut = function() {
        localStorage.clear();
        window.location.replace('/login.html');
    };

    /*// Clear the form
     function _clearFormData() {
     $scope.moduleForm.courseId = -1;
     $scope.moduleForm.studentId = "";
     };*/

});