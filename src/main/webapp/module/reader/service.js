angular.module("reader_configuration", ['ngResource'])
        .factory('test', function($resource) {
            return $resource('/hashlibrary/api/account/newUser/:studentNum', {studentNum: '@studentNum'});
        })
        .factory('reader', function($resource) {
            return $resource('/hashlibrary/api/account/newUser/:studentNum/:user_name/:passward',
                    {studentNum: '@studentNum', user_name: '@user_name', passward: '@passward'});
        })
        .factory('login', function($resource) {
            return $resource('/hashlibrary/api/account/UserLogin/:studentNum/:password',
                    {studentNum: '@studentNum', password: '@password'});
        })
        .factory('searchBooks', function($resource) {
            return $resource('/hashlibrary/api/book/searchBook')
        })
        .factory('searchRandomBooks', function($resource) {
            return $resource('/hashlibrary/api/book/randomBook')
        })
        .factory('getUserInfomation', function($resource) {
            return $resource('/hashlibrary/api/users/userInfo/:studentNum',
                    {studentNum: '@studentNum'})
        })
        .factory('putNewUserToUsersSchema', function($resource) {
            return $resource('/hashlibrary/api/users/newUserInfo/:studentNum/:user_name',
                    {studentNum: '@studentNum', user_name: '@user_name'})
        })
        .factory('postUserInfomation', function($resource) {
            return $resource('/hashlibrary/api/users/alterUserInfo/:studentNum/:user_name/:user_sex/:user_school/:user_description/:user_phoneNum',
                    {studentNum: '@studentNum', user_name: '@user_name', user_sex: '@user_sex', user_school: '@user_school',
                        user_description: '@user_description', user_phoneNum: '@user_phoneNum'})
        })
        .factory('postAlterUserName', function($resource) {
            return $resource('/hashlibrary/api/account/postAlterUserName/:studentNum/:user_name',
                    {studentNum: '@studentNum', user_name: '@user_name'})
        })
        .factory('checkOldPasswd', function($resource) {
            return $resource('/hashlibrary/api/account/getOldPasswd/:studentNum',
                    {studentNum: '@studentNum'})
        })
        .factory('alterPasswd', function($resource) {
            return $resource('/hashlibrary/api/account/alterPasswd/:studentNum/:newPasswd',
                    {studentNum: '@studentNum',newPasswd:'@newPasswd'})
        })
        .factory('bookNum', function($resource) {
            return $resource('/hashlibrary/api/book/bookNum')
        })
        //修改书籍表(可借数量减一)
        .factory('borrowBook', function($resource) {
            return $resource('/hashlibrary/api/book/borrowBook/:bookId',{bookId:'@bookId'})
        })
        //修改借阅表borrow1
         .factory('borrow', function($resource) {
            return $resource('/hashlibrary/api/borrow1/addRecord')
        })
        //查询某用户借阅信息
        .factory('borrowBookNum', function($resource) {
            return $resource('/hashlibrary/api/borrow1/borrowBookNum/:userNum',{userNum:'@userNum'})
        })
        //查询某用户催还数量
        .factory('dueBookNum', function($resource) {
            return $resource('/hashlibrary/api/borrow1/dueBookNum/:userNum',{userNum:'@userNum'})
        })
        //查询用户总借书的具体信息
        .factory('borrowBookInfo', function($resource) {
            return $resource('/hashlibrary/api/book/borrowBookInfos/:userNum',{userNum:'@userNum'})
        })
        //查询用户催还书籍的具体信息
        .factory('dueBookInfo', function($resource) {
            return $resource('/hashlibrary/api/book/dueBookInfos/:userNum',{userNum:'@userNum'})
        })
