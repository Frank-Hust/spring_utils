package com.july;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by haoyifen on 2017/5/29 14:48.
 */
@Service
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoService {
	private List<String> myList = new ArrayList<>();

	public void save() {
		log.info("demo service save");
	}
}
