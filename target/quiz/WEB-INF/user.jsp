<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Resistor</title>
    <link rel="stylesheet" href="user.css">
</head>
<body>
    <div class="send">
        <label for="username"><h4> ユーザ名 :  </h4>  <input type="text" id="username" name="username" required minlength="0" maxlength="20" size="25" /></label>
        <form action="userResist" method="">
            <input type="submit" value="送信する">
        </form>
    </div>
</body>