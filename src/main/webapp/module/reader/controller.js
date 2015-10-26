var mod = angular.module("reader", ['reader_configuration', 'ngRoute', 'ngCookies']);

mod.controller("ReaderCtrl", function($scope, $route, $cookies, $cookieStore, reader, test, login, searchBooks,
        searchRandomBooks, getUserInfomation, putNewUserToUsersSchema, postUserInfomation, postAlterUserName,
        checkOldPasswd, alterPasswd, bookNum, borrowBook, borrow, borrowBookNum, dueBookNum, borrowBookInfo, dueBookInfo) {
    //获取当前cookie中的用户信息
    $scope.current_UserNum = $cookieStore.get('current_userNum');
    $scope.current_UserName = $cookieStore.get('current_userName');
    $scope.judgeLogin = $cookieStore.get('judgeLogin');

    $scope.current_userSex = $cookieStore.get('current_userSex');
    $scope.current_userSchool = $cookieStore.get('current_userSchool');
    $scope.current_userDescription = $cookieStore.get('current_userDescription');
    $scope.current_phoneNumber = $cookieStore.get('current_phoneNumber');

    $scope.currentUser_BorrowBookNum = $cookieStore.get('currentUser_BorrowBookNum');
    $scope.currentUser_DueBookNum = $cookieStore.get('currentUser_DueBookNum');

    //注册用户
    $scope.add_user = function() {
        if (!$scope.newStudentNum || !$scope.newUserName || !$scope.newPassword || !$scope.newAgainPassword) {
            alert("请您填写完整的信息以便注册！")
        } else {
            //判断两次输入的密码是否相同
            if ($scope.newPassword == $scope.newAgainPassword) {
                //判断学号是否已经被注册
                test.query({studentNum: $scope.newStudentNum}, function(data) {
                    if (data[0]) {
                        alert("对不起，您的学号 " + $scope.newStudentNum + " 已经被注册！");
                    } else {
                        //将用户名、密码、学号存入account表
                        reader.save({studentNum: $scope.newStudentNum, user_name: $scope.newUserName, passward: $scope.newPassword}).$promise.then(function() {
                            alert("恭喜您注册成功！您的学号是 " + $scope.newStudentNum + " ,您可以用这个学号登陆！请您赶紧去用户信息界面填写个人信息额！");
                            //用户名和学号放入cookie
                            $scope.judgeIfLogin = 1;
                            $cookieStore.put('current_userNum', $scope.newStudentNum);
                            $cookieStore.put('current_userName', $scope.newUserName);
                            $cookieStore.put('judgeLogin', $scope.judgeIfLogin);

                            $scope.current_UserNum = $cookieStore.get('current_userNum');
                            $scope.current_UserName = $cookieStore.get('current_userName');
                            $scope.judgeLogin = $cookieStore.get('judgeLogin');
                            $route.reload();
                        });
                        //将学号、用户名存入users表
                        putNewUserToUsersSchema.save({studentNum: $scope.newStudentNum, user_name: $scope.newUserName}).$promise.then(function() {
                            $cookieStore.remove('current_userSex');
                            $cookieStore.remove('current_userSchool');
                            $cookieStore.remove('current_userDescription');
                            $cookieStore.remove('current_phoneNumber');
                        })

                        //查询用户基本信息
                        getUserInfomation.query({studentNum: $scope.newStudentNum}, function(data) {
                            if (!data[0].userSex) {
                                $cookieStore.put('current_userSex', "尚未填写");
                            } else {
                                $cookieStore.put('current_userSex', data[0].userSex);
                            }

                            if (!data[0].userSchool) {
                                $cookieStore.put('current_userSchool', "尚未填写");
                            } else {
                                $cookieStore.put('current_userSchool', data[0].userSchool);
                            }

                            if (!data[0].userDescription) {
                                $cookieStore.put('current_userDescription', "尚未填写");
                            } else {
                                $cookieStore.put('current_userDescription', data[0].userDescription);
                            }

                            if (!data[0].phoneNumber) {
                                $cookieStore.put('current_phoneNumber', "尚未填写");
                            } else {
                                $cookieStore.put('current_phoneNumber', data[0].phoneNumber);
                            }

                            $scope.current_userSex = $cookieStore.get('current_userSex');
                            $scope.current_userSchool = $cookieStore.get('current_userSchool');
                            $scope.current_userDescription = $cookieStore.get('current_userDescription');
                            $scope.current_phoneNumber = $cookieStore.get('current_phoneNumber');
                        })

                        //查询用户总借书数
                        borrowBookNum.query({userNum: $scope.newStudentNum}, function(data) {
                            //将当前用户的总借书数量放入cookie
                            $cookieStore.put('currentUser_BorrowBookNum', data.length);
                            $scope.currentUser_BorrowBookNum = $cookieStore.get('currentUser_BorrowBookNum');

                            console.log("data_length_borrowNum: " + data.length)
                        })

                        //查询用户催还数量
                        dueBookNum.query({userNum: $scope.newStudentNum}, function(data) {
                            //将当前用户催还数量放入cookie
                            $cookieStore.put('currentUser_DueBookNum', data.length);
                            $scope.currentUser_DueBookNum = $cookieStore.get('currentUser_DueBookNum');

                            console.log("data_length_dueNum: " + data.length)
                        })
                    }
                });
            } else {
                alert("对不起，您两次输入的密码不同，请重新输入！")
            }
        }
    };

    //用户登录
    $scope.userLogin = function() {
        if (!$scope.LoginStudentNum || !$scope.LoginPassword) {
            alert("请填写完整的信息！")
        } else {
            //查询用户登录信息
            login.query({studentNum: $scope.LoginStudentNum, password: $scope.LoginPassword}, function(data) {
                if (!data[0]) {
                    alert("您输入的用户名或密码有误，请重新登陆！")
                } else {
                    $scope.judgeIfLogin = 1;
                    $cookieStore.put('current_userNum', data[0].userId);
                    $cookieStore.put('current_userName', data[0].userName);
                    $cookieStore.put('judgeLogin', $scope.judgeIfLogin);

                    $scope.current_UserNum = $cookieStore.get('current_userNum');
                    $scope.current_UserName = $cookieStore.get('current_userName');
                    $scope.judgeLogin = $cookieStore.get('judgeLogin');

                    //查询用户基本信息
                    getUserInfomation.query({studentNum: $scope.LoginStudentNum}, function(data) {
                        if (!data[0].userSex) {
                            $cookieStore.put('current_userSex', "尚未填写");
                        } else {
                            $cookieStore.put('current_userSex', data[0].userSex);
                        }

                        if (!data[0].userSchool) {
                            $cookieStore.put('current_userSchool', "尚未填写");
                        } else {
                            $cookieStore.put('current_userSchool', data[0].userSchool);
                        }

                        if (!data[0].userDescription) {
                            $cookieStore.put('current_userDescription', "尚未填写");
                        } else {
                            $cookieStore.put('current_userDescription', data[0].userDescription);
                        }

                        if (!data[0].phoneNumber) {
                            $cookieStore.put('current_phoneNumber', "尚未填写");
                        } else {
                            $cookieStore.put('current_phoneNumber', data[0].phoneNumber);
                        }

                        $scope.current_userSex = $cookieStore.get('current_userSex');
                        $scope.current_userSchool = $cookieStore.get('current_userSchool');
                        $scope.current_userDescription = $cookieStore.get('current_userDescription');
                        $scope.current_phoneNumber = $cookieStore.get('current_phoneNumber');
                    })

                    //查询用户总借书数
                    borrowBookNum.query({userNum: $scope.LoginStudentNum}, function(data) {
                        //将当前用户的总借书数量放入cookie
                        $cookieStore.put('currentUser_BorrowBookNum', data.length);
                        $scope.currentUser_BorrowBookNum = $cookieStore.get('currentUser_BorrowBookNum');

                        console.log("data_length_borrowNum: " + data.length)
                    })

                    //查询用户催还数量
                    dueBookNum.query({userNum: $scope.LoginStudentNum}, function(data) {
                        //将当前用户催还数量放入cookie
                        $cookieStore.put('currentUser_DueBookNum', data.length);
                        $scope.currentUser_DueBookNum = $cookieStore.get('currentUser_DueBookNum');

                        console.log("data_length_dueNum: " + data.length)
                    })
                }
            });
        }
    }

    //退出登录
    $scope.UserLogout = function() {
        $scope.judgeLogin = 0;
        $cookieStore.remove('current_userNum');
        $cookieStore.remove('current_userName');
        $cookieStore.remove('judgeLogin');
        $cookieStore.remove('current_userSex');
        $cookieStore.remove('current_userSchool');
        $cookieStore.remove('current_userDescription');
        $cookieStore.remove('current_phoneNumber');
        $cookieStore.remove('currentUser_BorrowBookNum');
        $cookieStore.remove('currentUser_DueBookNum');
        $route.reload();
    }

    //未登录提示
    $scope.UserLogoutHint = function() {
        alert("请您先登录！")
    }

    //book.html页面默认显示书籍
    searchRandomBooks.query(function(data) {
        $scope.books = data;
        console.log("ramdombooks: " + data)
    })


    //默认显示用户借书数、催还数
    //查询用户总借书数
    borrowBookNum.query({userNum: $scope.current_UserNum}, function(data) {
        //将当前用户的总借书数量放入cookie
        $cookieStore.put('currentUser_BorrowBookNum', data.length);
        $scope.currentUser_BorrowBookNum = $cookieStore.get('currentUser_BorrowBookNum');
        console.log("data_length_borrowNum: " + data.length)
    })
    //查询用户催还数量
    dueBookNum.query({userNum: $scope.current_UserNum}, function(data) {
        //将当前用户催还数量放入cookie
        $cookieStore.put('currentUser_DueBookNum', data.length);
        $scope.currentUser_DueBookNum = $cookieStore.get('currentUser_DueBookNum');

        console.log("data_length_dueNum: " + data.length)
    })


    //搜索书籍(book.html界面)
    $scope.searchBooks = function() {
        var search = document.getElementById("q").value.toString();
        searchBooks.query({searchContent: search}, function(data) {
//            console.log(data)
            $scope.books = data;
        })
    }

    //搜索书籍（book.html界面边栏推荐书籍）
    $scope.recommendBook = function(book) {
        searchBooks.query({searchContent: book}, function(data) {
            $scope.books = data;
        })
    }

    //书籍评分，将分数转换成数组
    $scope.getNumber = function(num) {
        return new Array(num);
    }

    //借阅书籍
    $scope.borrowBooks = function(userNum, bookId) {
        //查询此书是否还有剩余可借
        bookNum.query({bookId: bookId}, function(data) {
            if (data[0].bookRemainNum == 0 || !data[0].bookRemainNum) {
                alert("您要借阅的这本书总藏书量：" + data[0].bookWholeNum + ", 可借数量：" + data[0].bookRemainNum + "。请您借阅其他图书。")
            } else if (data[0].bookRemainNum > 0) {
                //修改书籍表(可借数量减一)
                borrowBook.save({bookId: bookId}, function(data) {
                    console.log("xiugaibook")
                })
                //修改借阅表borrow1
                borrow.query({userNum: userNum, bookId: bookId}, function(data) {
                    console.log("xiugai borrow")
                })
                alert("借书成功！图书信息已经计入您的账户！请尽快到国家图书馆1号厅书架编号为：" + data[0].bookshelfNumber +
                        ", 领取图书：《" + data[0].bookName + "》。")
            }
        })
    }

    //fade中获取当前图书的偏移量
    $scope.bookIndex = function(index) {
        $scope.getBookIndex = index;
    }

    //修改用户基本信息
    $scope.alterUserInfo = function(userNum) {
        if (!$scope.alterUserName || !$scope.alterUserSex || !$scope.alterUserSchool || !$scope.alterUserDescription || !$scope.alterPhoneNum) {
            alert("请您填写完整的个人基本信息！");
        } else {
            postUserInfomation.save({studentNum: userNum, user_name: $scope.alterUserName, user_sex: $scope.alterUserSex, user_school: $scope.alterUserSchool,
                user_description: $scope.alterUserDescription, user_phoneNum: $scope.alterPhoneNum}, function(data) {
                getUserInfomation.query({studentNum: userNum}, function(data) {
                    $cookieStore.put('current_userName', data[0].userName);
                    $cookieStore.put('current_userSex', data[0].userSex);
                    $cookieStore.put('current_userSchool', data[0].userSchool);
                    $cookieStore.put('current_userDescription', data[0].userDescription);
                    $cookieStore.put('current_phoneNumber', data[0].phoneNumber);

                    $scope.current_UserName = $cookieStore.get('current_userName');
                    $scope.current_userSex = $cookieStore.get('current_userSex');
                    $scope.current_userSchool = $cookieStore.get('current_userSchool');
                    $scope.current_userDescription = $cookieStore.get('current_userDescription');
                    $scope.current_phoneNumber = $cookieStore.get('current_phoneNumber');
                })
            })
            postAlterUserName.save({studentNum: userNum, user_name: $scope.alterUserName}, function(data) {

            })
            alert("您的用户基本信息修改成功！")
            $route.reload();
        }
    }

    //修改密码
    $scope.alterPassword = function(userNum) {
        if (!$scope.oldPassword || !$scope.newPassword || !$scope.againNewPassword) {
            alert("请您填写完整的改密信息！");
        } else if ($scope.newPassword != $scope.againNewPassword) {
            alert("您两次填写的新密码不一样，请重新填写！")
        } else {
            //查询旧密码是否正确
            checkOldPasswd.query({studentNum: userNum}, function(data) {
                if (data[0].password != $scope.oldPassword) {
                    alert("您填写的旧密码不正确，无法改密！")
                } else {
                    //修改密码
                    alterPasswd.save({studentNum: userNum, newPasswd: $scope.newPassword}, function(data) {
                        alert("改密成功！请您用新密码重新登录！")
                        $scope.UserLogout();
                        location.href = "reader.html";
                    })
                }
            })
        }
    }

    //检测密码强度（获取用户密码）
    $scope.testPasswdStrength = function(userNum) {
        checkOldPasswd.query({studentNum: userNum}, function(data) {
            $scope.passwdStrengthValue = 0;
            for (var i = 0; i < data[0].password.length; i++) {
                if (data[0].password[i] > '0' && data[0].password[i] < '9') {
                    $scope.passwdStrengthValue = 20;
                    continue;
                } else if (data[0].password[i] > 'a' && data[0].password[i] < 'z') {
                    $scope.passwdStrengthValue = 40;
                    break;
                } else if (data[0].password[i] > 'A' && data[0].password[i] < 'Z') {
                    $scope.passwdStrengthValue = 60;
                    break;
                } else {
                    $scope.passwdStrengthValue = 80;
                    break;
                }
            }
            $scope.myProgressBar = {width: $scope.passwdStrengthValue + '%'};
        })
    }

    //密码强度不够提示
    $scope.passwdStrengthHint = function() {
        alert("您可以通过修改密码，在密码中混合使用数字、小写字母、大写字母可以提升密码强度！")
    }

    //查询用户借阅的图书
    $scope.token = 0
    $scope.borrowBook = function(userNum) {
        $scope.token = 1
        borrowBookInfo.query({userNum: userNum}, function(data) {
            $scope.userBorrowBook = data;
        })
    }

    //查询用户催还的图书
    $scope.dueBook = function(userNum) {
        $scope.token = 2
        dueBookInfo.query({userNum: userNum}, function(data) {
            $scope.userBorrowBook = data;
        })
    }

    //图书借阅、归还时间提示
    $scope.borrowTimeHint = function(borrowTime) {
        alert("借阅时间：" + borrowTime);
    }
});

//进度条的controller
mod.controller('ProgressCtrl', function($scope) {
    $scope.max = 200;

    $scope.random = function() {
        var value = Math.floor((Math.random() * 100) + 1);
        var type;

        if (value < 25) {
            type = 'success';
        } else if (value < 50) {
            type = 'info';
        } else if (value < 75) {
            type = 'warning';
        } else {
            type = 'danger';
        }

        $scope.showWarning = (type === 'danger' || type === 'warning');

        $scope.dynamic = value;
        $scope.type = type;
    };
    $scope.random();

    $scope.randomStacked = function() {
        $scope.stacked = [];
        var types = ['success', 'info', 'warning', 'danger'];

        for (var i = 0, n = Math.floor((Math.random() * 4) + 1); i < n; i++) {
            var index = Math.floor((Math.random() * 4));
            $scope.stacked.push({
                value: Math.floor((Math.random() * 30) + 1),
                type: types[index]
            });
        }
    };
    $scope.randomStacked();
})