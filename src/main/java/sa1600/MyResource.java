package sa1600;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Path("test")
public class MyResource {
	
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	@GET
	@Path("hello")
	public Response helloWorld() {
		return Response.ok().entity("Hello World").build();
	}
	
	@GET
	@Path("json")
	@Produces("application/json")
	public Response test() {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");
		
//		Student student = new Student();
//		student.setName("zhongkai");
		
		Entity entity = null;
		try {
			Key key = KeyFactory.createKey("Student", "itb1323");
			entity = datastore.get(key);
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Student student = new Student();
		student.setName(entity.getProperty("name").toString());
		return Response.ok().entity(student).build();
				
	}
	
	@GET
	@Path("savetest")
	@Produces("application/json")
	public Response datastore() {
		
		//Key key = KeyFactory.createKey("id", "itb1323");
		
		Entity student = new Entity("Student", "itb1323");
		
		student.setProperty("name", "zhongkai");
		student.setProperty("age", 50);
		
		Key key1 = datastore.put(student);
		
	
		try {
			student = datastore.get(key1);
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List list = new ArrayList();
		list.add(key1);
		list.add(student);
		
		

		return Response.ok().entity(list).build();
				
	}	

}
