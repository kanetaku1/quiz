<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="main.User" %> 

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Form</title>
    <link rel="stylesheet" href="css/form.css">
</head>
<body>
    <% 
        User user = (User) session.getAttribute("user"); 
        user.setUserType(User.UserType.GUEST);
    %>
    <label>
        <h4> ユーザ名 :  
            <% if(user != null){
                out.println(user.getUsername());
            } else{
                out.println("no name");
            }
            %>
        </h4>
    </label>

    <div class="btn">
        <button>
            <a href="forwardToSearch">閲覧モード</a>
        </button>
        <button>
            <a href="forwardToQuiz">回答モード</a>
        </button>
        <button>
            <a href="forwardToMake">作問モード</a>
        </button>
        <button>
            <a href="#">設定</a>
        </button>
    </div>
</body>
</html>
