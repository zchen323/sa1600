package sa1600.test;

import org.junit.Test;

import sa1600.security.JWT;

public class JWTTest {
	
	@Test
	public void jwtTest(){
		JWT jwt = new JWT();
		String s = jwt.createJWT("1233", 1000*10);
		System.out.println(s);
	}

}
