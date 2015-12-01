package me.mfransen.openmonsters.api.addons;

import org.w3c.dom.Element;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Matt Fransen on 11/30/2015.
 */
public interface IBinder<T> {
    String getTagName();
    T deserialize(Element element) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
    void serialize(Element element, T object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

}
