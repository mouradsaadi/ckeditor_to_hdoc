import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class Main {

    // prod
    private static final String INTERMEDIATE_FILE_PATH = "java/out/intermediate.xml";
    private static final String INTERMEDIATE_ADDROOT_FILE_PATH = "java/out/intermediate_addroot.xml";

    //test
    //private static final String INTERMEDIATE_FILE_PATH = "intermediate.xml";
    //private static final String INTERMEDIATE_ADDROOT_FILE_PATH = "intermediate_addroot.xml";

    public static void main(String[] args) {
        try {
            addRoot(args[0], INTERMEDIATE_ADDROOT_FILE_PATH);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler titlesToSectionsHandler = new TitlesToSectionsHandler(INTERMEDIATE_FILE_PATH);
            saxParser.parse(INTERMEDIATE_ADDROOT_FILE_PATH, titlesToSectionsHandler);

            DefaultHandler titlesToHeadersHandler = new TitlesToHeadersHandler(args[1]);
            saxParser.parse(INTERMEDIATE_FILE_PATH, titlesToHeadersHandler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void addRoot(String in, String out) {
        try {
            File file = new File(out);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileReader fr = new FileReader(in);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());

            fw.write("<root>");

            int s = fr.read();
            while (s != -1) {
                fw.write(s);
                s = fr.read();
            }

            fw.write("</root>");

            fr.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
