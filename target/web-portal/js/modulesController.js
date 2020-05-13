/**
 * Created by Sarim on 4/20/2020.
 */

var app = angular.module("moduleApp", []);

// Controller Part
app.controller("modulesController", function ($scope, $http) {

    $scope.courses = [];
    $scope.moduleForm = {
        moduleId: -1,
        moduleName: "",
        isMandatory: "",
        adminId: ""
    };


    if (localStorage.getItem('adminObject')) {
        $scope.adminObject = JSON.parse(localStorage.getItem('adminObject'));
        $scope.moduleForm.adminId = $scope.adminObject.adminId;
        console.log("Admin ID----->" + $scope.moduleForm.adminId);
    }

    // Get Courses
    if($scope.moduleForm.adminId){
        _getCoursesByAdminId();
    }

    // Now load the data from server
    if($scope.moduleForm.adminId) {
        _refreshModulesData();
    }

    // HTTP POST/PUT methods for add/edit
    $scope.sendData = function () {
        var url = "";

        if ($scope.moduleForm.moduleId == -1) {
            url = 'http://localhost:8080/modules-servlet/add-module';
        } else {
            url = 'http://localhost:8080/modules-servlet/update-module';
        }

        $http({
            method: "POST",
            url: url,
            data: angular.toJson($scope.moduleForm),
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        }).then(_success, _error);
    };

    // In case of edit
    $scope.editModule = function (module, item) {
        console.log('module', module);
        console.log('item', item);
        $scope.moduleForm.moduleId = module.moduleId;
        $scope.moduleForm.moduleName = module.moduleName;
        $scope.moduleForm.isMandatory = module.isMandatory;
        $scope.moduleForm.courseId = item.courseId;
    };


    // Logout Function
    $scope._logOut = function() {
        localStorage.clear();
        window.location.replace('/login.html');
    };

    // HTTP DELETE- delete course by Id
    $scope.deleteModule = function (item) {
        $http({
            method: 'POST',
            url: 'http://localhost:8080/modules-servlet/delete-module',
            params: {moduleId: item.moduleId}
        }).then(_success, _error);
    };

    // HTTP GET- get all courses collection
    function _refreshModulesData() {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/modules-servlet/getModules',
            params: {adminId: $scope.moduleForm.adminId}
        }).then(
            function (res) { // success
                console.log('res.data', res.data);
                $scope.modules = res.data;
            },
            function (res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }

    function _getCoursesByAdminId() {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/modules-servlet/getCoursesByAdminId',
            params: {adminId: $scope.moduleForm.adminId}
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
        // debugger;
        if (res.data.responseCode == 201) {
            _clearFormData();
            _refreshModulesData();
        } else if (res.data.responseCode == 401) {
            alert(res.data.message);
        }
    }

    function _error(res) {
        console.log('error', res);
        alert("Error: " + res.statusText);
        _clearFormData();
    }

    // Clear the form
    function _clearFormData() {
        $scope.moduleForm.moduleId = -1;
        $scope.moduleForm.moduleName = "";
        $scope.moduleForm.isMandatory = "";
        $scope.moduleForm.courseId = "";
    };

});