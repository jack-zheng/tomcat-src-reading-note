package saxtest;

import org.apache.commons.io.IOUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

/**
 * 在成绩末尾添加一个 'Result' 节点
 */
public class ModifyFile extends DefaultHandler {
    static String displayText[] = new String[1000];
    static int numberLines = 0;
    static String indentation = "";


    public static void main(String[] args) throws Exception {
        File inputFile = new File("input.txt");
        SAXParserFactory factory =
                SAXParserFactory.newInstance();
        ModifyFile obj = new ModifyFile();
        obj.childLoop(IOUtils.toInputStream(fileContent));
        FileWriter filewriter = new FileWriter("newfile.xml");

        for (int loopIndex = 0; loopIndex < numberLines; loopIndex++) {
            filewriter.write(displayText[loopIndex].toCharArray());
            filewriter.write('\n');
            System.out.println(displayText[loopIndex].toString());
        }
        filewriter.close();
    }


    public void childLoop(InputStream ins) {
        DefaultHandler handler = this;
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(ins, handler);
        } catch (Throwable t) {
        }
    }

    public void startDocument() {
        displayText[numberLines] = indentation;
        displayText[numberLines] += "<?xml version = \"1.0\" encoding = \"" +
                "UTF-8" + "\"?>";
        numberLines++;
    }

    public void processingInstruction(String target, String data) {
        displayText[numberLines] = indentation;
        displayText[numberLines] += "<?";
        displayText[numberLines] += target;

        if (data != null && data.length() > 0) {
            displayText[numberLines] += ' ';
            displayText[numberLines] += data;
        }
        displayText[numberLines] += "?>";
        numberLines++;
    }

    public void startElement(String uri, String localName, String qualifiedName,
                             Attributes attributes) {

        displayText[numberLines] = indentation;

        indentation += "    ";

        displayText[numberLines] += '<';
        displayText[numberLines] += qualifiedName;

        if (attributes != null) {
            int numberAttributes = attributes.getLength();

            for (int loopIndex = 0; loopIndex < numberAttributes; loopIndex++) {
                displayText[numberLines] += ' ';
                displayText[numberLines] += attributes.getQName(loopIndex);
                displayText[numberLines] += "=\"";
                displayText[numberLines] += attributes.getValue(loopIndex);
                displayText[numberLines] += '"';
            }
        }
        displayText[numberLines] += '>';
        numberLines++;
    }

    public void characters(char characters[], int start, int length) {
        String characterData = (new String(characters, start, length)).trim();

        if (characterData.indexOf("\n") < 0 && characterData.length() > 0) {
            displayText[numberLines] = indentation;
            displayText[numberLines] += characterData;
            numberLines++;
        }
    }

    public void endElement(String uri, String localName, String qualifiedName) {
        indentation = indentation.substring(0, indentation.length() - 4);
        displayText[numberLines] = indentation;
        displayText[numberLines] += "</";
        displayText[numberLines] += qualifiedName;
        displayText[numberLines] += '>';
        numberLines++;

        if (qualifiedName.equals("marks")) {
            startElement("", "Result", "Result", null);
            characters("Pass".toCharArray(), 0, "Pass".length());
            endElement("", "Result", "Result");
        }
    }

    private static String fileContent = "<?xml version = \"1.0\"?>\n" +
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
