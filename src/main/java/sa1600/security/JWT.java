package sa1600.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWT {
	
	private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	private static Key signingKey;
	
	static {
		signingKey = getKey();
	}

	public static String createJWT(String id, long ttlMillis) {
		
		String subject = "mySubject";
		String issuer = "myIssuer";
		
		
	    //long nowMillis = System.currentTimeMillis();
	   // Date now = new Date(ttlMillis);
	   // Key signingKey = this.getKey();

	    //Let's set the JWT Claims
	    JwtBuilder builder = Jwts.builder().setId(id)
	                                .setIssuedAt(new Date(System.currentTimeMillis()))
	                                .setSubject(subject)
	                                .setIssuer(issuer)
	                                .signWith(signatureAlgorithm, signingKey)
	    							.setExpiration(new Date(System.currentTimeMillis() + ttlMillis));
	                      
	    //Builds the JWT and serializes it to a compact, URL-safe string
	    return builder.compact();
	}

	public static String validateJWT(String jwt){
		Claims claims = Jwts.parser()
				.setSigningKey(signingKey)
				.parseClaimsJws(jwt).getBody();
		String id = claims.getId();
		long expiredAt = claims.getExpiration().getTime();		
		long delta = System.currentTimeMillis() - expiredAt;
		if(delta > 0){
			throw new RuntimeException("jwt token is expired");
		}
		return id;
	}
	
	private static Key getKey(){
		long t0 = System.currentTimeMillis();
	    byte[] apiKeySecretBytes = "mykey@@@%%5#34324324324234234ffsdfs32432werwerwerwer".getBytes();
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	    System.out.println((System.currentTimeMillis() - t0));
	    return signingKey;
	}
	
	public static void main(String[] args) throws InterruptedException{
		String token = JWT.createJWT("itb1323", 10000);
		System.out.println(token);
		String id = JWT.validateJWT(token);
		System.out.println(id);
		
		//Thread.sleep(1000*5);
		//jwt = new JWT();
		String token2 = JWT.createJWT("itb1323222", 1000);
		System.out.println(token2);
		String id2 = JWT.validateJWT(token2);
		System.out.println(id2);
				
		
	}
	
}
