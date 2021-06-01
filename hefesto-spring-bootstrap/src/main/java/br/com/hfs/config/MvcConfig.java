package br.com.hfs.config;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.hfs.util.network.HttpMessageConverterUtil;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@ComponentScan
public class MvcConfig implements WebMvcConfigurer {

	public MvcConfig() {
		super();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/bootstrap-5.0.1/**",  "/popper-2.9.2/**", "/fontawesome-free/**", "/jquery-3.6.0/**", 
				"/dataTables-1.10.24/**", "/css/**", "/img/**", "/js/**")
				.addResourceLocations("classpath:/static/bootstrap-5.0.1/", "classpath:/static/popper-2.9.2/",
						"classpath:/static/fontawesome-free/", "classpath:/static/jquery-3.6.0/",
						"classpath:/static/dataTables-1.10.24/",
						"classpath:/static/css/", "classpath:/static/img/", "classpath:/static/js/");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.addAll(HttpMessageConverterUtil.getMessageConverters());
	}

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login.html");
		registry.addViewController("/index.html");
		registry.addViewController("/accessDenied");

		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
