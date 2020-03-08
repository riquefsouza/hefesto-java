package br.com.hfs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@GetMapping("/")
	@ResponseBody
	public String home() {
		return "<h1>Welcome</h1>";
	}
	
	@GetMapping("/user")
	@ResponseBody
	public String user() {
		return "<h1>Welcome User</h1>";
	}
	
	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "<h1>Welcome Admin</h1>";
	}
}
