//Anne Schwartz
//this program creates the priority queue which holds all the idea nodes in the database 
//even when it is no longer linked to a student

public class IdeaPriorityQueue{
  private int n;
  public IdeaNode [] ideaPriorityQueue;

  //constructor
  public IdeaPriorityQueue(){
    ideaPriorityQueue = new IdeaNode[128]; //array of maximum 128 IdeaNodes 
    n = 0;                  
  }                 

  //method to check if PQ empty
  public boolean isEmpty(){
    return (n == 0);
  }

  //method to return the key of the minimum IdeaNode (first IdeaNode in array)
  public IdeaNode findMin(){
    if (n != 0)
      return ideaPriorityQueue[0];
    else
      return null;
  }

  //method to insert a new IdeaNode and calls insertSort to sort the heap
  public void insertPQ(IdeaNode x){ 
    if (n < (ideaPriorityQueue.length - 29)) {
      ideaPriorityQueue[n] = x;
      insertSort(n,x);
      n = n+1;
    }
    
    else {  //at 100 Priority Queue Doubles
      IdeaNode [] newArray = new IdeaNode[ideaPriorityQueue.length *2];
      for (int i = 0; i<ideaPriorityQueue.length; i++)
        newArray[i] = ideaPriorityQueue[i];
      ideaPriorityQueue = newArray;
      insertPQ(x);
    } 
  } 

  //insert helper function to sort Priority Queue so children IdeaNodes larger than parents
  private void insertSort(int j, IdeaNode x){
    //index of parent of IdeaNode at index j
    int parentIndex = (j -1)/2;
    IdeaNode parent = ideaPriorityQueue[parentIndex];
    //if parent larger than child, sort
    if (parent.getRating() > x.getRating()) {
      ideaPriorityQueue[parentIndex] = x;
      ideaPriorityQueue[j] = parent; //swap parent and child
      //move j up to parent index
      j = parentIndex; 
      //recursively sort up the heap
      insertSort(j, x);
    }   
    //if parent is root or already sorted
    else
      return;
  }
  //method to delete the minimum IdeaNode
  public void deleteMin(){
    //if Priority Queue isn't empty
    if (n != 0) {
      n = n-1;
      //swap first and last IdeaNodes
      IdeaNode min = ideaPriorityQueue[0];
      IdeaNode last = ideaPriorityQueue[n];
      ideaPriorityQueue[0] = last;
      ideaPriorityQueue[n] = null;
      //sort ideaPriorityQueue
      deleteSort(0);
      System.out.println("The best idea has been deleted");
   }
   else
    System.out.print("Empty. Cannot delete.");
  }

  //delete helper function to sort heap so children IdeaNodes larger than parents
  private void deleteSort(int i){
    IdeaNode parent = ideaPriorityQueue[i];
    int index; //index will be used to keep track of the index of the child sorted
    //if left child is beyond end
    if (2*i+1 >= ideaPriorityQueue.length) 
      return;
    //if space for left child, but no space for right child
    else if (2*i+2 >= ideaPriorityQueue.length) {
        //check if child is smaller than parent and swap if it is
      if (ideaPriorityQueue[2*i+1] == null)
        return;
      else {
        if (ideaPriorityQueue[2*i+1].getRating() < parent.getRating()) {
          index = (2*i+1);
          IdeaNode smaller = ideaPriorityQueue[index];
          ideaPriorityQueue[i] = smaller;
          ideaPriorityQueue[index] = parent;
        }
        //if child not smaller than parent, all done
        else
          return;
      }
    }
    //still space for 2 child IdeaNodes
    else {
      //if both child IdeaNodes are null, done sorting
      if ((ideaPriorityQueue[2*i+1] == null) && (ideaPriorityQueue[2*i+2] == null))
        return;
      //if only right child IdeaNode
      else if (ideaPriorityQueue[2*i+1] == null) {
        //check if child is smaller than parent and swap if it is
        if (ideaPriorityQueue[2*i+2].getRating() < parent.getRating()) {
          index = (2*i+2);
          IdeaNode smaller = ideaPriorityQueue[index];
          ideaPriorityQueue[i] = smaller;
          ideaPriorityQueue[index] = parent;
          //sort recursively down the Priority Queue for Ideas
          deleteSort(index);  //index is new root of section to sort
        }
        //if child not smaller than parent, all done
        else
          return;
      }
      //if only left child IdeaNode
      else if (ideaPriorityQueue[2*i+2] == null) {
        //check if child is smaller than parent and swap if it is
        if (ideaPriorityQueue[2*i+1].getRating() < parent.getRating()) {
          index = (2*i+1);
          IdeaNode smaller = ideaPriorityQueue[index];
          ideaPriorityQueue[i] = smaller;
          ideaPriorityQueue[index] = parent;
          //sort recursively down the Priority Queue for Ideas
          deleteSort(index);  //index is new root of section to sort
        }
        //if child not smaller than parent, all done
        else
          return;
      }
      //if left and right child IdeaNodes, check if one or both are smaller than the parent
      else if ((parent.getRating() > ideaPriorityQueue[2*i+1].getRating()) || 
        (parent.getRating() > ideaPriorityQueue[2*i+2].getRating())) {
        //swap with smaller of the child IdeaNodes
        //if left smaller than right
        if (ideaPriorityQueue[2*i+1].getRating() < ideaPriorityQueue[2*i+2].getRating()) {
          index = (2*i+1);
          IdeaNode smaller = ideaPriorityQueue[index];
          ideaPriorityQueue[i] = smaller;
          ideaPriorityQueue[index] = parent;
        }
        //if right smaller than left
        else {
          index = (2*i+2);
          IdeaNode smaller = ideaPriorityQueue[index];
          ideaPriorityQueue[i] = smaller;
          ideaPriorityQueue[index] = parent;
        }
        //sort recursively down the heap
        deleteSort(index);  //index is new root of section to sort
      }
      else
        return;
    }
  }

  //method that loops through heap and prints keys
  public void printPriorityQueue(){
    System.out.println(n);
    if (n != 0) {
      for(int i = 0; i <= n-1; i++) { //loop through and print keys
        System.out.print(ideaPriorityQueue[i].getRating()+" ");
      }
    }
    else {
      System.out.print("Empty");
    }  
    System.out.println();
  }
}
