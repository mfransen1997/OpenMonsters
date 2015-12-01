package me.mfransen.openmonsters.api.addons;

import org.w3c.dom.Element;

/**
 * Created by Matt Fransen on 11/30/2015.
 */
public interface IBinder<T> {
    String getTagName();
    T deserialize(Element element);
    void serialize(Element element, T object);

}
