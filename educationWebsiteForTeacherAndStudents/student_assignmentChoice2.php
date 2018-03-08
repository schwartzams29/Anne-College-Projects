<!-- student sees questions for assignment and answers them, with feedback on whether correct or not.
Continue answering until correct then can choose next -->
<!DOCTYPE html>

<html>
<head>
    
    <link rel="stylesheet" href="questions_v1.css">   
    <script type="text/javascript">
        function correctAns(){ 
            alert ("Correct!");
      }
        function incorrectAns(){
            alert ("That's incorrect.  Try again!");
        }
    </script>

<?php
   
        if( !empty($_GET["assign"]) ){
            $assignment = $_GET["assign"];
            session_start();
            $_SESSION['assign'] = $assignment;
        }
    //check answer, move on if correct, reload same question if incorrect
        if( isset($_POST["counter"]) ){
            $i = $_POST["counter"];
            if ($_POST["ans"] == $_POST["correct"]){
                echo '<script> correctAns(); </script>';
                $i = $i+1;  
            }
            else{
                echo '<script> incorrectAns(); </script>';

            }
        }
        
        ?>
</head>
    
<body>
    <div id = "header">
        The Assignment
    </div>
    <div id = "middle">
    <?php
        $db_conn = mysqli_connect("localhost", "root", "");
        if (!$db_conn)
            die("Unable to connect: " . mysqli_connect_error());  // die is similar to exit

        if( !mysqli_select_db($db_conn, "assignments_db") )
            die("Database doesn't exist: " . mysqli_error($db_conn));

        mysqli_select_db($db_conn, "assignments_db");
        session_start();
        $assignment = $_SESSION['assign'];
        $cmd = "SELECT * FROM `$assignment`;";
        $records = mysqli_query($db_conn, $cmd);
        $questionArray = array();
        $ans1Array = array();
        $ans2Array = array();
        $ans3Array = array();
        $ans4Array = array();
        $imageArray = array();
        $correctArray = array();
        while($row = mysqli_fetch_array($records)){
            array_push($questionArray, $row['question'] );
            array_push($ans1Array, $row['ans1'] );
            array_push($ans2Array, $row['ans2'] );
            array_push($ans3Array, $row['ans3'] );
            array_push($ans4Array, $row['ans4'] );
            array_push($imageArray, $row['image'] );
            array_push($correctArray, $row['correct'] );
        }
        $max = sizeof($questionArray);

        if( !isset($_POST["counter"]) ){
            $i = 0;
        }
        
        
        if ($i >= $max){
            echo "<div id = 'text'> Congratulations!  You have completed this assignment! </div>";
        }
        else{
            if (!empty($imageArray[$i])){
                $image = 'figures/'.$imageArray[$i];
                echo $questionArray[$i];
                echo "<br> <img id = 'image' src=$image>";                
            }
            else{
                echo $questionArray[$i];
                echo "<br><br>";
            }
            
                echo " <form action='student_assignmentChoice2.php' method='post'>
                  <input type='hidden' name='counter' value=$i>
                 <input type='hidden' name='correct' value=$correctArray[$i]>
                  <input type='radio' name='ans' value='a'> $ans1Array[$i]<br>
                  <input type='radio' name='ans' value='b'> $ans2Array[$i]<br>
                  <input type='radio' name='ans' value='c'> $ans3Array[$i]<br>
                  <input type='radio' name='ans' value='d'> $ans4Array[$i]<br> <br> <br> <br>
                  <input type='submit' class = 'button' value = 'Check'>
                </form>";
        }

    
    
    ?>
    </div>

    <br>
        <a href="student_assigmentChoice.php">Change Assignment</a>
    <br><a href="logout_student.php">Logout</a> 


    
    
</body>
</html>
