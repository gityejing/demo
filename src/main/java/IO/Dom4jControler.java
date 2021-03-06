package IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jControler {

	public static void main(String[] args) {
		Dom4jControler newXml = new Dom4jControler();
		String name = "src/main/java/sax/newXML.xml";
		String newName = "src/main/java/sax/UpdateXML.xml";
/*		if (!newXml.newXMl(name)) {
			System.out.println("创建出现错误！");
			return;
		} else {
			System.out.println("创建XML成功！");
		}*/
		
		if (!newXml.update(name, newName)) {
			System.out.println("修改出现错误！");
			return;
		} else {
			System.out.println("修改XML成功");
		}
	}

	public Document read(String fileName) throws MalformedURLException,
			DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(fileName));
		return document;
	}

	public Element getRootElement(Document doc) {
		return doc.getRootElement();
	}

	
	/**
	 * 创建一个新的xml文件
	 * @param fileName
	 * @return
	 */
	public boolean newXMl(String fileName) {
		boolean returnValue = false;
		// 定义一个XML文档
		Document docement = DocumentHelper.createDocument();
		// 设置XML文档的元素
		Element rootElement = docement.addElement("DOM4J创建XML文档");
		rootElement.addComment("镜花水月2007-4-24晚上创建");
		Element nameElement = rootElement.addElement("镜花水月");
		nameElement.addAttribute("名字", "不告诉你");
		Element schoolElement = nameElement.addElement("学校");
		schoolElement.setText("西安翻译学院");
		Element cityElement = rootElement.addElement("城市");
		cityElement.setText("上海");
		Element homeElement = cityElement.addElement("住址");
		homeElement.setText("徐家汇");
		// 创建XML文档
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(new FileWriter(new File(fileName)), format);
			writer.write(docement);
			
			writer.close();
			return returnValue = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	public boolean update(String fileName, String newFileName) {
		boolean returnValaue = false;
		SAXReader reader = new SAXReader();
		try {
			Document docement = reader.read(new File(fileName));
			List list = null;
			list = docement.selectNodes("/DOM4J创建XML文档/镜花水月/@名字");
			if(list != null && list.size()>0){
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Attribute attribute = (Attribute) it.next();
					if (attribute.getValue().equals("不告诉你")) {
						attribute.setValue("还是不告诉你");
					}
				}
				// 将就该写入新的文件
				try {
					// 转码
					OutputFormat format = OutputFormat.createPrettyPrint();
					format.setEncoding("UTF-8");
					XMLWriter writer = new XMLWriter(new FileWriter(new File(newFileName)), format);
					writer.write(docement);
					writer.close();
					return returnValaue = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return returnValaue;
	}
}