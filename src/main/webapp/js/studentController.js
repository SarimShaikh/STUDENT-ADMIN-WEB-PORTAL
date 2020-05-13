var app = angular.module("studentApp", []);

// Controller Part
app.controller("studentController", function ($scope, $http) {


    $scope.students = [];
    $scope.studentForm = {
        studentFirstName: "",
        studentLastName: "",
        studentContactNo: "",
        studentEmail: "",
        studentPassword: "",
        studentAddress: "",
        paymentStatus: ""
    };

    // HTTP POST/PUT methods for add/edit student
    // Call: http://localhost:8080/student
    $scope.sendData = function () {

        $http({
            method: "POST",
            url: "http://localhost:8080/studentServlet/save-student",
            data: angular.toJson($scope.studentForm),
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        }).then(_success, _error);
    };

    function _success(res) {
        alert("Student Registered successfully");
        window.location.replace('login.html');
    }

    function _error(res) {
        console.log('error', res);
        alert("Error: " + res.statusText);
        $scope.resetForm();
    }

    $scope.resetForm = function (form) {
        angular.copy({}, $scope.studentForm);
    }

});