angular.module("admin_configuration", ['ngResource'])
        .factory('adminUserLogin', function($resource) {
            return $resource('/hashlibrary/api/adminaccount/adminLogin/:adminNum/:adminPasswd',
                    {adminNum: '@adminNum', adminPasswd: '@adminPasswd'});
        })
        //查询全部用户信息
        .factory('allUserInfo', function($resource) {
            return $resource('/hashlibrary/api/users');
        })
        //重置密码前查询用户信息以便确认
        .factory('userInfosAffirm', function($resource) {
            return $resource('/hashlibrary/api/users/userInfo/:studentNum', {studentNum: '@studentNum'});
        })
        //管理员重置用户密码
        .factory('userPasswdReset', function($resource) {
            return $resource('/hashlibrary/api/account/passwdReset/:userId', {userId: '@userId'});
        })
        //检测用户是否有书籍尚未归还
        .factory('judgeUserBook', function($resource) {
            return $resource('/hashlibrary/api/borrow1/judgeUserBook/:userId', {userId: '@userId'});
        })
        //管理员删除特定用户(account表)
        .factory('adminDeleteUser', function($resource) {
            return $resource('/hashlibrary/api/account/deleteUser/:userId', {userId: '@userId'});
        })
        //管理员删除特定用户(users表)
        .factory('adminDeleteUser1', function($resource) {
            return $resource('/hashlibrary/api/users/deleteUser/:userId', {userId: '@userId'});
        })
        //管理员查看用户借书情况
        .factory('userBorrowBookInfos', function($resource) {
            return $resource('/hashlibrary/api/borrow1/userBorrowBookInfos');
        })
        //管理员查看所有书籍信息
        .factory('allBook', function($resource) {
            return $resource('/hashlibrary/api/book');
        })
        //管理员新书入库
        .factory('newBookInput', function($resource) {
            return $resource('/hashlibrary/api/book/newBookInput/:bookName/:bookAuthor/:bookPress/:bookDescribe/:bookScore/:bookState/:bookShelf/:bookWholeNum/:bookRemainNum',
                    {bookName: '@bookName', bookAuthor: '@bookAuthor', bookPress: '@bookPress', bookDescribe: '@bookDescribe', bookScore: '@bookScore',
                        bookState: '@bookState', bookShelf: '@bookShelf', bookWholeNum: '@bookWholeNum', bookRemainNum: '@bookRemainNum'});
        })