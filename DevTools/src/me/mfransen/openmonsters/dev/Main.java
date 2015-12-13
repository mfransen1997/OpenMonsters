package me.mfransen.openmonsters.dev;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by matt on 12/12/15.
 */
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        if(args.length>0) {
            if (args[0].equalsIgnoreCase("convertMap")) {
                System.out.print("Input File: ");
                String in = input.nextLine();
                System.out.print("Output File (*.map): ");
                String output = input.nextLine();
                System.out.println("tileset asset name: ");
                String asset = input.nextLine();
                try {
                    convertMap(new File(in), new File(output), asset);
                    System.out.println("Map converted");
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
            }
        } else {
            String cmd = input.nextLine();
            main(new String[]{cmd});
        }
    }
    public static void convertMap(File input, File output, String asset) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(input);
        Document newDoc = builder.newDocument();
        Element e = newDoc.createElement("Map");
        e.setAttribute("tileset",asset);
        e.setAttribute("width",doc.getDocumentElement().getAttribute("width"));
        e.setAttribute("height",doc.getDocumentElement().getAttribute("height"));
        e.setAttribute("tileSize",doc.getDocumentElement().getAttribute("tilewidth"));
        Element layers = newDoc.createElement("layers");
        NodeList oldLayers = doc.getDocumentElement().getElementsByTagName("layer");
        for(int i = 0; i < oldLayers.getLength(); i++) {
            Element l = (Element)oldLayers.item(i);
            Element layer = newDoc.createElement("layer");
            Element tiles = newDoc.createElement("tiles");
            tiles.setTextContent(((Element)l.getElementsByTagName("data").item(0)).getTextContent());
            layer.appendChild(tiles);
            layers.appendChild(layer);
        }
        e.appendChild(layers);
        newDoc.appendChild(e);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource src = new DOMSource(newDoc);
        StreamResult result = new StreamResult(output);
        transformer.transform(src,result);
    }
}
