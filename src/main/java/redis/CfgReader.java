package redis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import jodd.props.Props;
import jodd.props.PropsEntries;
import jodd.props.PropsEntry;


public class CfgReader {

	static HashMap<String, String> map = new HashMap<String, String>();
	static{
		Props props = new Props();
		try {
			props.load(CfgReader.class.getClassLoader().getResourceAsStream("config.properties"));
			PropsEntries entries = props.entries();
			Iterator<PropsEntry> iterator = entries.iterator();
			while(iterator.hasNext()){
				PropsEntry entry = iterator.next();
				map.put(entry.getKey(),entry.getValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(map);
	}
	
	public static String getParam(String key) {
		return map.get(key);
	}

	public static void main(String[] args) {
		System.out.println(getParam("ip"));
	}
}
