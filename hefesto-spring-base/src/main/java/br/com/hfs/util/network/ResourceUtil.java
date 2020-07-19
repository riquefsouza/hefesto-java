package br.com.hfs.util.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String[] resourceHandler() { 
		String[] res = { "/css/**", "/scss/**", "/img/**", "/js/**", "/vendor/**" };
		return res;
	}
	
	public static String[] resourceLocations() {	
		
		//classpath:/META-INF/resources/, classpath:/resources/, classpath:/static/, classpath:/public/
		
		
		String[] res = { "classpath:/static/css/","classpath:/static/scss/",
				"classpath:/static/img/","classpath:/static/js/", "classpath:/static/vendor/" };
		
		return res;
	}
	
	public static String[] resourceSwagger() {
		String[] res = { "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**", "/configuration/**" };
		return res;
	}
	
	public static boolean findResourceUrl(String[] resources, String url) {
		boolean ret = false;
		
		ArrayList<String> resourcesList = new ArrayList<String>(Arrays.asList(resources));
		resourcesList.add("/public/**");
		String[] permitAll = resourcesList.stream().toArray(String[]::new);		
		
		for (String item : permitAll) {
			item = item.replaceAll("[**]", "");

			String pat = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]+"+item+"+[-a-zA-Z0-9+&@#/%=~_|]";
			Pattern pattern = Pattern.compile(pat);
			Matcher matcher = pattern.matcher(url);
			if (matcher.find()) {
				ret = true;
				break;
			}
		}
		return ret;
	}
}
