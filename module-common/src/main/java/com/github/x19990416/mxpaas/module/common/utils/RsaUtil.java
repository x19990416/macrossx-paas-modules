/*
 *  Copyright (c) 2020-2021 Guo Limin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.x19990416.mxpaas.module.common.utils;

import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaUtil {
	public static String privateKey="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAICNKjStPGwYOtOlzOczCmfwgoVcMdpCOTAqJSFMCQDfBNMcnIE218Y8u9gi8Hqj1gTCiiYZT+oYoAeMYjLgUng8rxhlkEks+7U1saL9/oHHjv4UT37KFxHEs3bFeHW7pQWT1GHGJyLWdovQpshdbGgClUWq7LbH6yP9UmZx627TAgMBAAECgYBGueQDsWAx9K7A7VKrzTgncXrOFqhS9eZy6m0dQbEeapVD9VTh/qN+rMGIq8h1IRjJ66KITZrbKAs7u+/3H9YfBdbn0iJajsBbIQzipRKXHmaJT15A5hEs6Nz2EO6dcLaZGTUyIlVTMBC28jzZhAwW75kmUkdtKw27FTdtDW/YiQJBAOVr/TQrnkdzXjCqkKQmvbF31Ul3h0F4ie/6RajdSK5jK9/Ue7WHNift8kzFYw4TiSA/OPMDCPadSKIsF1bEcf0CQQCPcaQkXR2Q3DKELdii641L8gdx6Bpgp1A0BM5TLZKZUOrr85s8VAfD10tnGVoAJqHQMgLlcF/hAK4oncMs+xUPAkB2a3tTBoC4mNAxhpkKYgTgKd6qAUyLetCYUjLKqw3tFbt72Y3RcW1+xs+e2PP8PBE31+ppZVOnGCB5tRCG9PdxAkA4hJAl+8JQd1I4HlBDMQhFKiGg0dDC0GmbdWFOCKDAY8+MFDnP9VPx5/w/rQ93C8Gp5GqbuEEuyDPWsQJb0LolAkBLp84B98aC6ZQZaFKTGfL5U7OcWBK2EJazzjUqJAvD3+SB+wyvnhfJyS9V87sJ3ktVU452F0XGi2WCpitmy63b";
	private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAjSo0rTxsGDrTpcznMwpn8IKFXDHaQjkwKiUhTAkA3wTTHJyBNtfGPLvYIvB6o9YEwoomGU/qGKAHjGIy4FJ4PK8YZZBJLPu1NbGi/f6Bx47+FE9+yhcRxLN2xXh1u6UFk9Rhxici1naL0KbIXWxoApVFquy2x+sj/VJmcetu0wIDAQAB";
	private static final String SRC = "macrossx";

	public static void main(String[] args) throws Exception {
		System.out.println(generateKeyPair());
		System.out.println("\n");
		RsaKeyPair keyPair = new RsaKeyPair(publicKey,privateKey);
		System.out.println("公钥：" + keyPair.getPublicKey());
		System.out.println("私钥：" + keyPair.getPrivateKey());
		System.out.println("公钥揭秘:"+decryptByPrivateKey(privateKey,"PSUEwUio2W8j0sXX6z7K3qqWgL4P+0JuQ42nQQyD9VsytZCwu1CHP6BzOWo0dzySH8XeOuOOsBjPrPC8mKpLoK9JEX+WhCGp6KGLRoNZjGl5pMqhAa1uDwl1sDELSKginzFaKMQjTOSObsOrjFnsWldgDoiuxs5J+zLt4J85N0E="));


		System.out.println("\n");
		test1(keyPair);
		System.out.println("\n");
		test2(keyPair);
		System.out.println("\n");
	}

	/**
	 * 公钥加密私钥解密
	 */
	private static void test1(RsaKeyPair keyPair) throws Exception {
		System.out.println("***************** 公钥加密私钥解密开始 *****************");
		String text1 = encryptByPublicKey(keyPair.getPublicKey(), RsaUtil.SRC);
		String text2 = decryptByPrivateKey(keyPair.getPrivateKey(), text1);
		System.out.println("加密前：" + RsaUtil.SRC);
		System.out.println("加密后：" + text1);
		System.out.println("解密后：" + text2);
		if (RsaUtil.SRC.equals(text2)) {
			System.out.println("解密字符串和原始字符串一致，解密成功");
		} else {
			System.out.println("解密字符串和原始字符串不一致，解密失败");
		}
		System.out.println("***************** 公钥加密私钥解密结束 *****************");
	}

	/**
	 * 私钥加密公钥解密
	 * @throws Exception /
	 */
	private static void test2(RsaKeyPair keyPair) throws Exception {
		System.out.println("***************** 私钥加密公钥解密开始 *****************");
		String text1 = encryptByPrivateKey(keyPair.getPrivateKey(), RsaUtil.SRC);
		String text2 = decryptByPublicKey(keyPair.getPublicKey(), text1);
		System.out.println("加密前：" + RsaUtil.SRC);
		System.out.println("加密后：" + text1);
		System.out.println("解密后：" + text2);
		if (RsaUtil.SRC.equals(text2)) {
			System.out.println("解密字符串和原始字符串一致，解密成功");
		} else {
			System.out.println("解密字符串和原始字符串不一致，解密失败");
		}
		System.out.println("***************** 私钥加密公钥解密结束 *****************");
	}

	/**
	 * 公钥解密
	 *
	 * @param publicKeyText 公钥
	 * @param text 待解密的信息
	 * @return /
	 * @throws Exception /
	 */
	public static String decryptByPublicKey(String publicKeyText, String text) throws Exception {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyText));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] result = cipher.doFinal(Base64.decodeBase64(text));
		return new String(result);
	}

	/**
	 * 私钥加密
	 *
	 * @param privateKeyText 私钥
	 * @param text 待加密的信息
	 * @return /
	 * @throws Exception /
	 */
	public static String encryptByPrivateKey(String privateKeyText, String text) throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyText));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] result = cipher.doFinal(text.getBytes());
		return Base64.encodeBase64String(result);
	}

	/**
	 * 私钥解密
	 *
	 * @param privateKeyText 私钥
	 * @param text 待解密的文本
	 * @return /
	 * @throws Exception /
	 */
	public static String decryptByPrivateKey(String privateKeyText, String text) throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyText));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] result = cipher.doFinal(Base64.decodeBase64(text));
		return new String(result);
	}

	/**
	 * 公钥加密
	 *
	 * @param publicKeyText 公钥
	 * @param text 待加密的文本
	 * @return /
	 */
	public static String encryptByPublicKey(String publicKeyText, String text) throws Exception {
		X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyText));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] result = cipher.doFinal(text.getBytes());
		return Base64.encodeBase64String(result);
	}

	/**
	 * 构建RSA密钥对
	 *
	 * @return /
	 * @throws NoSuchAlgorithmException /
	 */
	public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
		String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
		String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
		return new RsaKeyPair(publicKeyString, privateKeyString);
	}


	/**
	 * RSA密钥对对象
	 */
	@Data
	public static class RsaKeyPair {

		private final String publicKey;
		private final String privateKey;

		public RsaKeyPair(String publicKey, String privateKey) {
			this.publicKey = publicKey;
			this.privateKey = privateKey;
		}

		public String getPublicKey() {
			return publicKey;
		}

		public String getPrivateKey() {
			return privateKey;
		}

	}


}

