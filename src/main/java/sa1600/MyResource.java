package sa1600;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import sa1600.dao.UserDao;
import sa1600.data.InfoConfirm;
import sa1600.data.Kit;
import sa1600.util.EmailUtil;
import sa1600.util.NCBIUtil;
import sa1600.util.Sequence;

@Path("test")
public class MyResource {
	
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	private Sequence sequence = new Sequence();
	
	@GET
	@Path("hello")
	public Response helloWorld() {
		EmailUtil.sendSimpleMail();
		return Response.ok().entity("Hello World").build();
	}
	
	@GET
	@Path("createUser")
	public Response createUser() throws Exception{
		UserDao.createUser("itb1323", "pwd");
		return Response.ok().entity("user created").build();
	}
	
	@GET
	@Path("validateUser")
	public Response validateUser() throws Exception{
		UserDao.validateUsernameAndPassowrd("itb1323", "pwd");
		return Response.ok().entity("").build();
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
	
	@POST
	@Path("kit")
	@Consumes("application/json")
	@Produces("application/json")
	public Response toBeconfirm(List<Kit> kits) throws Exception{
		List<InfoConfirm> info = new ArrayList<InfoConfirm>();
		for(int i = 0; i < kits.size(); i++){
			InfoConfirm confirm = new InfoConfirm();
			
			Kit kit = kits.get(i);
			int from = Integer.parseInt(kit.getPosition()) - 300;
			int to = Integer.parseInt(kit.getPosition()) + 300;
			Object NCBUtil;
			String refName = NCBIUtil.getGRCH38RefName(kit.getChromosome());
			String seq = sequence.getSequence(refName, from, to);
			kit.setSequence(seq);			
			confirm.setKit(kits.get(i));
			
			
			
			confirm.setDifficulty("Low, " + i);
			confirm.setPrice("" + (i*4));
			confirm.setEta("" + i);
			info.add(confirm);
		}		
		return Response.ok().entity(info).build();
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
		System.out.println(key1.getName());
		System.out.println(key1.getId());
		System.out.println(key1.getKind());
		System.out.println(key1.getParent());
		
		
		
	
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
