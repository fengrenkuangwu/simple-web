package com.leaf.base.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * <p/>
 * Jaxb报文转换工具类
 * <p/>
 *
 * @author linql 创建 2012-4-16
 * @version 1.0 Copyright(c) 北京思特奇信息技术股份有限公司
 */
public final class JaxbUtil {

	/**
	 * 创建一个新的实例 JaxbUtil.
	 */
	private JaxbUtil() {
	}

	/**
	 * 日志输出
	 */
	private static final Log log = LogFactory.getLog(JaxbUtil.class);

	/**
	 * 编组，从类转换为xml字符串
	 *
	 * @param bean
	 *            javaBean对象
	 * @return javaBean对应的xml报文结构
	 * @throws JAXBException
	 */
	public static String marshal(Object bean) throws JAXBException {
		Writer writer = null;
		String packageName = bean.getClass().getPackage().getName();
		try {
			JAXBContext jc = JAXBContext.newInstance(packageName);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
			writer = new StringWriter();
			marshaller.marshal(bean, writer);
		} finally {
			try {
				// 输出流不为空
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				log.warn("XML转换文件流关闭错误", e);
			}
		}

		return writer.toString();
	}

	/**
	 * 从xml字符串解码为类实例
	 *
	 * @param <T>
	 *            javaBean对象类型
	 * @param docClass
	 *            javaBean对象类
	 * @param xml
	 *            转换XML报文
	 * @return javaBean对象类型对应的实例
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(Class<T> docClass, String xml) throws JAXBException, UnsupportedEncodingException {
		T t = null;
		String packageName = docClass.getPackage().getName();
		JAXBContext jc = JAXBContext.newInstance(packageName);
		Unmarshaller u = jc.createUnmarshaller();
		InputStream is = stringToInputStream(xml);
		t = (T) u.unmarshal(is);
		return t;
	}

	/**
	 * 从xml字符串解码为类实例
	 *
	 * @param <T>
	 *            javaBean对象类型
	 * @param docClass
	 *            javaBean对象类
	 * @param inputStream
	 *            转换XML报文
	 * @return javaBean对象类型对应的实例
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(Class<T> docClass, InputStream inputStream) throws JAXBException {
		T t = null;
		String packageName = docClass.getPackage().getName();
		JAXBContext jc = JAXBContext.newInstance(packageName);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		t = (T) unmarshaller.unmarshal(inputStream);
		return t;
	}

	/**
	 * xml报文准换为输入流
	 *
	 * @param str
	 *            xml报文
	 * @return 输入流
	 * @throws UnsupportedEncodingException
	 */
	private static InputStream stringToInputStream(String str) throws UnsupportedEncodingException {
		ByteArrayInputStream stream = null;
		stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
		return stream;
	}

}
