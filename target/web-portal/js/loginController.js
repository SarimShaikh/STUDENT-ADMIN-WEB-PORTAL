/**
 * Created by Sarim on 4/19/2020.
 */

var app = angular.module("loginApp", []);

// Controller Part
app.controller("loginController", function ($scope, $http) {

    $scope.loginForm = {
        username: "",
        password: "",
        type: ""
    };

    // HTTP POST/PUT methods for add/edit student
    // Call: http://localhost:8080/student
    $scope.sendData = function () {

        $http({
            method: "POST",
            url: "http://localhost:8080/LoginServlet/login",
            data: angular.toJson($scope.loginForm),
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*'
            }
        }).then(_success, _error);
    };

    function _success(res) {
        console.log('response', res);
        alert(res.data.message);
        if (res.data.responseCode == 200 && res.data.admin) {
            localStorage.setItem('adminObject', JSON.stringify(res.data.admin));
            window.location.replace('admin-menu.html');
        } else if (res.data.responseCode == 200 && res.data.student) {
            localStorage.setItem('studentObject', JSON.stringify(res.data.student));
            window.location.replace('select-modules.html');
        }
    }

    function _error(res) {
        var data = res.data;
        var status = res.status;
        var header = res.header;
        var config = res.config;
        alert("Error: " + status + ":" + data);
    }

});