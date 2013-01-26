package org.coder.alpha.httpclient.core;

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

/**
 * XMLUtility.
 *
 * @author yoshida-n
 * @version	created.
 */
public final class XmlUtility {

    /** JAXBContextのキャッシュ（JAXBContextのインスタンス化の度にクラスロードが起きるための回避策） */
    private static Map<Class<?>, JAXBContext> contextPool = new ConcurrentHashMap<Class<?>, JAXBContext>();

    /**
     * プライベートコンストラクタ.
     */
    private XmlUtility() {
    }

    /**
     * 渡されたXMLを<code>clazz</code>で指定されたクラスのオブジェクトに変換する
     * 
     * @param <T>
     *            変換先のクラス
     * @param xml
     *            XML文字列
     * @param clazz
     *            変換先のクラス
     * @return 変換したオブジェクト
     * @throws XmlException
     *             オブジェクト変換に失敗した場合
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
            throw new IllegalStateException("unmarshaling failed.", e);
        }
        return restored;
    }

    /**
     * オブジェクトをXMLへ変換する.
     * 
     * @param object
     *            変換するオブジェクト
     * @return XML
     * @throws XmlException
     *             XML変換に失敗した場合
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String marshal(Object object) {
        StringWriter writer = new StringWriter();
        try {
            JAXBContext context = createContext(object.getClass());
            Marshaller marshaller = context.createMarshaller();

            // クラス名の一文字を小文字にしたものがXMLのルート要素名
            String fullName = object.getClass().getName();
            int classNameIndex = fullName.lastIndexOf('.') + 1;
            StringBuffer className = new StringBuffer();
            className.append(fullName.substring(classNameIndex, classNameIndex + 1).toLowerCase());
            className.append(fullName.substring(classNameIndex + 1));

            // XML変換
            marshaller.marshal(new JAXBElement(new QName(className.toString()), object.getClass(), object), writer);
        } catch (JAXBException e) {
            throw new IllegalStateException("marshaling failed.", e);
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
