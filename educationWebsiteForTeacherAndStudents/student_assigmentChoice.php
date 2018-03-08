<?php
	session_start();           // Start new or resume existing session
	if(!isset($_SESSION['activeStudent'])){
		header("Location: login_student.php");  //  back to login page
	}
	$db_conn = mysqli_connect("localhost", "root", "");
	mysqli_select_db($db_conn, "school_db");
?>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="questions.css">   
</head>

<body>
        <div id = "header">
        Assignments For Students
        </div>
    
        <div id = "middle">
                <div id = "text">
                    Select an assignment to complete:
                </div>
		<?php
        $db_conn = mysqli_connect("localhost", "root", "");
        mysqli_select_db($db_conn, "assignments_db");

        $cmd = "SELECT *
                    FROM assignmentNames;";
        $records = mysqli_query($db_conn, $cmd);
        //drop down of all existing assignments
            echo "<select name = 'assign' id='assign' style='font-size:15px'>";
            $count = 0;
            while ($row = mysqli_fetch_array($records)){
                $count = $count+1;
                $name = $row["name"]; 
                echo "<option>".$name."</option>";
            }
            echo " </select>";
            if ($count == 0){
                echo "<br> <br>No assignments yet!";
            }
            else {
                echo "<button class='button2' type='button' onClick='getData()'>Select</button>";
                echo "<article id='output'></article>";
            }

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
            var str = "student_assignmentChoice2.php?";
            str += "assign=" + document.getElementById("assign").value;
                        
            xmlhttp.open("GET",str,true);
            location.href = 'student_assignmentChoice2.php';
            xmlhttp.send();
        }
        </script>
    
    
	   <?php
		if(isset($_SESSION['activeStudent']))
			echo "<br> <br> You are logged in as: " . $_SESSION['activeStudent'] . "<br>";
        ?>
    
		<br><a href="logout_student.php">Logout</a>
</body>
</html>
