/*
reading and writting file
*/
//Anne Schwartz
//this program writes all the information out to an outside source so it can be accessed the next 
//time the user opens the program

import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.*;

public class ReadnWrite{
	public int max_ideaNum;

	public void ReadnWrite(){
	}
	public IdeaNode readDeleted(String deletedIdeas, int num){
		try{
			FileReader readerA = new FileReader(deletedIdeas);
			Scanner in = new Scanner(readerA);
			while (in.hasNextLine()) {
				String inputLine = in.nextLine();
				String[] idealine = inputLine.split(",");
				int ideaNumber = Integer.parseInt(idealine[2]);
				int ssn = Integer.parseInt(idealine[0]);
				int rating = Integer.parseInt(idealine[1]);
				String idea = idealine[3];
				if (ideaNumber == num){
					readerA.close();
					return(new IdeaNode(ssn, rating, ideaNumber, idea));
				}
			}
			return null;
		}
		catch (IOException e){
			return null;
		}
	} 
	public void read(String studentFile, String ideasFile){
		try{
			FileReader reader = new FileReader(studentFile) ;
			
			FileReader reader1 = new FileReader(ideasFile);
			
			Scanner in = new Scanner(reader);
			Scanner in1 = new Scanner(reader1);
			
			while (in1.hasNextLine()) {
				String inputLine1 = in1.nextLine();
				String[] idealine= inputLine1.split(",");
				String idea = idealine[3];
				int idearating = Integer.parseInt(idealine[1]);
				int ideaNumber = Integer.parseInt(idealine[2]);
				int ssn = Integer.parseInt(idealine[0]);
				IdeaNode b = new IdeaNode(ssn, idearating, ideaNumber, idea);
				Intro.priorityQueue.insertPQ(b);
				if (ideaNumber>max_ideaNum){
					max_ideaNum = ideaNumber;
				}
			}
			
			while (in.hasNextLine()) {
				String inputLine = in.nextLine();
				String[] student = inputLine.split(",");
				String[] identity = student[0].split(" ");
				String[] studentIdeas = student[1].split(" ");
				String name = identity[0];
				String email = identity[1];
				int ssn = Integer.parseInt(identity[2]);
				int sn = Integer.parseInt(identity[3]);
				int average = Integer.parseInt(identity[4]);
				StudentNode a = new StudentNode(name, email, ssn, sn);
				Intro.database.insert(a);
				a.setWholeAverage(average);
				for (int i = 0;i<(studentIdeas.length);i++){
					if(!studentIdeas[i].equals("")) {
						IdeaNode Node = searchIdeaNode(Integer.parseInt(studentIdeas[i]));
						a.getQueueLL().enqueue(Node);
					}
				}
			}
			
			reader.close();
			reader1.close();
		}
		catch (IOException e){
		}
	}
	public IdeaNode searchIdeaNode(int ideaNum){
		int i = 0;
		IdeaNode temp = Intro.priorityQueue.ideaPriorityQueue[i];
		while (temp != null) {
			if (temp.getIdeaNumber() == ideaNum)
				return temp;
			else {
				temp = Intro.priorityQueue.ideaPriorityQueue[i];
				i = i+1;
			}
		}
		return readDeleted("Deleted.txt", ideaNum);
	}
	public void write(String outLine, PrintWriter out){
		out.println(outLine);
		
	}
	public void writeDeletedNodes(IdeaNode x, String fileName){
		String x1 = (String.valueOf(x.getSSN())+","+ String.valueOf(x.getRating())+","+String.valueOf(x.getIdeaNumber())+","+ x.getIdea());
		try{
			PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
			write(x1,out1);
			out1.close();
		}
		catch(IOException e){
		}
	}
	public void writeNodes(IdeaNode[] x, String fileName){
		try{
			PrintWriter out = new PrintWriter(fileName);
			for (int i = 0;i<x.length;i++){
				if (x[i]==null){
					break;
				}
				else{
					write(String.valueOf(x[i].getSSN())+","+ String.valueOf(x[i].getRating())+","+String.valueOf(x[i].getIdeaNumber())+","+ x[i].getIdea(), out);
				}
			}
			out.close();
		}
		catch (IOException e){
		}
	}
   //methods to print the contents of the tree in order using recursion
  //method that calls traverse2 if tree isn't empty
	public void writeStudents(StudentNode headBST, String fileName){
		try{
			PrintWriter out = new PrintWriter(fileName);
		if (headBST!= null){
			traverse2(headBST, out);
		}
		out.close();
		}
		catch (IOException e){
		}
	}
  //method to recursively go through the tree and print out values in order
	public void traverse2(StudentNode t, PrintWriter out){
		if (t != null) {
			//print as go through tree so that values are not added in order
			//this way when entered back in, info will not be entered in order which would result in an ordered LL
			write(t.getLastName() + " "+t.getEmail() + " "+String.valueOf(t.getSSN()) +" "+String.valueOf(t.getStudentNumber()) +" "+  String.valueOf(t.getAverage() )+", "+t.getQueueLL().getQueueStr() , out);
			traverse2(t.getLeft(), out);
			traverse2(t.getRight(), out);
		}
	}	
}
