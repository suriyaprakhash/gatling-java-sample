package com.example;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/*
 * This SpringBootServletInitializer is to notify the container the application class if external tomcat container is used instead of embedded tomcat
 */
@SpringBootApplication
public class GtogApplication  {

	//private double maxUploadSizeInMb = 10 * 1024 * 1024; // 10 MB
	
	public static void main(String[] args) {
		SpringApplication.run(GtogApplication.class, args);
	}

	 @Bean 
	    ServletContextTemplateResolver templateResolver(){ 
	        ServletContextTemplateResolver resolver=new ServletContextTemplateResolver(); 
	        resolver.setSuffix(".jsp"); 
	        resolver.setPrefix("/resources/templates/"); 
	        resolver.setTemplateMode("HTML5"); 
	        return resolver; 
	    } 
	
	
	//Tomcat large file upload connection reset
    //http://www.mkyong.com/spring/spring-file-upload-and-connection-reset-issue/
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 means unlimited
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });

        return tomcat;

    }
}
