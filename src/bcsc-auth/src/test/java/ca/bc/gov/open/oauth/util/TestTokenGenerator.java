package ca.bc.gov.open.oauth.util;

import java.text.ParseException;
import java.util.Date;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

/**
 * 
 * Token Generator. 
 * 
 * Used by ECRC Token Validation Service Testing ONLY. 
 * 
 * Tokens generated by this utility are NOT valid.
 * 
 * @author shaunmillargov
 *
 */
public class TestTokenGenerator {
	
	/**
	 * 
	 * generateBCSCAccessToken
	 * 
	 * @param privateKey
	 * @param issuer
	 * @param expiryDate
	 * @return
	 */
	public static String generateBCSCAccessToken(String privateKey, String issuer, Date expiryDate) {
	
		// BCSCAccess Token claims.
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
		    .issuer(issuer)
		    .claim("jti", "b13819ee-ca13-4684-946a-58eb65c64385")
		    .audience("urn.my.test.org.com") 
		    .issueTime(new Date(new Date().getTime()))
		    .expirationTime(expiryDate)
		    .build();
		
		return generateBCSCToken(privateKey, issuer, claimsSet); 
		
	}
	
	/**
	 * 
	 * generateBCSCIDToken
	 * 
	 * @param privateKey
	 * @param issuer
	 * @param expiryDate
	 * @return
	 */
	public static String generateBCSCIDToken(String privateKey, String issuer, Date expiryDate) {
		
		// BCSC ID Token claims
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
		    .issuer(issuer)
		    .claim("jti", "b13819ee-ca13-4684-946a-58eb65c64385")
		    .subject("3MD^677545S3UKKXRSNLR5TGQVZWWV6AE")
		    .audience("urn.my.test.org.com") 
		    .claim("kid", "1")
		    .claim("acr", "3")
		    .issueTime(new Date(new Date().getTime()))
		    .expirationTime(expiryDate)
		    .build();
		
		return generateBCSCToken(privateKey, issuer, claimsSet); 
		
	}
	
	
	/**
	 * 
	 * Generates a cryptographically sound Token similar to those generated
	 * by BCSC (but not signed by their private key).  
	 * 
	 * @param privateKey
	 * @param issuer
	 * @param claims
	 * @return
	 */
	private static String generateBCSCToken(String privateKey, String issuer, JWTClaimsSet claims) {
		
		JWSSigner signer = null; 
		SignedJWT signedJWT = null; 
		
		try {
			
			signer = new RSASSASigner( RSAKey.parse(privateKey));
			
			signedJWT = new SignedJWT(
				    new JWSHeader.Builder(JWSAlgorithm.RS256).keyID("1").build(),
				    claims);
			
			signedJWT.sign(signer);
			
		} catch (ParseException | JOSEException e) {
			e.printStackTrace();
		}
		
		return signedJWT.serialize();
		
	}

}