/**
 * Initializing web contex for the app 
 * 
 * @author daniela.depablos
 *	
 *	Benetech trainning app Copyrights reserved
 *
 */

package com.argSecurity.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import com.argSecurity.listener.SessionListener;

@Configuration
public class WebConfig implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		// TODO Auto-generated method stub
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
	    applicationContext.register(ApplicationConfig.class);

	    // Manage the lifecycle of the root application context
	    container.addListener(new ContextLoaderListener(applicationContext));
	    container.addListener(new SessionListener());
		
		// Add springSecurityFilterChain to the context
		FilterRegistration.Dynamic springSecurityFilterChain = container.addFilter( "springSecurityFilterChain", DelegatingFilterProxy.class );
		springSecurityFilterChain.addMappingForUrlPatterns( null, false, "/*" );

	    // Register and map the dispatcher servlet
	    DispatcherServlet servletDispatcher = new DispatcherServlet(applicationContext);
	    ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", servletDispatcher);
	    dispatcher.setLoadOnStartup(1);
	    dispatcher.addMapping("/rest/*");

	    // Register and map the data rest "exporter" servlet
	    DispatcherServlet servletExporter = new DispatcherServlet();
	    ServletRegistration.Dynamic exporter = container.addServlet("exporter", servletExporter);
	    exporter.setLoadOnStartup(1);
	    exporter.addMapping("/data-rest/*");
	}

}
