/*Anne Schwartz 
//this creates the node for each student that holds the student's information
4/27/14
StudentNode.java
student node class
Final Project */

public class StudentNode{

  private String lastName; //declaring all my variables for class
  private int ssn;
  private StudentNode right;
  private StudentNode left;
  private StudentNode next;
  public QueueLL queue;
  private String email;
  private int studentNumber;
  private int average;
  private int total;
  private int numIdea;

  //initialize variables
  public StudentNode(String lastName0, String email0, int ssn0, int studentNumber0){
    lastName = lastName0;
    email = email0;
    ssn = ssn0;
    right = null;
    left = null;
    next = null;
    studentNumber = studentNumber0;
    average = 0;
    total = 0;
    numIdea = 0;
    queue = new QueueLL();
  }
  public void setLastName(String lastName0){
    lastName = lastName0;
  }
  public String getLastName(){
    return lastName;
  }
  public void setSSN(int ssn0){
    ssn = ssn0;
  }
  public int getSSN(){
    return ssn;
  }
  public void setLeft(StudentNode left0){
    left = left0;
  }
  public void setRight(StudentNode right0){
    right = right0;
  }
  public StudentNode getRight(){
    return right;
  }
  public StudentNode getLeft(){
    return left;
  }
  public void setNext(StudentNode next0){
    next = next0;
  }
  public StudentNode getNext(){
    return next;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email0){
    email = email0;
  }
  public void setStudentNumber(int studentNumber0){
    studentNumber = studentNumber0;
  }
  public int getStudentNumber(){
    return studentNumber;
  }
  public void setAverage(int newRating){
    average = ((total + newRating)/(numIdea+1));
    total = total + newRating;
    numIdea = numIdea + 1;
  }
  public void setWholeAverage(int average0){
    average = average0;
  }
  public int getAverage(){
    return average;
  }
  public QueueLL getQueueLL(){
    return queue;
  }
}
