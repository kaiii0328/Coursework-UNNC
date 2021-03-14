import java.io.*;
import java.util.*;
interface IReadFile{
	void read() throws IOException;		
}
class ReadFile {
	String str=null;
	String[] input=null;
	ArrayList<String> words= new ArrayList<String>(10);
	ArrayList<Integer> count = new ArrayList<Integer>(10);
	ArrayList<String> sen_words = new ArrayList<String>(10);
	ArrayList<Integer> sen_count = new ArrayList<Integer>(10);
	
	
	class inner implements IReadFile{
	public  void read() throws IOException{  
		try (BufferedReader br = new BufferedReader(new FileReader("Test.txt"))){	//use a try-with syntax to handle unexpected IOException
			while ((str=br.readLine())!=null) {					
				input = str.trim().split("[,. ]+");		
				if (!str.equals("")) {
					for (String temp : input) {
						boolean flag1=false;
						boolean flag2 = false;
						for (int index=0;index < words.size();index++) {																	
							 if (words.get(index).equalsIgnoreCase(temp)) {									
									int num=(int) count.get(index);
									num++;
									count.set(index, (Integer)num);
									flag1 = true;
									break;
							 }							
						}
						if (!flag1) {
							words.add(temp);
							count.add(1);
						}
						for (int index=0;index < sen_words.size();index++) {																	
							 if (sen_words.get(index).equals(temp)) {									
									int num=(int) sen_count.get(index);
									num++;
									sen_count.set(index, (Integer)num);
									flag2 = true;
									break;
							 }							
						}
						if (!flag2) {
							sen_words.add(temp);
							sen_count.add(1);
						}
					}
				}		
			}		
		}
	}
	public  void read(String filename) throws IOException{  
		// polymorphism : if no argument is passed to read() method,it will read Test.txt by default,
		//else it will read the corresponding file by using the passed filename 
		try (BufferedReader br = new BufferedReader(new FileReader(filename))){	//use a try-with syntax to handle unexpected IOException
			while ((str=br.readLine())!=null) {					
				input = str.trim().split("[,. ]+");		
				if (!str.equals("")) {
					for (String temp : input) {
						boolean flag1=false;
						boolean flag2 = false;
						for (int index=0;index < words.size();index++) {																	
							 if (words.get(index).equalsIgnoreCase(temp)) {									
									int num=(int) count.get(index);
									num++;
									count.set(index, (Integer)num);
									flag1 = true;
									break;
							 }							
						}
						if (!flag1) {
							words.add(temp);
							count.add(1);
						}
						for (int index=0;index < sen_words.size();index++) {																	
							 if (sen_words.get(index).equals(temp)) {									
									int num=(int) sen_count.get(index);
									num++;
									sen_count.set(index, (Integer)num);
									flag2 = true;
									break;
							 }							
						}
						if (!flag2) {
							sen_words.add(temp);
							sen_count.add(1);
						}
					}
				}		
			}
		}
	}
	public void getMostFrequent() {
		int max=0;
		int i=0;
		for (int index=0;index <count.size();index++) {
			if (count.get(index)>max) {
				max = count.get(index);
				i =index;
			}			
		}
		System.out.println(words.get(i)+ " " + count.get(i));
	}
	public void getOnlyOnce() {
		ArrayList<Integer> one = new ArrayList<Integer>();
		for (int index =0;index<sen_count.size();index++) {
			if (sen_count.get(index)==1)
				one.add(index);
		}
		for (int index =0;index<one.size();index++) {
			System.out.print(sen_words.get(one.get(index))+ " ");
		}
		System.out.println(" ");
	}
	}
	
}
public class AssessedCW1 {
	public static void main(String[] args) throws IOException {
		ReadFile outer = new ReadFile();
		ReadFile.inner rf = outer.new inner();	//use inner class
		rf.read();
		rf.getMostFrequent();
		rf.getOnlyOnce();
	}
}
