//Anne Schwartz
//this creates the linked list queue that holds all 10 ideas for each student 

public class QueueLL{
  
  private IdeaNode head; //all needs to be initialized when we put program together
  private int m;
  private IdeaNode tail;

  public QueueLL(){
    head = null;
    tail = null;
    m = 0;
  } 
  public IdeaNode front(){
    if (m == 0)
      return null;
    else
      return head;
  }
  public IdeaNode dequeue(){   
    if (head == tail){
      return null;
    }
    else{
      IdeaNode temp;
      temp = head;
      head = head.getNext();
      temp.setNext(null);
      m = m-1;
      return temp;
    }
  }

  public void enqueue(IdeaNode idea){
    if (m == 0) {
      head = idea;
      tail = idea;
    }
    else if (m < 10) {
      tail.setNext(idea);
      tail = idea;
    }
    else {
      IdeaNode temp = head;
      head = head.getNext();
      temp.setNext(null);
      tail.setNext(idea);
    }
    m = m+1;
  }
  //method to print elements of queue
  public void printQueue() {
    IdeaNode temp = head;
    while (temp != null){  //loop through linked list
      System.out.println(temp.getIdea()); //print each node
      temp = temp.getNext(); //move temp to next node
    }
  }
  public String getQueueStr() {
    IdeaNode temp = head;
    String str = "";
    while (temp != null){  //loop through linked list
      str = str + " " +String.valueOf(temp.getIdeaNumber());
      temp = temp.getNext(); //move temp to next node
    }
    return str;
  }
}

