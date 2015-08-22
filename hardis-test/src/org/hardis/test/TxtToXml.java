package org.hardis.test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TxtToXml extends TxtTo {
	
	public DocumentBuilderFactory factory ;
	public DocumentBuilder builder ;
	public Document document;
	public Element root;
	public Element errors;
	
	public TxtToXml(String file, String outFile) {
		super(file, outFile);
		factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document = builder.newDocument();
		root = document.createElement("report");
		errors = document.createElement("errors");
		Element input = document.createElement("inputFile");
		input.setTextContent(file);
		root.appendChild(input);
		document.appendChild(root);
	}
	
	@Override
	public void processLine(String line, int lineNumber) {
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(";");
		Element ref = document.createElement("reference");
		while(scanner.hasNext()){
			
			ref.setAttribute("numReference", scanner.next());
			String color = scanner.next();
			if("R".endsWith(color) || "G".equals(color) || "B".equals(color)){
				ref.setAttribute("color", color);
			}else{
				Element error = document.createElement("error");
				error.setAttribute("line", String.valueOf(lineNumber));
				error.setAttribute("message", "Incorrect value of Color");
				error.setTextContent(line);
				errors.appendChild(error);
				ref = null;
				break;
			}
			ref.setAttribute("price", scanner.next());
			ref.setAttribute("size", scanner.next());
			
		}
		if(ref!=null){
		root.appendChild(ref);}
		
	}
	
	public void write() throws TransformerConfigurationException, TransformerFactoryConfigurationError{
		root.appendChild(errors);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
        DOMSource source = new DOMSource(document);
        File output = Paths.get(getOutPath()).toFile();
        StreamResult result = new StreamResult(output);
        try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
