package com.july;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by haoyifen on 2017/5/29 16:21.
 */
@Slf4j
@RestController
public class DemoController {
	@GetMapping("/demo")
	public void demo(HttpServletRequest request) throws ServletRequestBindingException {
		String name = ServletRequestUtils.getRequiredStringParameter(request, "name");
		log.info("request data: name is {}",name);
		Integer age = ServletRequestUtils.getRequiredIntParameter(request, "age");
		log.info("request data: age is {}",age);
		WebApplicationContext webApplicationContext = RequestContextUtils.findWebApplicationContext(request);
		Environment environment = webApplicationContext.getBean(Environment.class);
		String property = environment.getProperty("user.home");
		String[] activeProfiles = environment.getActiveProfiles();
		log.info("user home is {}", property);
		log.info("active profiles are {}", Arrays.toString(activeProfiles));
	}
}
