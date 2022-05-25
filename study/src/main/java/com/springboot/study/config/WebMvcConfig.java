package com.springboot.study.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Value("${file.path}")
	private String filePath;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/image/**") //요청이 들어오면 
		.addResourceLocations("file:///" + filePath) //요청의 경로를 바꿈 -> /image/**의 /image를 file:///" + filePath 경로로 바꿈
		.setCachePeriod(60*60) //캐시 지속시간 설정(초단위)
		.resourceChain(true) //해당 경로로 연결
		.addResolver(new PathResourceResolver()); // 해당경로를 연결시켜주는 객체->PathResourceResolver()를 사용해서 
	}
}
