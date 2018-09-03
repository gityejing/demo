package sax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class BatchModifyConnectionUtil {
	// 修改数据
	public static void edit(File file,String driver,String user,String password,String url) throws JDOMException,
			FileNotFoundException, IOException {
		
		SAXBuilder builder = new SAXBuilder();
		InputStream stream = new FileInputStream(file);
		Document document = builder.build(stream);
		Element root = document.getRootElement();
		
		List<Element> list = root.getChildren();
		Iterator<Element> it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			if(e.getName().equals("NodeSource")){
				Element e2 = e.getChild("Children");
				if(e2 != null){
					List<Element> list2 = e2.getChildren();
					if(list2 != null && list2.size()>0){
						for (Element element : list2) {
							Element e3 = element.getChild("Reader");
							if(e3 != null){
								Element e4 = e3.getChild("Connection");
								if(e4 != null){
									Element e5 = e4.getChild("Driver");
									Element e6 = e4.getChild("User");
									Element e7 = e4.getChild("Password");
									Element e8 = e4.getChild("Url");
									
									if(e5 != null){
										e5.setText(driver);
									}
									if(e6 != null){
										e6.setText(user);
									}
									if(e7 != null){
										e7.setText(password);
									}
									if(e8 != null){
										e8.setText(url);
									}
								}
							}
						}
					}
				}
			}
		}
		XMLOutputter output = new XMLOutputter();
		output.output(document, new FileOutputStream(file));
		System.out.println(file.getAbsolutePath());
	}
	
	static Map<DbInfo, String> map = new HashMap<DbInfo, String>();
	static{
		map.put(DbInfo.TEST, "E:\\Workspaces\\auditScp_1.0\\WebRoot\\WEB-INF\\打印模板\\测试");
		map.put(DbInfo.Local, "E:\\Workspaces\\auditScp_1.0\\WebRoot\\WEB-INF\\打印模板\\本地");
		map.put(DbInfo.M122, "E:\\Workspaces\\auditScp_1.0\\WebRoot\\WEB-INF\\打印模板\\10.96.245.122");
		map.put(DbInfo.M115, "E:\\Workspaces\\auditScp_1.0\\WebRoot\\WEB-INF\\打印模板\\10.96.245.115");
		map.put(DbInfo.M20, "E:\\Workspaces\\auditScp_1.0\\WebRoot\\WEB-INF\\打印模板\192.168.102.20");
		map.put(DbInfo.M21, "E:\\Workspaces\\auditScp_1.0\\WebRoot\\WEB-INF\\打印模板\\192.168.102.21");
	}
	
	public static void main(String[] args) {
		
		for (Map.Entry<DbInfo, String> entry : map.entrySet()) {
			DbInfo connection = entry.getKey();
			try {
				File file = new File(entry.getValue());
				if(file.isDirectory()){
					File[] files = file.listFiles();
					if(files!= null && files.length>0){
						for (File file2 : files) {
							BatchModifyConnectionUtil.edit(file2,connection.driver,connection.user,connection.password,connection.url);
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
