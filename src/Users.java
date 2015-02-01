import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Users {
	public static void main(String[] args){
		addUser("@dmin", "Flipp3rs");
	}
	public static boolean checkPwd(String user, String pwd){
		HashMap<String, String> users = readFile();
		if(users.get(user) == null){
			return false;
		}
		return BCrypt.checkpw(pwd, users.get(user));
	}
	/**
	 * Adds a User. If user already exists then it changes the password to the new password. 
	 * @param username
	 * @param pwd
	 */
	public static void addUser(String username, String pwd){
		HashMap<String, String> users = readFile();
		users.put(username, BCrypt.hashpw(pwd, BCrypt.gensalt()));
		writeToFile(users);
	}
	
	public static HashMap<String,String> readFile(){
		HashMap<String, String> users = new HashMap<String, String>();
		String csvFile = "/Users/vanvari/Minecraft/users.csv";
		BufferedReader br = null;

		try {
			 
			br = new BufferedReader(new FileReader(csvFile));
			String line = br.readLine();
			while ( line != null) {
				String[] user = line.split(",");
				users.put(user[0], user[1]);
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

		return users;
	}
	
	public static void writeToFile(HashMap<String, String> users){
		try
		{
			String location = "/Users/vanvari/Minecraft/users.csv";
		    FileWriter writer = new FileWriter(location);
	 
			for (Map.Entry<String, String> entry : users.entrySet()) {
				 writer.append(entry.getKey() + "," + entry.getValue() + "\n");	 
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
