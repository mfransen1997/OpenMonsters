package me.mfransen.openmonsters.assets.loaders;

import com.badlogic.gdx.graphics.Texture;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import me.mfransen.openmonsters.assets.AssetLoader;
import me.mfransen.openmonsters.assets.AssetManager;
import me.mfransen.openmonsters.map.Map;
import me.mfransen.openmonsters.map.MapLayer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

/**
 * Created by matt on 12/12/15.
 */
public class MapLoader implements AssetLoader<Map> {
    @Override
    public Class<? extends Map> type() {
        return Map.class;
    }

    @Override
    public Map load(AssetManager assets, byte[] data) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            Scanner scnr = new Scanner(bis);
            String xml = scnr.useDelimiter("\\Z").next();
            scnr.close();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            db = factory.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new StringReader(xml.substring(0,xml.lastIndexOf(">")+1))));
            Map output = new Map(doc.getDocumentElement().getAttribute("tileset"), Integer.parseInt(doc.getDocumentElement().getAttribute("width")), Integer.parseInt(doc.getDocumentElement().getAttribute("height")), Integer.parseInt(doc.getDocumentElement().getAttribute("tileSize")));
            NodeList nodes = ((Element) doc.getDocumentElement().getElementsByTagName("layers").item(0)).getElementsByTagName("layer");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element e = (Element) nodes.item(i);
                MapLayer layer = new MapLayer(output);
                String[] layerData = e.getElementsByTagName("tiles").item(0).getTextContent().replace("\n", "").replace("\r", "").replace(" ", "").split(",");
                for (int j = 0; j < layerData.length; j++) {
                    int val = Integer.parseInt(layerData[j]) - 1;
                    layer.tiles[j % output.getWidth()][output.getHeight()-j / output.getWidth()-1] = val;
                }
                output.layers.add(layer);
            }
            return output;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
