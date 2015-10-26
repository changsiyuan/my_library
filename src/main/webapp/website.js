var website = angular.module('website', ['ngAnimate', 'ngTouch']);

website.controller('MainCtrl', function($scope) {
    $scope.slides = [
        {image: 'images/library1.png', description: 'Image 00'},
        {image: 'images/library3.png', description: 'Image 01'},
        {image: 'images/library5.png', description: 'Image 02'}
    ];

    $scope.direction = 'left';
    $scope.currentIndex = 0;

    $scope.setCurrentSlideIndex = function(index) {
        $scope.direction = (index > $scope.currentIndex) ? 'left' : 'right';
        $scope.currentIndex = index;
    };

    $scope.isCurrentSlideIndex = function(index) {
        return $scope.currentIndex === index;
    };

    $scope.prevSlide = function() {
        $scope.direction = 'left';
        $scope.currentIndex = ($scope.currentIndex < $scope.slides.length - 1) ? ++$scope.currentIndex : 0;
    };

    $scope.nextSlide = function() {
        $scope.direction = 'right';
        $scope.currentIndex = ($scope.currentIndex > 0) ? --$scope.currentIndex : $scope.slides.length - 1;
    };
});
website.animation('.slide-animation', function() {
    return {
        beforeAddClass: function(element, className, done) {
            var scope = element.scope();

            if (className == 'ng-hide') {
                var finishPoint = element.parent().width();
                if (scope.direction !== 'right') {
                    finishPoint = -finishPoint;
                }
                TweenMax.to(element, 0.5, {left: finishPoint, onComplete: done});
            }
            else {
                done();
            }
        },
        removeClass: function(element, className, done) {
            var scope = element.scope();

            if (className == 'ng-hide') {
                element.removeClass('ng-hide');

                var startPoint = element.parent().width();
                if (scope.direction === 'right') {
                    startPoint = -startPoint;
                }

                TweenMax.fromTo(element, 0.5, {left: startPoint}, {left: 0, onComplete: done});
            }
            else {
                done();
            }
        }
    };
});

var app = angular.module('app', [
    'ngRoute',
    'HDFSApp',
    'PigApp',
    'account.service',
    'account.controller',
//    'configuration.service',
//    'configuration.controller',
//    'overview.controller',
//    'overview-category.controller',
//    'base.directive',
//    'category.directive',
//    'jobmonitor.service',
//    'jobmonitor.controller',
]);

app.config(['$routeProvider', function($routeProvider) {
        $routeProvider
                .when('/', {
                    redirectTo: '/home'
                })
                .when('/reader', {templateUrl: "module/reader/reader.html"})

                .otherwise({
                    redirectTo: '/home'
                });
    }]);

app.controller("indexCtr", function($scope, $http, $route, $location, CurrentUser) {
//    $http.get("api/account/current_user").success(function (data) {
//        $scope.current_user = data;
//    }).error(function (data) {
//        $location.path("/login.html");
//        location.href = "login.html";
//    });

    CurrentUser.get_current_user($scope, CurrentUser);

    $scope.getMenuActiveClass = function(path) {
        if ($location.path().contains(path)) {
            //alert(path);
            return "active"
        } else {
            return ""
        }
    };

    $scope.logout = function() {
        $http.get("api/account/logout").success(function(data) {
            location.href = "login.html";
        }).error(function(data) {
            location.href = "login.html";
        });
    };

});

