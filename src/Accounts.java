import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Accounts {

	public static void main(String[] args) throws IOException {
		checkout(20,"@dmin");
	}
	public static ArrayList<String> getUserAccounts(String user)
	{

		ArrayList<String> ret = new ArrayList <String>();
		ArrayList<String[]> accts = readFile();
		for(String[] arr: accts)
		{
			if(arr[2].equals(user))
			{
				String addThis =  arr[0];
				ret.add(addThis);
				addThis = arr[1];
				ret.add(addThis);
			}

		}
		
		return ret;
	}
	public static String cleanOut()
	{
		ArrayList<String[]> accts = readFile();     
		for(String[] arr: accts)
		{ 
			arr[2] = "-";
		}
		writeToFile(accts);
		return "success";
	}
	public static String revoke(String[] rev)
	{
		int count=0;
		ArrayList<String[]> accts = readFile();     
		for(String[] arr: accts)
		{
			//System.out.println(Arrays.toString(arr)); // debug
			if(arr[0].equals(rev[count]))
			{
				arr[2] = "-";
				count++;
				if( count == rev.length)
				{break;}
			}

		}
		writeToFile(accts);

		return "success";
	}
	public static int howManyFree()
	{ 

		int count = 0;
		ArrayList<String[]> accts = readFile();     
		for(String[] arr: accts)
		{
			if(arr[2].equals("-"))
			{ 
				count++;
			}
		}
		return count;
	}

	public static String[] checkout(int num, String user)
	{
		int counter = 0;
		if(howManyFree() < num){
			//System.out.println("Error! Number unavilable. Number of accounts avaiable " + howManyFree());
		}
		else 
		{
			//System.out.println("yea, we have 'em");
		}

		String[] ret = new String[num];

		ArrayList<String[]> accts = readFile();     
		for(String[] arr: accts)
		{
			if(arr[2].equals("-"))
			{
				arr[2] = user;
				String addThis = "Email: " + arr[0] + " \nPW: " + arr[1];
				ret[counter] = addThis;
				counter++;
			}
			if(counter == num)
			{
				break;
			}

		}
		writeToFile(accts);

		return ret;
	}
	public static ArrayList<String[]> readFile(){
		ArrayList<String[]> accts = new ArrayList<String[]>();
		String csvFile = "/Users/vanvari/Minecraft/accts.csv";
		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader(csvFile));
			String line = br.readLine();
			while ( line != null) {
				String[] user = line.split(",");
				accts.add(user);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return accts;
	}

	public static void writeToFile(ArrayList<String[]> accts){
		try
		{
			String location = "/Users/vanvari/Minecraft/accts.csv";
			FileWriter writer = new FileWriter(location);

			for (String[] act: accts) {
				int i = 0;
				for(String str: act){

					writer.append(str);
					i++;
					if(i == 3){
						writer.append("\n");
					}
					else{
						writer.append(",");
					}
				}
			}

			writer.flush();
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		} 

	}

}
