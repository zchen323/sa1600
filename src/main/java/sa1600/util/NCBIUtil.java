package sa1600.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class NCBIUtil {
	
	private static Map<String, String> grch37Map = new HashMap<String, String>();
	private static Map<String, String> grch38Map = new HashMap<String, String>();
	
	static {
		grch37Map = loadData("GRCH37list.txt");
		grch38Map = loadData("GRCH38p7list.txt");
	}
	
	public static String getGRCH37RefName(String chromsome){
		return grch37Map.get(chromsome);
	}
	
	public static String getGRCH38RefName(String chromsome){
		return grch38Map.get(chromsome);
	}
	
	private static Map<String, String> loadData(String fileName){
		Map<String, String> map = new HashMap<String, String>();
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		ObjectMapper mapper = new ObjectMapper();		
		List<String> list = new ArrayList<String>();		
		try {
			list = mapper.readValue(is, list.getClass());
			for(int i = 0; i < list.size(); i++){
				map.put("" + (i + 1), list.get(i));
				map.put("X", map.get("23"));
				map.put("x", map.get("23"));
				map.put("Y", map.get("24"));
				map.put("y", map.get("24"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	
	public static void main(String[] args){
		System.out.println(getGRCH37RefName("22"));
		
		System.out.println(getGRCH38RefName("X"));
		
	}
}
