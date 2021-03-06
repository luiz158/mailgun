package net.sargue.mailgun.content;

import net.sargue.mailgun.MailgunException;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

import static javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING;
import static javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION;

class Util {
    private Util() {}

    static String escapeXml(String target) {
        try {
            Document document = DocumentBuilderFactory.newInstance()
                                                      .newDocumentBuilder()
                                                      .newDocument();
            Text text = document.createTextNode(target);
            TransformerFactory factory = TransformerFactory.newInstance();
            factory.setFeature(FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = factory.newTransformer();
            DOMSource source = new DOMSource(text);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.setOutputProperty(OMIT_XML_DECLARATION, "yes");
            transformer.transform(source, result);
            return writer.toString();
        } catch (ParserConfigurationException | TransformerException e) {
            throw new MailgunException("Problem escaping XML", e);
        }
    }
}
