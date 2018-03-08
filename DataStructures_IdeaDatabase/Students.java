/*Anne Schwartz
4/28/14
Students.java
store student nodes in a hash table based on ssn and bst based on sn
Final Project */

public class Students{
  private int n;
  private StudentNode [] hash;
  public StudentNode headBST; //root of bst

  public Students(){
    n = 0;
    hash = new StudentNode[100];
    headBST = null;
  }

  //method to insert a node
  public void insert(StudentNode x){
    int index;
    int key = x.getSSN();
    //first hash is mod100
    index = (key%100);
    //insert node into hash
    insertHash(index, x);
    //insert node into BST
    if (headBST == null)
      headBST = x;
    else
      insertBST(headBST, x);
  }
  //recursive helper method to insert a node into chained hash
  private void insertHash(int index, StudentNode x){
    int key = x.getSSN();
    StudentNode temp;
    //if open spot in hash array -> insert
    if (hash[index] == null) {
      hash[index] = x;
      n = n+1;
    }
    //if index not empty but no linked list off of hash yet
    else if (hash[index].getNext() == null){
      temp = hash[index];
      int tempKey = temp.getSSN();
      if (tempKey < key) {
        temp.setNext(x);
        n = n+1;
      }
      else {
        hash[index] = x;
        hash[index].setNext(temp);
        n = n+1;
      }
    } 
    //if linked list off of hash array 
    else {
      StudentNode previous = null;
      temp = hash[index];
      int tempKey = temp.getSSN();
      //loop through until find spot where previous is less and temp is larger
      while (temp != null) {
        //move to next if greater than current temp
        if (key > tempKey) {
          previous = temp;
          temp = temp.getNext();
        }
        else {
          //if smaller than first
          if (previous == null) {
            StudentNode next = temp.getNext();
            hash[index] = x;
            hash[index].setNext(temp);
            temp.setNext(next);
            return;
          }
          else {
            previous.setNext(x);
            x.setNext(temp);
            return;
          }
        }
      }
      //if get to end and haven't inserted yet
      temp.setNext(x);
      n = n+1;
    }
  }

  //recursive helper method to insert node into BST
  private void insertBST(StudentNode t, StudentNode x){
    //smaller than parent -> move left
    if (x.getStudentNumber() < t.getStudentNumber()) {
      if (t.getLeft() == null)
        t.setLeft(x);
      else
        insertBST(t.getLeft(), x);
    }
    //larger than parent -> move right
    else {
      if (t.getRight() == null)
        t.setRight(x);
      else
        insertBST(t.getRight(),x);
    }
  }

  //search for student based on SSN
  public StudentNode lookUpSSN(int key){
    int index;
    //first hash is mod7
    index = (key%100);
    if (hash[index] == null) {
      return null;
    }
    //found in hash array
    else if (hash[index].getSSN() == key) {
      return hash[index];
    }
    //look in linked list off hash array
    else
      return lookUpSSN1(index, key);
  }
  //helper method that checks for node with ssn
  private StudentNode lookUpSSN1(int index, int key){
    //if node in index is not null, can check if correct node
    StudentNode temp = hash[index];
    while (temp.getSSN() != key) {
      //past where node with SSN would be
      if (temp.getSSN() > key) {
        return null;
      }
      //if no more nodes to check
      else if (temp.getNext() == null) {
        return null;
      }
      else {
        temp = temp.getNext();
      }
    }
    //when found node with key
    return temp;
  }

