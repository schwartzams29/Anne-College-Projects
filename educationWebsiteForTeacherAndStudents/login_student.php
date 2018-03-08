<?php
    session_start();
    $message = "";
    if( !empty($_POST["username"]) ){
        $db_conn = mysqli_connect("localhost", "root", "");
        mysqli_select_db($db_conn, "school_db");
      
        $user = mysqli_real_escape_string($db_conn, $_POST['username']);
        $pass = mysqli_real_escape_string($db_conn, $_POST['password']);

        $cmd = "SELECT id FROM students WHERE username='$user' AND password='$pass'";
        $result = mysqli_query($db_conn, $cmd);
        $row = mysqli_fetch_array($result);
        if($row != null && mysqli_num_rows($result)==1){
            $_SESSION['activeStudent'] = $user;
            header("location: student_assigmentChoice.php");
        }
        else
            $message = "Username or Password is invalid";
    }
?>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="final_login_v3.css">
    <title>LOGIN</title>
</head>

<body style="text-align:center; margin:75px;">
  <canvas id="myCanvas">
  </canvas>
        
    
    <div>
        <h1>Login to our Class Site</h1>
        <div id = "bottomDiv">
        <form action="login_student.php" method="post">
            <input type="text" id="username" name="username" placeholder="Username" />
            <input type="password" id="password" name="password" placeholder="Password" />
            <button type="submit">Login</button>
        </form>
        </div>
        <?php echo "<br>" . $message; ?>
        <br><br>
        <a href="adduser.php">Register if you do not already have an account</a>
    </div>
    
    
    
    <!--math is fun canvas -->
    <script>
    var canv=document.getElementById("myCanvas");
    var c=canv.getContext("2d");
    var h = canv.height;
    var w = canv.width;


    c.beginPath();
    c.lineWidth=3;
    c.strokeStyle="Blue";
    c.moveTo(w/2,h/2-100);
    c.lineTo(w/2-w/7-100,h/2);
    c.lineTo(w/2+w/7-100,h/2);
    c.lineTo(w/2-100,h/2-100);
    c.fillStyle="Sienna";
    c.closePath();
    c.fill();
        
    c.font = "30px Arial";
    c.fillStyle = "blue";
    c.textAlign = "center";
    c.fillText("Math is Fun!",w/2+h/5,h/2);    
    </script>
    
    
    
    <a href="index.html">Home</a>
</body>
</html>
