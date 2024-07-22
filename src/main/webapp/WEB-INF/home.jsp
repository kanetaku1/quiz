<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="main.User" %> 

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/home.css">
    <title>ホーム</title>
</head>
<body>
    <% 
        User user = (User) session.getAttribute("user");
    %>
    <label>
        <h4> ユーザ名 :  
            <% if(user != null){
                out.println(user.getUsername());
            } else{
                out.println("no name");
            }%>
        </h4>
    </label>

    <div class="btn">
        <button>
            <a href="Search">閲覧モード</a>
        </button>
        <button>
            <a href="Quiz">回答モード</a>
        </button>
        <button>
            <a href="Make">作問モード</a>
        </button>
        <button>
            <a href="Setting">設定</a>
        </button>
    </div>
</body>
</html>
