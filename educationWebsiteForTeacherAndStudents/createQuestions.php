<?php
	session_start();           // Start new or resume existing session
	if(!isset($_SESSION['active'])){
		header("Location: login_staff.php");  //  back to login page
	}
	$db_conn = mysqli_connect("localhost", "root", "");
	mysqli_select_db($db_conn, "school_db");
?>


<!DOCTYPE html>
<!-- Allows the teacher to create a new assignment
-->

<html>
<head>
    
    <link rel="stylesheet" href="questions.css">   

</head>
    
<body>
    <!-- teacher can create new assignments or choose existing one -->
    <div id = "header">
     Creating Assignments
    </div>
    <div id = "middle">
    <div id = "text">
    Enter the name of a new assignment or select an existing assignment in the side menu.
    </div>
   <form action="createQuestions.php" method="post">
        <input type="text" id="new" name="new" placeholder="Assignment Name" size='25%' height='50'/>
        <button type="submit" class = "button" value = "newAssignment" name = "button"> Create a new assignment </button>
    </form>
    </div>
    
    <div>
<?php
        $db_conn = mysqli_connect("localhost", "root", "");
        mysqli_select_db($db_conn, "assignments_db");

        $cmd = "SELECT *
                    FROM assignmentNames;";
            $records = mysqli_query($db_conn, $cmd);
            echo "<br> <br> If you wish to add questions to an assignment that you have already created, click the assignment name below:<br> <br>";
      
//drop down of all existing assignments
            echo "<select name = 'assign' id='assign' style='font-size:15px'>";
            while ($row = mysqli_fetch_array($records)){
                $name = $row["name"]; 
                echo "<option>".$name."</option>";
            }



        if( !empty($_POST["new"]) ){
            $assignment = mysqli_real_escape_string($db_conn, $_POST['new']);
            $cmd = "CREATE TABLE IF NOT EXISTS $assignment ( id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
                                                question varchar(200) NOT NULL,
                                                image varchar(30) NULL,
                                                ans1 varchar(200) NOT NULL,
                                                ans2 varchar(200) NOT NULL,
                                                ans3 varchar(200) NOT NULL,
                                                ans4 varchar(200) NOT NULL,
                                                correct varchar(1) NOT NULL
                                                );";
            mysqli_query($db_conn, $cmd);
          
            
            $cmd = "INSERT INTO assignmentNames (name) VALUES ('$assignment');";
         
            if (mysqli_query($db_conn, $cmd)){
                echo "<option>".$assignment."</option>";
            }
        }
            
            echo"</select>";
            echo "
                <button class='button2' type='button' onClick='getData()'>Select</button>
                <article id='output'></article>";
    
        ?>
    </div>
    <!--pass assignment information to next page -->
        <script>
        function getData(){
            xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
                    document.getElementById("output").innerHTML = "xmlhttp.responseText";
            };
            var str = "createQuestions2.php?";
            str += "assign=" + document.getElementById("assign").value;
                        
            xmlhttp.open("GET",str,true);
            location.href = 'createQuestions2.php';
            xmlhttp.send();
        }
        </script>

     <?php
		if(isset($_SESSION['active']))
			echo "<br> <br> <br>You are logged in as: " . $_SESSION['active'] . "<br><br>";
		?>
		<br><a href="logout_staff.php">Logout</a> 
</body>
</html>