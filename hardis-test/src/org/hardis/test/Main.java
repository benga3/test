package org.hardis.test;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws Exception {
		TxtTo trans = null;
		String format = args[2];
		if("xml".equals(format)){
		trans = new TxtToXml(args[0],args[1]+".xml");
		}else if("json".equals(format)){
			trans = new TxtToJson(args[0],args[1]+".json");
			}else{
				System.out.println("le format de sortie est incorrect");
				System.exit(0);
			}
		try {
			trans.parseFile();
			trans.write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("Fin du programme");
	}

}
