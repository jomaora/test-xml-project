package com.jomaora;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 */
public class UsingXPath 
{
    public static void main( String[] args ) throws ParserConfigurationException, IOException, SAXException
    {
        File file = new File("test.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        gettingBooksInformation(doc);
    }
    
    public static void gettingBooksInformation(Document xml){
        String countBooks = "count(/library/descendant::book)";
        String getBooks = "/library/descendant::book";
        String getAuthorWithId = "/library/descendant::author[@id = ";

        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression expression = xPath.compile(countBooks);
            Number countNodes = (Number) expression.evaluate(xml, XPathConstants.NUMBER);
            System.out.println("Number of books: " + countNodes.intValue());
            
            expression = xPath.compile(getBooks);
            NodeList books = (NodeList) expression.evaluate(xml, XPathConstants.NODESET);
            for(int i = 0; i < books.getLength(); i++){
                
                System.out.println( "Book #" + (i+1) );
                
                NodeList bookAttributs = books.item(i).getChildNodes();
                System.out.println( "Title: " + bookAttributs.item(0).getTextContent() );
                
                expression = xPath.compile(getAuthorWithId +  bookAttributs.item(1).getTextContent() + "]" );
                Node author = (Node) expression.evaluate(xml, XPathConstants.NODE);
                
                System.out.println( "Author: " + author.getTextContent() );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace(System.out);
        }
    }
}
