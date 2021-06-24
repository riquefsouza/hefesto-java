package br.com.hfs.util.bcrypt;

import java.util.function.Function;

public class BCryptUtil {

	private static String[] mutableHash = new String[1];

	public static Function<String, Boolean> update = hash -> { mutableHash[0] = hash; return true; };

	private static final UpdatableBCrypt bcrypt = new UpdatableBCrypt(10);

	public static String hash(String password) {
	    return bcrypt.hash(password);
	}

	public static boolean verifyAndUpdateHash(String password, String hash, Function<String, Boolean> updateFunc) {
	    return bcrypt.verifyAndUpdateHash(password, hash, updateFunc);
	}
	
}
