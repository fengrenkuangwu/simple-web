package com.leaf.base.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * <p/>
 *
 * @author Administrator 创建 2012-3-13
 * @version 1.0 Copyright(c) 北京神州数码思特奇信息技术股份有限公司
 */
public class XmlUtil {

    /**
     * 日志
     */
    private static Log log = LogFactory.getLog(XmlUtil.class);

    /**
     * 创建DOM对象
     *
     * @param _xmlStr
     * @param _charSetName
     * @return
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws UnsupportedEncodingException 
     */
    public static Document builderDomWithStr(String _xmlStr, String _charSetName) throws ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException {
        // 解析返回结果
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        Document document = null;
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(new ByteArrayInputStream(_xmlStr
                    .getBytes(_charSetName)));
        return document;
    }

    /**
     * 根据XPATH获取节点值
     *
     * @param query       XPATH表达式
     * @param xmlDocument 文档对性
     * @return 节点值
     * @throws XPathExpressionException 
     */
    public static String evaluateXPath(String query, Node xmlDocument) throws XPathExpressionException {
        String result = null;
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = null;
            expr = xpath.compile(query);
            result = (String) expr.evaluate(xmlDocument, XPathConstants.STRING);
        return result;
    }

    public static List<String> evaluateXPathList(String query, Node xmlDocument) throws XPathExpressionException {
        List<String> resultList = new ArrayList<String>();
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = null;
            expr = xpath.compile(query);
            NodeList nodeList = (NodeList) expr.evaluate(xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                resultList.add(nodeList.item(i).getFirstChild().getNodeValue());
            }
        return resultList;
    }

    /**
     * @param interInfoElement
     * @param NodeName
     * @return
     */
    public static String getElementValue(Element interInfoElement, String NodeName) {
        NodeList interInfoList = interInfoElement.getElementsByTagName(NodeName);
        String temp = "";
        if (interInfoList.getLength() != 0) {
            Element e = (Element) interInfoList.item(0);
            Text t = (Text) e.getFirstChild();
            if (t == null) {
                temp = "";
            } else {
                temp = t.getNodeValue();
            }
        }
        return temp;
    }

    /**
     * @param interInfoElement
     * @param nodeName
     * @return
     */
    public static NodeList getNodeListByTagName(Element interInfoElement, String nodeName) {
        return interInfoElement.getElementsByTagName(nodeName);
    }

    /**
     * 获取节点的属性值
     *
     * @param node
     * @param attrName
     * @return
     */
    public static String getNodeAttribute(Node node, String attrName) {
        return node.getAttributes().getNamedItem(attrName).getNodeValue();
    }

    /**
     * 根据地址读取xml转成字符串
     *
     * @param fileName xml名称
     * @param fileUrl  xml文件存放路径
     * @return String 转换后的字符串
     * @throws java.io.IOException io异常
     * @author sunhao
     */
//    public static String getXml2Str(String fileName, String fileUrl) throws IOException {
//        StringBuffer sb = new StringBuffer();
//        String webRootPath = App.getAppPath();
//        String path = webRootPath + fileUrl;
//        File file = new File(path + fileName);
//        InputStream is = new FileInputStream(file);
//        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
//        BufferedReader br = new BufferedReader(isr);
//        int tempChar;
//        while ((tempChar = br.read()) != -1) {
//            sb.append((char) tempChar);
//        }
//        return sb.toString();
//    }
}
