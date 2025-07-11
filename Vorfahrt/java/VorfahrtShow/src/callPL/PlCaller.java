package callPL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlCaller {

	public static ArrayList<String> callPL() {
		String[] command = new String[] {"C:\\Users\\karat\\Documents\\code\\eclipse-workspace\\Vorfahrt\\test1.bat"};
		ProcessBuilder pb = new ProcessBuilder(command);
		
		try {
			Process p = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = null;
			ArrayList<String>  res = new ArrayList<String>();
			while((s = reader.readLine()) != null) {
				res.add(s);
			}
			return res;
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
		
	}

}
