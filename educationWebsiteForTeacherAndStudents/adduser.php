<!DOCTYPE html>
<!-- Demonstrates how to add a user to an existing database -->
<html>
<head>
        <link rel="stylesheet" href="final_login_v3.css">

	<title>Add User to MySQL Database</title>
	<style>
		div {margin: auto; width:50%; font: bold 16px verdana, sans-serif; }
	</style>
</head>

<body style="text-align:center; margin:75px;">
	<div>
		<?php
			$message = "";
			if( !empty($_POST["username"]) ){
				$db_conn = mysqli_connect("localhost", "root", "");
				if (!$db_conn)
					die("Unable to connect: " . mysqli_connect_error());  // die is similar to exit

				if( !mysqli_select_db($db_conn, "school_db") )
					die("Database doesn't exist: " . mysqli_error($db_conn));

				mysqli_select_db($db_conn, "school_db");
                
                $user = mysqli_real_escape_string($db_conn, $_POST['username']);
                $pass = mysqli_real_escape_string($db_conn, $_POST['password']);

				$cmd = "INSERT INTO students (username, password, email) VALUES ('"
				                . $user . "','" . $pass . "','"
								. $_POST['email'] . "');";

				if( mysqli_query($db_conn, $cmd) )
					$message = "You have been added to the database";
				else
					$message = "Problem creating your account: ". mysqli_error($db_conn) . "<br>";

				mysqli_close($db_conn);
			}
		?>
	</div>
    <h1>Register for our Class Site</h1>
    <div id = "bottomDiv">
	<form action="adduser.php" method="post">
		<input type="text" id="username" name="username" placeholder="Username" required /><br>
		<input type="password" id="password" name="password" placeholder="Password" required /><br>
		<input type="text" id="email" name="email" placeholder="Email" /><br>
		<button type="submit">Register</button>
	</form>
    </div>
	<?php echo "<br>" . $message . "<br><a href='login_student.php'>Login</a>" ?>
</body>
</html>
