package saxtest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXParserDemo {
    public static void main(String[] args) throws Exception {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        UserHandler userhandler = new UserHandler();
        saxParser.parse(IOUtils.toInputStream(fileContext), userhandler);
    }

    private static String fileContext = "<?xml version = \"1.0\"?>\n" +
            "<class>\n" +
            "   <student rollno = \"393\">\n" +
            "      <firstname>dinkar</firstname>\n" +
            "      <lastname>kad</lastname>\n" +
            "      <nickname>dinkar</nickname>\n" +
            "      <marks>85</marks>\n" +
            "   </student>\n" +
            "   \n" +
            "   <student rollno = \"493\">\n" +
            "      <firstname>Vaneet</firstname>\n" +
            "      <lastname>Gupta</lastname>\n" +
            "      <nickname>vinni</nickname>\n" +
            "      <marks>95</marks>\n" +
            "   </student>\n" +
            "   \n" +
            "   <student rollno = \"593\">\n" +
            "      <firstname>jasvir</firstname>\n" +
            "      <lastname>singn</lastname>\n" +
            "      <nickname>jazz</nickname>\n" +
            "      <marks>90</marks>\n" +
            "   </student>\n" +
            "</class>";
}

/**
 * 解析 xml 并将 tag name 和对用的内容打印出来
 */
class UserHandler extends DefaultHandler {
    boolean bFirstName = false;
    boolean bLastName = false;
    boolean bNickName = false;
    boolean bMarks = false;

    @Override
    public void startElement(String uri,
                             String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("student")) {
            String rollNo = attributes.getValue("rollno");
            System.out.println("Roll No : " + rollNo);
        } else if (qName.equalsIgnoreCase("firstname")) {
            bFirstName = true;
        } else if (qName.equalsIgnoreCase("lastname")) {
            bLastName = true;
        } else if (qName.equalsIgnoreCase("nickname")) {
            bNickName = true;
        } else if (qName.equalsIgnoreCase("marks")) {
            bMarks = true;
        }
    }

    @Override
    public void endElement(String uri,
                           String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("student")) {
            System.out.println("End Element :" + qName);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if (bFirstName) {
            System.out.println("First Name: "
                    + new String(ch, start, length));
            bFirstName = false;
        } else if (bLastName) {
            System.out.println("Last Name: " + new String(ch, start, length));
            bLastName = false;
        } else if (bNickName) {
            System.out.println("Nick Name: " + new String(ch, start, length));
            bNickName = false;
        } else if (bMarks) {
            System.out.println("Marks: " + new String(ch, start, length));
            bMarks = false;
        }
    }
}