  //methods to search for a node containing the key 
  //(returns null if node doesn't exist)
  //check if t is null and initialize recursion
  public StudentNode lookUpStudentNumber(int key){
    if (headBST == null) {
      System.out.print("No student with that student number.");
      return null;
    }
    else  //if not empty, use search2 method 
      return lookUpStudentNumber1(headBST, key);
  }
  //method to recursively check for node if tree isn't empty
  private StudentNode lookUpStudentNumber1(StudentNode t, int key){
    //found node
    if (key == t.getStudentNumber()) {
      System.out.print("Email Login: " + t.getEmail());
      return t;
    }
    //if key is larger than one searching for, move left
    else if (key < t.getStudentNumber()) {
      if (t.getLeft() == null) {
        System.out.print("No student with that student number.");
        return null;
      }
      else
        return lookUpStudentNumber1(t.getLeft(), key);
    }
    //if key is smaller than one searching for, move right
    else {
      if (t.getRight() == null) {
        System.out.print("No student with that student number.");
        return null;
      }
      else
        return lookUpStudentNumber1(t.getRight(), key);
    }
  }
    //methods to print the contents of the tree in order using recursion
  //method that calls traverse2 if tree isn't empty
  public void traverse(){
    if (headBST!= null)
      traverse2(headBST);
    else //print blank line if empty tree
      System.out.println();
    System.out.println(); //new line at end after printing out values
  }
  //method to recursively go through the tree and print out values in order
  public void traverse2(StudentNode t){
    if (t != null) {
      traverse2(t.getLeft());
      System.out.println("Student Number: " + t.getStudentNumber() +" "+ "SSN: " + t.getSSN() +" "+ "Name: " + t.getLastName() + " "+ "Average Rating: " + t.getAverage());
      traverse2(t.getRight());
    }
  }
  public void delete(StudentNode t){
    if (n == 0)
      return;
    else {
      deleteHash(t);
      deleteBST(t);
    }
  }
  private void deleteHash(StudentNode t){
    int key = t.getSSN();
    int index = (key%100);
    if (hash[index] == t) {
      StudentNode temp = t.getNext();
      hash[index] = temp;
      n = n-1;
    }
    else {
      StudentNode prev = hash[index];
      while (prev.getNext() != t);
        prev = prev.getNext();
      prev.setNext(t.getNext());
      t.setNext(null);
      n = n-1;
    }
  }

  //method to find successor (next number numerically after node to delete)
  //this is a helper funtion for the delete function
  private StudentNode successor(StudentNode p){
    StudentNode temp = null;
    if (p.getRight() != null) {
      temp = p.getRight(); //go right one 
      //and then left all the way to get to successor
      while (temp.getLeft() != null)
        temp = temp.getLeft();
    }
    return temp;
  }
  //helper method for delete method to find the parent node
    //method is initially called with t as the root
  public StudentNode findParent(StudentNode p) {
    return findParent(headBST, p);
  }
  //recursive funtion to find parent
  private StudentNode findParent(StudentNode temp, StudentNode p){
    //if p is the root (t) there is no parent
    if (p == headBST)
      return null;
    int key = p.getStudentNumber();
    //if child of temp is p, temp is the parent
    if ((temp.getLeft() == p) || (temp.getRight() == p)) 
      return temp;
    //if the key of temp is larger, move to left
    else if (key < temp.getStudentNumber())
      return findParent(temp.getLeft(), p);
    //if the key of temp is larger, move to right
    else
      return findParent(temp.getRight(), p);
  }
  //method to delete the given node, p (multiple situations to consider)
  public void deleteBST(StudentNode p){
    StudentNode parent = findParent(p);
    StudentNode leftChild = p.getLeft();
    // If node is a leaf, don't need to rearrange anything
    if (p.getLeft() == null && p.getRight() == null) {
      // If node is root, tree is now empty
      if (p == headBST) {
        headBST = null;
        return;
      }
      //if p is right child, set parent's right pointer to null
      if (parent.getRight() == p)
        parent.setRight(null);
      //if p is right child, set parent's right pointer to null
      else
        parent.setLeft(null);
    }
    //if node has no right child, no successor to find
    else if (p.getRight() == null) {
      // if p isn't the root (there is a parent)
      if (parent != null) {
        //if p is the right child, left child of p is now right child of parent
        if (parent.getRight() == p)
          parent.setRight(leftChild);
        //if p is the left child, left child of p is now left child of parent
        else
          parent.setLeft(leftChild);
      }
      //if p is the root, left child becomes new root
        //(no right children of p to worry about)
      else
        headBST = leftChild;
    }
    //if node has a right child, there is a successor
    else {
      StudentNode successorNode = successor(p);
      StudentNode successorParent = findParent(successorNode);
      // Check if node is root
      //if node isn't root
      if (p != headBST) {
        //if p is the right child, set successor to right child of parent
        if (parent.getRight() == p)
          parent.setRight(successorNode);
        //if p is the left child, set successor to left child of parent
        else
          parent.setLeft(successorNode);
      } 
      //if node is the root
      else
        //set root to successor node
        headBST = successorNode;

      //re-connect nodes after connecting parent of p to the successor
      //if successor is not immediate right child of node being deleted
        //(nodes between p and successor to re-connect)
      if (successorParent != p) {
        //right children of successor set to left of successor parent
        successorParent.setLeft(successorNode.getRight());
        //right child of p set to right of the successor
        successorNode.setRight(p.getRight());
      }
      //set left child of p to left of successor
      successorNode.setLeft(p.getLeft());
    }
  }
}