package org.hardis.test;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TxtToJson extends TxtTo {
	
	JSONObject root;
	JSONArray refs;
	JSONArray errors;
	
	public TxtToJson(String file, String outFile) {
		super(file, outFile);
		root = new JSONObject();
		errors = new JSONArray();
		try {
			root.put("inputFile", file);
			refs = new JSONArray();
			root.put("references", refs);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void processLine(String line, int lineNumber) {
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(";");
		
		while (scanner.hasNext()) {
			JSONObject ref = new JSONObject();
			try {
				ref.put("numReference", scanner.next());
				String color = scanner.next();
				if("R".endsWith(color) || "G".equals(color) || "B".equals(color)){
					ref.put("color", color);
				}else{
					JSONObject error = new JSONObject();
					error.put("line", lineNumber);
					error.put("message", "Incorrect value for color");
					error.put("value", line);
					ref = null;
					errors.put(error);
					break;
				}
				ref.put("price", scanner.next());
				ref.put("size", scanner.next());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(ref != null){
				refs.put(ref);
			}
			
		}

	}

	@Override
	public void write()throws Exception {
		// TODO Auto-generated method stub
		root.put("errors", errors);
		File jsonFile=new File(getOutPath());  
        jsonFile.createNewFile();  
        FileWriter fileWriter = new FileWriter(jsonFile); 
        fileWriter.write(root.toString());  
        fileWriter.flush();  
        fileWriter.close(); 

	}

}
