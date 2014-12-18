import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TitlesToHeadersHandler extends DefaultHandler {

    private BufferedWriter mBufferedWriter;
    private boolean openDiv = false;
    private boolean openHeader = false;
    private String[] sIgnoredTags = new String[]{"strong"};

    public TitlesToHeadersHandler(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            mBufferedWriter = new BufferedWriter(fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            if (doWeIgnore(qName)) return;
            if (qName.equals("section")) {
                if (openDiv) {
                    mBufferedWriter.write("\n</div>\n");
                    openDiv = false;
                }
                mBufferedWriter.write("<section");
            } else if (qName.equalsIgnoreCase("li")) {
                mBufferedWriter.write("<li><p");
            } else if (qName.equals("h1") || qName.equals("h2") || qName.equals("h3")) {
                mBufferedWriter.write("<header><h1");
                openHeader = true;
            } else {
                mBufferedWriter.write("<" + qName);
            }
            writeAttributes(attributes);
            mBufferedWriter.write(">");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean doWeIgnore(String tag) {
        if (openHeader) {
            if (!tag.equalsIgnoreCase("h1") && !tag.equalsIgnoreCase("h2") && !tag.equalsIgnoreCase("h3")) return true;
        }
        for (String itag : sIgnoredTags) {
            if (tag.equalsIgnoreCase(itag)) {
                return true;
            }
        }
        return false;
    }

    private void writeAttributes(Attributes attributes) throws IOException{
        for(int i = 0; i<attributes.getLength(); i++) {
            mBufferedWriter.write(" "+attributes.getQName(i)+"=\""+attributes.getValue(i)+"\"");
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (doWeIgnore(qName)) return;
        if (qName.equalsIgnoreCase("root")) return;
        try {
            if (qName.equals("h1") || qName.equals("h2") || qName.equals("h3")) {
                mBufferedWriter.write("</h1></header>\n<div>\n");
                openDiv = true;
                openHeader = false;
            } else if (qName.equalsIgnoreCase("li")) {
                mBufferedWriter.write("</p></li>");
            } else if (qName.equals("section")) {
                if (openDiv) {
                    mBufferedWriter.write("\n</div>\n");
                    openDiv = false;
                }
                mBufferedWriter.write("</section>");
            } else {
                mBufferedWriter.write("</" + qName + ">");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        try {
            mBufferedWriter.write(new String(ch, start, length));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endDocument() throws SAXException {
        try {
            mBufferedWriter.write("</root>");
            mBufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
