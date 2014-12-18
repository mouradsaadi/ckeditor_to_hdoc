import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TitlesToSectionsHandler extends DefaultHandler {

    private BufferedWriter mBufferedWriter;

    public boolean openH1 = false;
    public boolean openH2 = false;
    public boolean openH3 = false;

    public TitlesToSectionsHandler(String path) throws IOException {
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

    private void closeLastH1() {
        try {
            closeLastH2();
            if(openH1) {
                mBufferedWriter.write("</section><!-- end h1 -->\n");
                openH1 = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openNewH1() {
        try {
            closeLastH1();
            mBufferedWriter.write("<section>");
            openH1 = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeLastH2() {
        try {
            closeLastH3();
            if (openH2) {
                mBufferedWriter.write("</section><!-- end h2 -->\n");
                openH2 = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openNewH2() {
        try {
            closeLastH2();
            mBufferedWriter.write("<section>");
            openH2 = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeLastH3() {
        if(openH3) try {
            mBufferedWriter.write("</section><!-- end h3 -->\n");
            openH3 = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openNewH3() {
        try {
            closeLastH3();
            mBufferedWriter.write("<section>");
            openH3 = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            if(qName.equalsIgnoreCase("h1")) {
                openNewH1();
            } else if(qName.equalsIgnoreCase("h2")) {
                openNewH2();
            } else if (qName.equalsIgnoreCase("h3")) {
                openNewH3();
            }
            mBufferedWriter.write("<" + qName);
            for(int i = 0; i<attributes.getLength(); i++) {
                mBufferedWriter.write(" "+attributes.getQName(i)+"=\""+attributes.getValue(i)+"\"");
            }
            mBufferedWriter.write(">");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equalsIgnoreCase("root")) return;

        try {
            mBufferedWriter.write("</" + qName + ">");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        try {
            mBufferedWriter.write(new String(ch, start, length));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endDocument() throws SAXException {
        closeLastH3();
        closeLastH2();
        closeLastH1();

        try {
            mBufferedWriter.write("</root>");
            mBufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
