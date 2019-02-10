package sa1600;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class MyApplication extends Application{
	
	private Set<Object> singletons = new HashSet<Object>();
	
	public MyApplication() {
		singletons.add(new MyResource());
	}
	
	@Override
	public Set<Object> getSingletons(){
		return this.singletons;
	}

}
