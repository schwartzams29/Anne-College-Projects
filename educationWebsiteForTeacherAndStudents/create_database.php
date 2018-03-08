
<!DOCTYPE html>
<!-- Creates a school database using PHP 
     Run this first to prepare the database for student and staff login
     Staff login is created at this level because there is only one staff login and more should not be allowed to be made
        staff login: aschwar3, 1234

Create another database that holds tables for each assignment with the questions and possible answers
-->
<html>
<head>
	<title>Create MySQL Database</title>
</head>

<body style="text-align:center; margin:75px;">
	<?php
		$db_conn = mysqli_connect("localhost", "root", "");
		if (!$db_conn)
			die("Unable to connect: " . mysqli_connect_error());

    
    
        //school_db holds staff and students logins
		if (mysqli_query($db_conn, "CREATE DATABASE school_db;"))
			echo "Database ready<br>";
		else
			echo "Unable to create database: " . mysqli_error($db_conn) . "<br>";

		mysqli_select_db($db_conn, "school_db");
        
//    create students table with username, password and email
		$cmd = "CREATE TABLE IF NOT EXISTS students (
                                                    id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
                                                    username varchar(60) UNIQUE NOT NULL,
                                                    password varchar(60) NOT NULL,
                                                    email varchar(60)
                                                  );";

		if( mysqli_query($db_conn, $cmd) )
			echo "Table 'students' created<br>";
		else
			echo "Table not created: ". mysqli_error($db_conn) . "<br>";
    
    //    create staff table with username and password --> insert the one staff login to this table

        $cmd = "CREATE TABLE IF NOT EXISTS staff (
                                                        id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
                                                        username varchar(60) UNIQUE NOT NULL,
                                                        password varchar(60) NOT NULL
                                                      );";

		if( mysqli_query($db_conn, $cmd) )
			echo "Table 'staff' created<br>";
		else
			echo "Table not created: ". mysqli_error($db_conn) . "<br>";
    
        $cmd = "INSERT INTO staff (username, password) VALUES ('aschwar3', '1234');";
        if( mysqli_query($db_conn, $cmd) )
            $message = "You have been added to the database";
        else
            $message = "Problem creating your account: ". mysqli_error($db_conn) . "<br>";
    
    
        //assignments_db database will hold a table for each assignment

		if (mysqli_query($db_conn, "CREATE DATABASE assignments_db;"))
			echo "Database ready<br>";
		else
			echo "Unable to create database: " . mysqli_error($db_conn) . "<br>";
    
        mysqli_select_db($db_conn, "assignments_db");

    
    // create list of all assignment names
        $cmd = "CREATE TABLE IF NOT EXISTS assignmentNames (
                                                        id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
                                                        name varchar(60) UNIQUE NOT NULL
                                                        );";

		if( mysqli_query($db_conn, $cmd) )
			echo "Table 'assignmentNames' created<br>";
		else
			echo "Table not created: ". mysqli_error($db_conn) . "<br>";

    
		mysqli_close($db_conn);
	?>
</body>
</html>
