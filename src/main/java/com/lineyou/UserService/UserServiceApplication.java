package com.lineyou.UserService;

import com.lineyou.UserService.net.NetClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(UserServiceApplication.class, args);
		ctx.getBean(NetClient.class).start();
	}

}
