package sa1600.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Test {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException{
		// GRCH37list
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("GRCH37list.txt");
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<String> list = new ArrayList<String>();
		
		list = mapper.readValue(is, list.getClass());
		is.close();
		
		System.out.println(list);
		
		
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("GI.txt");
		Map<String, String> chromsomeUid = new HashMap<String, String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String line = null;
		while((line = br.readLine()) != null){
			if(line.isEmpty()){
				;
			}else if(line.startsWith("#")){
				;
			}else{
				String[] keyValue = line.split("\\s+");
				chromsomeUid.put(keyValue[0], keyValue[1]);
			}
		}
		System.out.println(chromsomeUid);
		
		for(String ch : list){
			System.out.println(ch + ": " + chromsomeUid.get(ch));
		}
		
		
		
		
		
	}
}
