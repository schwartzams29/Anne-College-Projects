//Anne Schwartz
//this program creates the idea nodes which holds the ideas when entered into the system and
//allows them to have the following functions

public class IdeaNode{

  private String idea; //declaring all the variables
  private int ideaNumber;
  private int ssn;
  private int ideaRating;
  private IdeaNode next;

 public IdeaNode(int ssn0, int ideaRating0, int ideaNumber0, String idea0){
    //initializing variables 
    ssn = ssn0;
    ideaRating = ideaRating0;
    idea = idea0;
    ideaNumber = ideaNumber0;
    next = null;
  }
  public void setNext(IdeaNode next0){
    next = next0;
  }
  public IdeaNode getNext(){
    return next;
  }
  public int getIdeaNumber(){
    //get Idea Number
    return ideaNumber;
  }
  public void setIdeaNumber(int ideaNumber0){
    //set idea number
    ideaNumber = ideaNumber0;
  }
  public int getSSN(){
    //getSSN
    return ssn;
  }
  public void setSSN(int ssn0){
    //set SSN
    ssn = ssn0;
  }
  public void setIdea(String idea0){
  //set the idea
    idea = idea0;
  }
  public String getIdea(){
    return idea;
  }
  public void setRating(int ideaRating0){
     //set rating
     ideaRating = ideaRating0;
  }
  public int getRating(){
    //get rating
     return ideaRating;
  }
}

