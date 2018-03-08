//user interface
//Anne Schwatyz
//This is our main function which runs the menu that 
//gives the user the options of what to do.

import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Intro {
  private static int n = 0;
  public static Students database = new Students();
  public static IdeaPriorityQueue priorityQueue = new IdeaPriorityQueue();


  public static void main(String[] args){
    ReadnWrite r = new ReadnWrite();
    r.read("student.txt","nodes.txt") ;
    n = r.max_ideaNum;	  
    PrintWriter out=null;
    try{
      Scanner in = new Scanner(System.in);
      out = new PrintWriter("output.txt");
      readWrite(in,out);
    }
    catch(IOException e){
      System.out.println("Input/output error "+ e);
    }
    catch(NumberFormatException e){
      System.out.println("");
      System.out.println("The program is being haulted.");
      System.out.println("The last entry was not a valid function");
    }
    finally{
      out.close();
    }
  }
  public static void readWrite (Scanner in, PrintWriter out)
        throws java.io.IOException, NumberFormatException {
    printIntro();
    String inputLine1 = printOptions(in, out);
    options(inputLine1, in, out);
  }
  public static void printIntro(){
    System.out.println("**************************************************************");
    System.out.println("**    Hello! Welcome to your Student Idea Database!         **");
    System.out.println("**    Please select an option from following list below:    **");
    System.out.println("**************************************************************");
  }
  public static String printOptions(Scanner in, PrintWriter out){
    System.out.println();
    System.out.println("Add new student --> Type add");
    System.out.println("Look up student by SSN --> Type SSN");
    System.out.println("Look up student by student number --> Type student number");
    System.out.println("Print student info ordered by student number --> Type print");
    System.out.println("Get the best idea ---> Type best idea");
    System.out.println("Delete the best idea --> Type delete idea");
    System.out.println("Exit program --> Type exit");
    System.out.println();
    System.out.print("Please enter command: ");
    String inputLine = in.nextLine();
    return inputLine;
  }
  public static void options(String inputLine, Scanner in, PrintWriter out){
    if (inputLine.equals("add")|| inputLine.equals("Add")|| inputLine.equals("ADD")){
      add(in, out); //call method to add student
      String inputLine1 = printOptions(in, out);
      options(inputLine1, in, out);
    }
    else if (inputLine.equals("print")||inputLine.equals("Print")||inputLine.equals("PRINT")){
      printStudents(in, out);
    }
    else if (inputLine.equals("ssn")||inputLine.equals("SSN")){
      int ssn1 = ssnSearch(in, out);
      StudentNode nodessn = lookupSSNHelper(ssn1);
      printStudentNode(nodessn);
      optionsAfterSSNSearch(ssn1, nodessn, in, out);
      String inputLine1 = printOptions(in, out);
      options(inputLine1, in, out);
    }
    else if (inputLine.equals("student number")||inputLine.equals("Student Number")||inputLine.equals("STUDENT NUMBER")){
      studentNumberSearch(in, out);
      String inputLine1 = printOptions(in, out);
      options(inputLine1, in, out);
    }
    else if (inputLine.equals("best idea")||inputLine.equals("Best Idea")||inputLine.equals("BEST IDEA")||inputLine.equals("Best idea")){
      getBestIdea();
      String inputLine1 = printOptions(in, out);
      options(inputLine1, in, out);
    }
    else if (inputLine.equals("delete idea")||inputLine.equals("Delete Idea")||inputLine.equals("DELETE IDEA")||inputLine.equals("Delete idea")){
      ReadnWrite w1 = new ReadnWrite();
      IdeaNode deletedNode = priorityQueue.findMin();
	if (deletedNode == null){
		System.out.println("No ideas to delete.");
		String inputLine1 = printOptions(in, out);
		options(inputLine1, in, out);
	}
	else{
	      w1.writeDeletedNodes(deletedNode, "Deleted.txt");
	      deleteBestIdea();
	      String inputLine1 = printOptions(in, out);
	      options(inputLine1, in, out);
	}
    }
    else if (inputLine.equals("exit")||inputLine.equals("Exit")||inputLine.equals("EXIT")){
      ReadnWrite w = new ReadnWrite();
      w.writeStudents(database.headBST, "student.txt") ; 
      ReadnWrite n = new ReadnWrite();
      n.writeNodes( priorityQueue.ideaPriorityQueue, "nodes.txt");
      System.exit(0);
    }
    else
      warn(in, out);
  }
  public static void getBestIdea(){
    System.out.println();
    if (priorityQueue.findMin()!= null)
      System.out.println(priorityQueue.findMin().getIdea());
    else
      System.out.println("There are currently no saved ideas.");
  }
  public static void deleteBestIdea(){
    System.out.println();
    priorityQueue.deleteMin();
    System.out.println();
  }
  public static void add(Scanner in, PrintWriter out){
    System.out.println(" ");
    System.out.print("Please enter the student's last name: ");
    String lastName=in.nextLine();
    System.out.print("Email ID: ");
    String email=in.nextLine();
    System.out.print("SSN: ");
    int ssn=Integer.parseInt(in.nextLine());
    int lengthSSN = String.valueOf(ssn).length();
    while (lengthSSN != 4) {
	    System.out.print("Please enter a valid SSN: ");
	    ssn = Integer.parseInt(in.nextLine());
	    lengthSSN = String.valueOf(ssn).length();
    }
    System.out.print("Student Number: ");
    int studNumber=Integer.parseInt(in.nextLine());
    int lengthSN = String.valueOf(studNumber).length();
    while (lengthSN != 4) {
	    System.out.print("Please enter a valid student number: ");
	    studNumber = Integer.parseInt(in.nextLine());
	    lengthSN = String.valueOf(studNumber).length();
    }
    StudentNode newStudent= new StudentNode(lastName, email, ssn, studNumber);
    database.insert(newStudent);
    System.out.println();
    System.out.println("Student has been added!");
  }
  public static void printStudents(Scanner in, PrintWriter out){
    System.out.println();
    database.traverse();
    String inputLine1 = printOptions(in, out);
    options(inputLine1, in, out);
  }
  public static StudentNode lookupSSNHelper(int ssn){
    StudentNode studentSSNNode=database.lookUpSSN(ssn);
    return studentSSNNode;
  }
  public static void printStudentNode(StudentNode studentSSNNode){
    if (studentSSNNode == null)
      System.out.println("No student with that SSN.");
    else{
      System.out.println("Last Name: " + studentSSNNode.getLastName());
      System.out.println("Email Login Name: " + studentSSNNode.getEmail());
      System.out.println("SSN: " + studentSSNNode.getSSN());
      System.out.println("Average Score: " + studentSSNNode.getAverage());
    }
  }
  public static int ssnSearch(Scanner in, PrintWriter out){
    System.out.println();
    System.out.print("Please enter the Student's SSN to search for them: ");
    int ssn= Integer.parseInt(in.nextLine());
    System.out.println();
    return ssn;
  }
  public static void optionsAfterSSNSearch(int ssn, StudentNode studentSSNNode, Scanner in, PrintWriter out){
    if (studentSSNNode == null){
      return;
    }
    System.out.println(" ");
    System.out.println("Would you like to: ");
    System.out.println("Access student "+ssn+" last ten ideas --> Type ideas");
    System.out.println("Delete records --> Type delete");
    System.out.println("Modify name and email --> Type modify");
    System.out.println("Add ideas --> Type add idea");
    System.out.println("Return to main menu --> Type return");
    System.out.println("Exit--> Type exit");
    System.out.println();
    System.out.print("Please enter a command: ");
    String answerSearch = in.nextLine();

    if (answerSearch.equals("ideas")||answerSearch.equals("Ideas")||answerSearch.equals("IDEAS")){
      System.out.println();
      studentSSNNode.getQueueLL().printQueue();
      optionsAfterSSNSearch(ssn, studentSSNNode, in, out);
    }
    else if (answerSearch.equals("return")||answerSearch.equals("Return")||answerSearch.equals("RETURN")){
      String inputLine1 = printOptions(in, out);
      options(inputLine1, in, out);
    }
    else if (answerSearch.equals("delete")||answerSearch.equals("Delete")||answerSearch.equals("DELETE")){
      database.delete(studentSSNNode);
      System.out.println();
      System.out.print("Student record has been deleted.");
      System.out.println();
      optionsAfterSSNSearch(ssn, studentSSNNode, in, out);
    }
    else if (answerSearch.equals("modify")||answerSearch.equals("Modify")||answerSearch.equals("MODIFY")){
      System.out.println();
      System.out.print("Enter what would you like to change the last name to: ");
      String name = in.nextLine();
      int nameLength = name.length();
      if (nameLength == 0)
	      name = studentSSNNode.getLastName();
      System.out.println();
      System.out.print("Enter what would you like to change the email to: ");
      String email = in.nextLine();
      int emailLength = email.length();
      if (emailLength == 0)
        email = studentSSNNode.getEmail();
      studentSSNNode.setLastName(name);
      studentSSNNode.setEmail(email);
      optionsAfterSSNSearch(ssn, studentSSNNode, in, out);
    }
    else if (answerSearch.equals("add idea")||answerSearch.equals("Add Idea")||answerSearch.equals("ADD IDEA")||answerSearch.equals("Add idea")){
      System.out.println();
      System.out.print("Please enter student's idea: ");
      String idea = in.nextLine();
      System.out.print("Please enter the idea's rating: ");
      int rating = Integer.parseInt(in.nextLine());
      while (rating<0 || rating>100){
        System.out.print("Please enter a valid idea rating: ");
        rating = Integer.parseInt(in.nextLine());
      }
      n=n+1;
      IdeaNode newNode = new IdeaNode(studentSSNNode.getSSN(), rating, n, idea);
      priorityQueue.insertPQ(newNode);
      studentSSNNode.getQueueLL().enqueue(newNode);
      studentSSNNode.setAverage(rating);
      optionsAfterSSNSearch(ssn, studentSSNNode, in, out);
    }
    else if (answerSearch.equals("exit")||answerSearch.equals("Exit")||answerSearch.equals("EXIT")){
      ReadnWrite w = new ReadnWrite();
      w.writeStudents(database.headBST, "student.txt") ; 
      ReadnWrite n = new ReadnWrite();
      n.writeNodes( priorityQueue.ideaPriorityQueue , "nodes.txt");
      System.exit(0);
    }
    else{
      warn(in, out);
    }
  }
  public static void studentNumberSearch(Scanner in, PrintWriter out){
    System.out.println();
    System.out.print("Please enter the Student's student number: ");
    int studNumber=Integer.parseInt(in.nextLine());
    database.lookUpStudentNumber(studNumber);
    System.out.println(" ");
  }
  public static void warn(Scanner in, PrintWriter out){
    System.out.println("You did not enter a valid function");
    String inputLine1 = printOptions(in, out);
    options(inputLine1, in, out);
  }
}