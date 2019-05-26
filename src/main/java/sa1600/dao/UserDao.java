package sa1600.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.repackaged.com.google.common.util.Base64;

public class UserDao {
	
	private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	
	public static void createUser(String userId, String password) throws Exception{
		Key key = KeyFactory.createKey("User", userId);
		try {
			Entity user = datastore.get(key);
			if(user != null){
				System.out.println("===== user already exists");
				throw new Exception("user already existed");
			}
			
		} catch (EntityNotFoundException e) {
			System.out.println("entity not found: " + userId);
			Entity user = new Entity(key);//userId);
			try {
				user.setProperty("usr", userId);
				user.setProperty("pwd", hash(password));
				datastore.put(user);

				
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private static String hash(String input) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] output = md.digest(input.getBytes());
		return Base64.encode(output);		
	}
	
	public static boolean validateUsernameAndPassowrd(String userId, String password) throws NoSuchAlgorithmException{
		Key key = KeyFactory.createKey("User", userId);
		Entity entity = null;
		boolean result = false;
		try {
			entity = datastore.get(key);
			String pwdOnFile = (String)entity.getProperty("pwd");
			String pwdHash = hash(password);
			System.out.println(pwdOnFile);
			
			result = pwdOnFile.equals(pwdHash);
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return result;		
	}

}
