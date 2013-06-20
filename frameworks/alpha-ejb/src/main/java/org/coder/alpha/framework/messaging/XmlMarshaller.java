/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.messaging;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;

/**
 * Utility for XML.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class XmlMarshaller {
	
	 /** JAXBContextのキャッシュ（JAXBContextのインスタンス化の度にクラスロードが起きるための回避策） */
    private static Map<Class<?>, JAXBContext> contextPool = new ConcurrentHashMap<Class<?>, JAXBContext>();

    /**
     * XML to object.
     * 
     * @param xml the xml
     * @param clazz the target 
     * @return object
     */
    public static <T> T unmarshal(String xml, Class<T> clazz) {

        T restored = null;
        StringReader reader = new StringReader(xml);
        try {
            JAXBContext context = createContext(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // オブジェクト変換
            JAXBElement<T> elem = unmarshaller.unmarshal(new StreamSource(reader), clazz);
            restored = elem.getValue();
        } catch (JAXBException e) {
            throw new RuntimeException("unmarshaling failed.", e);
        }
        return restored;
    }
    
    /**
     * Object to XML.
     * @param object the object 
     * @return xml 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String marshal(Object object) {
        StringWriter writer = new StringWriter();
        try {
            JAXBContext context = createContext(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            // XML変換
            marshaller.marshal(new JAXBElement(new QName(StringUtils.uncapitalize(object.getClass().getSimpleName())), object.getClass(), object), writer);
        } catch (JAXBException e) {
            throw new RuntimeException("marshaling failed.", e);
        }
        return writer.toString();
    }

    /**
     * JAXBContextを生成する
     * 
     * @param cls
     *            Java-XML変換対象のクラス型
     * @return JAXBContextのインスタンス
     * @throws JAXBException
     *             JAXB例外
     */
    private static JAXBContext createContext(Class<?> cls) throws JAXBException {
        if (!contextPool.containsKey(cls)) {
            contextPool.put(cls, JAXBContext.newInstance(cls));
        }
        return contextPool.get(cls);
    }

}
