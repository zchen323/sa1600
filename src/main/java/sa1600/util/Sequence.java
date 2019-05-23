package sa1600.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sequence {
	
	private static final Logger log = Logger.getLogger(Sequence.class.getName());
	
	private Map<String, String> giMap = new HashMap<String, String>();
	
	public Sequence() {	
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("GI.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String line = null;
		try {
			while((line = br.readLine()) != null){
				if(line.isEmpty()){
					;
				}else if(line.startsWith("#")){
					;
				}else{
					String[] keyValue = line.split("\\s+");
					giMap.put(keyValue[0], keyValue[1]);
				}
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}		
	}
	
	
	public String getSequence(String accession, int from, int to) throws Exception{
		String result = null;
		String id = giMap.get(accession);
		if(id != null && !id.isEmpty()){
			String urlString = "https://www.ncbi.nlm.nih.gov/sviewer/viewer.fcgi?id=" + id + "&db=nuccore&report=fasta&extrafeat=null&conwithfeat=on&hide-cdd=on&from=" + from + "&to=" + to;
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			int responseCode = conn.getResponseCode();
			InputStream is = null;
			if(responseCode == 200){
				is = conn.getInputStream();
				result =  readFromInputStream(is);
			}else{
				is = conn.getErrorStream();
				String errorMessage = readFromInputStream(is);
				throw new Exception("HTPP error code: " + responseCode + ", " +  errorMessage+ ". failed to get sequence for " + accession + " from: " + from + " to: " + to);
			}
		}else{
			throw new Exception("Not support: " + accession);
		}
		return result;
	}

	private static String readFromInputStream(InputStream is) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while((line = br.readLine()) != null){
			sb.append(line).append(System.lineSeparator());
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception{
		Sequence fs = new Sequence();
		String seg = fs.getSequence("NC_012920.1", 12345, 12355);
		System.out.println(seg);
	}
}
