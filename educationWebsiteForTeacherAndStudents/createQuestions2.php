<?php
        if( !empty($_GET["assign"]) ){
            $assignment = $_GET["assign"];
            session_start();
            $_SESSION['assign'] = $assignment;
        }
        if( !empty($_POST["question"]) ){
				$db_conn = mysqli_connect("localhost", "root", "");
				if (!$db_conn)
					die("Unable to connect: " . mysqli_connect_error());  // die is similar to exit

				if( !mysqli_select_db($db_conn, "assignments_db") )
					die("Database doesn't exist: " . mysqli_error($db_conn));

				mysqli_select_db($db_conn, "assignments_db");
                
                $question = mysqli_real_escape_string($db_conn, $_POST['question']);
                $pic = mysqli_real_escape_string($db_conn, $_POST['pic']);

                $ans1 = mysqli_real_escape_string($db_conn, $_POST['ans1']);
                $ans2 = mysqli_real_escape_string($db_conn, $_POST['ans2']);
                $ans3 = mysqli_real_escape_string($db_conn, $_POST['ans3']);
                $ans4 = mysqli_real_escape_string($db_conn, $_POST['ans4']);
            
                $correct = mysqli_real_escape_string($db_conn, $_POST['correct']);
            
                session_start();
                $assignment = $_SESSION['assign'];

				$cmd = "INSERT INTO $assignment (question, image, ans1, ans2, ans3, ans4, correct) VALUES ('"
				                . $question . "','" . $pic . "','" . $ans1 . "','"
                                . $ans2 . "','" . $ans3 . "','" . $ans4 . "','" . $correct .
								 "');";

				if( mysqli_query($db_conn, $cmd) )
					$message = "Queestion has been added to the database";
				else
					$message = "Problem creating your account: ". mysqli_error($db_conn) . "<br>";

				mysqli_close($db_conn);
			}
        ?>


<!DOCTYPE html>
<!-- Allows the teacher to enter questions to enter into a database table that can then generate assignments
when students login
-->
<html>
<head>
    
    <link rel="stylesheet" href="questions.css">   
    
</head>
    
<body>
    <div id = "header">
     Creating Assignments
    </div>
    <div id = "middle">
    <div id = "text">
    Enter a question and 4 possible answers.  You can also enter a file name if there is an image you wish to display.
    </div>
    
    <form action="createQuestions2.php" method='post'>

    <br> <br> <br> <input type='text' id='question' name='question' placeholder='Question' size='50%' height='50' /> <br><br>
        <input type='text' id='pic' name='pic' placeholder='File Name of Image (Optional)' size='25%' height='50' /> <br><br>
        a) <input type='text' id='ans1' name='ans1' placeholder='Answer Option a' size='25%' height='50'/> <br><br>
        b) <input type='text' id='ans2' name='ans2' placeholder='Answer Option b' size='25%' height='50'/> <br><br>
        c) <input type='text' id='ans3' name='ans3' placeholder='Answer Option c' size='25%' height='50'/> <br><br>
        d) <input type='text' id='ans4' name='ans4' placeholder='Answer Option d' size='25%' height='50'/> <br> <br> 
        <input type='text' id='correct' name='correct' placeholder='Letter of Correct Answer (lowercase)' size='25%' height='50'/> <br> <br>
        <br><br>
        <input type='submit' class = 'button' value = 'Create'>
        
    </form>
    </div>
        <br> <br> 

    <br>
        <a href="createQuestions.php">Change Assignment</a>
    <br><a href="logout_staff.php">Logout</a> 


    
    
</body>
</html>
