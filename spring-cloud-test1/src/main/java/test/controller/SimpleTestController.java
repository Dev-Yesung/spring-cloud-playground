package test.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@ConfigurationProperties("server")
@RequestMapping("/api/simple-test")
@RestController
public class SimpleTestController {

	private String host;
	private String port;

	@RequestMapping(path = "/hello", method = RequestMethod.GET)
	public ResponseEntity<String> getHelloMessage() {
		return ResponseEntity.ok("Hello Spring Cloud!!");
	}

	@RequestMapping(path = "/health", method = RequestMethod.GET)
	public ResponseEntity<String> getHealth() {
		return ResponseEntity.ok("Current server[" + host + ":" + port + "] is working!!");
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
