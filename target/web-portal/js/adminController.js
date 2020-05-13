var app = angular.module("adminApp", []);

// Controller Part
app.controller("adminController", function($scope, $http) {


    $scope.admins = [];
    $scope.adminId="";
    $scope.studentCourses=[];
    $scope.studentModules=[];
    $scope.adminForm = {
        adminName: "",
        adminEmail: "",
        adminPassword: "",
        adminContactNo: "",
        adminAddress: ""
    };

    if (localStorage.getItem('adminObject')) {
        $scope.adminObject = JSON.parse(localStorage.getItem('adminObject'));
        $scope.adminId = $scope.adminObject.adminId;
        console.log("Admin ID----->" + $scope.adminId);
    }

    _refreshStudentCourseData();
    _refreshStudentModulesData();

    // HTTP POST/PUT methods for add/edit student
    // Call: http://localhost:8080/student
    $scope.sendData = function() {

        $http({
            method: "POST",
            url: "http://localhost:8080/adminServlet/save-admin",
            data: angular.toJson($scope.adminForm),
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin':'*'
            }
        }).then(_success, _error);
    };

    function _refreshStudentCourseData() {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/adminServlet/getStudentsWithCourse',
            params: {adminId: $scope.adminId}
        }).then(
            function (res) { // success
                debugger;
                $scope.studentCourses = res.data;
                console.log("MOD----->" + $scope.selectModules)
            },
            function (res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }

    function _refreshStudentModulesData() {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/adminServlet/getStudentsWithModules',
            params: {adminId: $scope.adminId}
        }).then(
            function (res) { // success
                debugger;
                $scope.studentModules = res.data;
                console.log("MOD----->" + $scope.studentModules)
            },
            function (res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }

    // Logout Function
    $scope._logOut = function() {
        localStorage.clear();
        window.location.replace('/login.html');
    };


    function _success(res) {
        console.log('response', res);
        alert("Admin Registered successfully");
        window.location.replace('login.html');
    }

    function _error(res) {
        console.log('error', res);
        alert("Error: " + res.statusText);
        $scope.resetForm();
    }

    $scope.resetForm = function(form) {
        angular.copy({},$scope.adminForm);
    }

});