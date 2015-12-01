package me.mfransen.openmonsters.api.addons.binders;

import me.mfransen.openmonsters.api.addons.IBinder;
import org.w3c.dom.Element;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Matt Fransen on 11/30/2015.
 * Class must have a constructor with a single Element object for parameters and a 'serialize' method with an element parameter
 */
public class ClassBinder<T> implements IBinder<T>{
    private Class<? extends T> clazz;
    private String tagName;
    public ClassBinder(String tagName, Class<? extends T> clazz) {
        this.tagName = tagName;
        this.clazz = clazz;
    }
    public ClassBinder(Class<? extends T> clazz) {
        this.tagName = clazz.getSimpleName();
        this.clazz = clazz;
    }
    @Override
    public String getTagName() {
        return tagName;
    }

    @Override
    public T deserialize(Element element) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return clazz.getConstructor(Element.class).newInstance(element);
    }

    @Override
    public void serialize(Element element, T object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        clazz.getDeclaredMethod("serialize",Element.class).invoke(object,element);
    }
}
