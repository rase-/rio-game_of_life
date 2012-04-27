<%-- 
    Document   : index
    Created on : Apr 26, 2012, 9:06:38 PM
    Author     : scolphoy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Game Of Life web-ui</title>
    </head>
    <body>
        <h1>Welcome!</h1>
        <p>Enter the game of life content file and how many threads to use.</p>
        <form action="Life" method="post" enctype="multipart/form-data">
            File: <input type="file" name="inputfile" size="20"><br />
            Threads: <input type="text" name="threads" value="4" size="4"><br />
            <input type="submit" value="Send">
        </form>
    </body>
</html>
