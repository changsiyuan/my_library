var mod_admin = angular.module("admin", ['admin_configuration', 'ngRoute', 'ngCookies', 'ngTable', 'ngTableExport']);

mod_admin.controller("AdminCtrl", function($scope, $route, $cookies, $cookieStore, adminUserLogin, allUserInfo, NgTableParams,
        $filter, userInfosAffirm, userPasswdReset, adminDeleteUser, adminDeleteUser1, judgeUserBook,
        userBorrowBookInfos, allBook, newBookInput) {
    //获取cookie信息
    $scope.current_adminNum = $cookieStore.get("current_adminNum")
    $scope.judgeAdminLogin = $cookieStore.get("judgeAdminLogin")
    $scope.flag = 0;

    //管理员不开放注册提示
    $scope.adminRegisterHint = function() {
        alert("国家图书馆管理员账户暂不开放注册，请到国家图书馆申请注册管理员！");
    }

    //管理员登陆
    $scope.adminLogin = function() {
        if (!$scope.LoginAdminNum || !$scope.LoginAdminPassword) {
            alert("请填写完整的管理员登录信息！")
        } else {
            adminUserLogin.query({adminNum: $scope.LoginAdminNum, adminPasswd: $scope.LoginAdminPassword}, function(data) {
                if (!data[0]) {
                    alert("您输入的管理员账号或密码有误，请重新登陆！")
                } else {
                    $scope.judgeIfAdminLogin = 1;
                    $cookieStore.put('current_adminNum', data[0].id);
                    $cookieStore.put('judgeAdminLogin', $scope.judgeIfAdminLogin);

                    $scope.current_adminNum = $cookieStore.get("current_adminNum")
                    $scope.judgeAdminLogin = $cookieStore.get("judgeAdminLogin")
                }
            })
        }
    }

    //管理员退出
    $scope.adminLogout = function() {
        $scope.judgeAdminLogin = 0;
        $cookieStore.remove('current_adminNum');
        $cookieStore.remove('judgeAdminLogin');
        $route.reload();
    }

    //管理员登录提示
    $scope.adminLoginHint = function() {
        alert("请您先登录管理员账户！")
    }

    //管理员查看除密码以外的用户信息
    $scope.checkUserInfo = function() {
        allUserInfo.query(function(data) {
            $scope.flag = 1;
            $scope.tableParams = new NgTableParams({
                page: 1,
                count: 10,
                sorting: {
                    userId: 'asc'
                },
                filter: {
                    userId: ''
                }
            }, {
                total: data.length,
                getData: function($defer, params) {
                    var orderedData = params.sorting() ?
                            $filter('orderBy')(data, params.orderBy()) :
                            data;
                    var orderedData1 = params.filter() ?
                            $filter('filter')(orderedData, params.filter()) :
                            orderedData;
                    $defer.resolve(orderedData1.slice((params.page() - 1) * params.count(), params.page() * params.count()));

                    $("[name='userId']").attr("placeholder", "按学号筛选")
                    $("[name='userName']").attr("placeholder", "按用户名筛选")
                    $("[name='userSex']").attr("placeholder", "按性别筛选")
                    $("[name='userSchool']").attr("placeholder", "按学院信息筛选")
                    $("[name='userDescription']").attr("placeholder", "按用户描述筛选")
                    $("[name='phoneNumber']").attr("placeholder", "按联系方式筛选")
                }
            });
        });
    }

    //管理员查看用户借书情况
    $scope.userBorrowBookInfo = function() {
        userBorrowBookInfos.query(function(data) {
            $scope.flag = 3;
            $scope.tableParams1 = new NgTableParams({
                page: 1,
                count: 10,
                sorting: {
                    borrow_num: 'asc'
                },
                filter: {
                    borrow_num: ''
                }
            }, {
                total: data.length,
                getData: function($defer, params) {
                    var orderedData = params.sorting() ?
                            $filter('orderBy')(data, params.orderBy()) :
                            data;
                    var orderedData1 = params.filter() ?
                            $filter('filter')(orderedData, params.filter()) :
                            orderedData;
                    $defer.resolve(orderedData1.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                }
            });
        })
    }

    //管理员查看所有书籍信息
    $scope.allBooks = function() {
        allBook.query(function(data) {
            $scope.flag = 4;
            $scope.tableParams2 = new NgTableParams({
                page: 1,
                count: 10,
                sorting: {
                    bookId: 'asc'
                },
                filter: {
                    bookId: ''
                }
            }, {
                total: data.length,
                getData: function($defer, params) {
                    var orderedData = params.sorting() ?
                            $filter('orderBy')(data, params.orderBy()) :
                            data;
                    var orderedData1 = params.filter() ?
                            $filter('filter')(orderedData, params.filter()) :
                            orderedData;
                    $defer.resolve(orderedData1.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                }
            });
        })
    }

    //用户信息确认，以便重置密码或者删除用户
    $scope.userInfoAffirm = function() {
        userInfosAffirm.query({studentNum: $scope.userID}, function(data) {
            if (data.length == 0) {
                alert("您查询的用户不存在！")
            } else {
                $scope.userId = data[0].userId
                $scope.userName = data[0].userName
                $scope.userSex = data[0].userSex
                $scope.userSchool = data[0].userSchool
                $scope.userDescription = data[0].userDescription
                $scope.phoneNumber = data[0].phoneNumber
                $scope.flag = 2;
            }
        })
    }

    //重置用户密码
    $scope.resetPasswd = function(userId) {
        userPasswdReset.save({userId: userId}, function(data) {
            alert("学号为" + userId + "的用户密码被重置为123456，请通知用户尽快登录账户并修改密码！")
        })
    }

    //删除用户
    $scope.deleteUser = function(userId) {
        //检测用户是否有书籍尚未归还
        judgeUserBook.query({userId: userId}, function(data) {
            if (data.length != 0) {
                alert("该用户有书籍尚未归还，不能删除用户！")
            } else {
                //删除用户
                adminDeleteUser.remove({userId: userId}, function() {

                })
                adminDeleteUser1.remove({userId: userId}, function() {
                    alert("学号为" + userId + "的用户已经被删除！")
                })
            }
        })
    }

    //管理员新书入库
    $scope.newBooksInput = function() {
        newBookInput.save({bookName: $scope.bookName, bookAuthor: $scope.bookAuthor, bookPress: $scope.bookPress,
            bookDescribe: $scope.bookDescribe, bookScore: $scope.bookScore, bookState: $scope.bookState, bookShelf: $scope.bookShelf,
            bookWholeNum: $scope.bookWholeNum, bookRemainNum: $scope.bookRemainNum}, function(data) {
            alert("书籍已经入库！用户可以查询到这本书！")
        })
    }
})