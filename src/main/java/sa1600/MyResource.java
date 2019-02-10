package sa1600;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("test")
public class MyResource {
	
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
		
		Student student = new Student();
		student.setName("zhongkai");
		
		return Response.ok().entity(student).build();
				
	}

}
