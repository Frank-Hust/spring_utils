package com.july;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.aop.support.AopUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.ClassUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.SocketUtils;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringUtilsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringUtilsApplication.class, args);
	}

	private void assertTest() {
		Object o = new Object();
		Assert.notNull(o, "object o should't not be null");
		String names = "tom,jerry,jack,mark,bob";
		Assert.doesNotContain(names, "david", names + "should not contain david");
		Assert.isTrue(names.startsWith("tom"), names + " should start with tom");
		Assert.isAssignable(List.class, ArrayList.class, "arrayList should be subType of list");
	}

	private void socketUtilsTest() {
		int availableTcpPort = SocketUtils.findAvailableTcpPort();
		// int port = SocketUtils.findAvailableTcpPort(30000, 40000);
		Assert.isTrue(availableTcpPort != 0, "should find a availableTcpPort");
		SortedSet<Integer> availableTcpPorts = SocketUtils.findAvailableTcpPorts(20);
		Assert.isTrue(availableTcpPorts.size() == 20, "does not find 20 port available");
	}

	private void beanUtilsTest() {
		// BeanUtils
	}

	private void reflectionUtilsTest(DemoService demoService) {
		Method save = ReflectionUtils.findMethod(demoService.getClass(), "save");
		Assert.isTrue(save.getName().equals("save"), "method name should equals to save");

		Field myList = ReflectionUtils.findField(demoService.getClass(), "myList");
		Assert.isTrue(myList.getName().equals("myList"), "field name should equals to myList");

		ArrayList<Field> fields = new ArrayList<>();

		Class<?> demoServiceClass = ClassUtils.getUserClass(demoService);
		ReflectionUtils.doWithFields(demoServiceClass, fields::add);

		log.info("field  of demoService is {}", fields.toString());
		log.info("field size of demoService is {}", fields.size());

		Method equalsMethod = ReflectionUtils.findMethod(demoServiceClass, "equals", Object.class);
		boolean isEqualsMethod = ReflectionUtils.isEqualsMethod(equalsMethod);
		Assert.isTrue(isEqualsMethod, equalsMethod.getName() + " should be equals method");
	}

	private void aopUtilsTest(DemoService demoService) {
		Assert.isTrue(AopUtils.isAopProxy(demoService), "demoService should be proxied");
		Assert.isTrue(AopUtils.isCglibProxy(demoService), "demoService should be cglib proxied");
		// AopUtils.canApply
		// AopUtils.findAdvisorsThatCanApply
		Method saveMethod = ReflectionUtils.findMethod(demoService.getClass(), "save");
		Assert.notNull(saveMethod, "save method of demoService should not be null");
		try {
			AopUtils.invokeJoinpointUsingReflection(demoService, saveMethod, new Object[] {});
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	private void classUtilsTest(DemoService demoService) {
		Class<?> userClass = ClassUtils.getUserClass(demoService);
		Assert.isTrue(userClass.equals(DemoService.class), "demoService's user class is DemoService");

		Constructor<? extends DemoService> constructorIfAvailable = ClassUtils
				.getConstructorIfAvailable(demoService.getClass());

		Assert.notNull(constructorIfAvailable, "demoService no arg construct should not be null");
		String s = ClassUtils.convertClassNameToResourcePath(DemoService.class.getName());
		Assert.isTrue(s.equals("com/july/DemoService"), "demoService class resource path should be com/july/DemoService ");
		Set<Class<?>> allInterfacesForClassAsSet = ClassUtils.getAllInterfacesForClassAsSet(DemoService.class);
		log.info("allInterfacesForClassAsSet size is {}", allInterfacesForClassAsSet.size());
		// ClassUtils.getMethod()
	}

	private void resolvableTest(DemoService demoService) {
		Field myList = ReflectionUtils.findField(demoService.getClass(), "myList");
		ResolvableType myListType = ResolvableType.forField(myList);
		Assert.isTrue(myListType.hasGenerics(), "myList should has Generics");
		Class<?> myListTypeRawClass = myListType.getRawClass();
		Assert.isTrue(myListTypeRawClass.equals(List.class), "myList raw class should be list");
		ResolvableType generic = myListType.getGeneric(0);
		Class<?> genericClass = generic.getRawClass();
		Assert.isTrue(genericClass.equals(String.class), "myList generic class should be string");
	}

	private void servletRequestUtilsTest(RestTemplate restTemplate) {
		restTemplate.getForEntity("http://localhost:8080/demo?name=july&age=25", Void.class);
	}

	private void digestUtilsTest() {
		String s = DigestUtils.md5DigestAsHex("julysky".getBytes(StandardCharsets.UTF_8));
		String md5 = "d52ceccd9a3341b720fa2baf9f06b8e3";
		Assert.isTrue(s.equals(md5), "md5 hex of julysky should be " + md5);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("julysky".getBytes(StandardCharsets.UTF_8));
		String inputStreamHex;
		try {
			inputStreamHex = DigestUtils.md5DigestAsHex(byteArrayInputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Assert.isTrue(s.equals(inputStreamHex), "md5 hex of julysky's byte stream should be " + md5);
	}

	private void base64Test() {
		String name = "julysky";
		String s = Base64Utils.encodeToString(name.getBytes(StandardCharsets.UTF_8));
		String nameEncoded = "anVseXNreQ==";
		Assert.isTrue(s.equals(nameEncoded), "base64 of " + name + " should be " + nameEncoded);

		String decodedName = new String(Base64Utils.decodeFromString(s), StandardCharsets.UTF_8);
		Assert.isTrue(decodedName.equals(name),"decodedName should equals to name: "+name);
	}

	@Bean
	public CommandLineRunner test(DemoService demoService, RestTemplate restTemplate) {
		return args -> {
			assertTest();
			socketUtilsTest();
			beanUtilsTest();
			reflectionUtilsTest(demoService);
			resolvableTest(demoService);
			aopUtilsTest(demoService);
			classUtilsTest(demoService);
			servletRequestUtilsTest(restTemplate);
			digestUtilsTest();
			base64Test();
		};
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
