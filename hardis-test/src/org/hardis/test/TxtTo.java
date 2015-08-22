package org.hardis.test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Scanner;

public abstract class TxtTo {
	private static Charset ENCODING = StandardCharsets.UTF_8;
	String inPath;
	String outPath;
	
	
	public TxtTo(String inFile, String outFile){
		this.inPath = inFile;
		this.outPath = outFile;
	}
	
	public void parseFile() throws IOException{
		Path path = Paths.get(inPath);
		Scanner scanner = new Scanner(path, ENCODING.name());
		int i =1;
		while(scanner.hasNext()){
			processLine(scanner.nextLine(), i++);
		}
		
	}

	public String getInPath() {
		return inPath;
	}

	public void setInPath(String inPath) {
		this.inPath = inPath;
	}

	public String getOutPath() {
		return outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	public abstract void processLine(String line, int lineNumber);
	public abstract void write()throws Exception;
}
