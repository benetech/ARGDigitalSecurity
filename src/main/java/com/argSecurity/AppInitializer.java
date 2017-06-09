/**
 * AppInitializer
 * 
 * @author daniela.depablos
 *	
 *Benetech trainning app Copyrights reserved
 *
 */
package com.argSecurity;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;

import org.springframework.boot.builder.ParentContextApplicationContextInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.boot.context.web.ServletContextApplicationContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class AppInitializer implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		WebApplicationContext rootAppContext = createRootApplicationContext(servletContext);
		if (rootAppContext != null) {
			servletContext.addListener(new ContextLoaderListener(rootAppContext) {
				@Override
				public void contextInitialized(ServletContextEvent event) {
					// no-op because the application context is already initialized
				}
			});
		}
		else {			
		}
	}

	protected WebApplicationContext createRootApplicationContext(
			ServletContext servletContext) {
		ApplicationContext parent = null;
		Object object = servletContext
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		if (object instanceof ApplicationContext) {			
			parent = (ApplicationContext) object;
			servletContext.setAttribute(
					WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, null);
		}
		SpringApplicationBuilder application = new SpringApplicationBuilder();
		if (parent != null) {
			application.initializers(new ParentContextApplicationContextInitializer(
					parent));
		}
		application.initializers(new ServletContextApplicationContextInitializer(
				servletContext));
		application.contextClass(AnnotationConfigEmbeddedWebApplicationContext.class);
		application = configure(application);
		// Ensure error pages are registered
		//application.sources(ErrorPageFilter.class);
		return (WebApplicationContext) application.run();
	}

	/**
	 * Configure the application. Normally all you would need to do it add sources (e.g.
	 * config classes) because other settings have sensible defaults. You might choose
	 * (for instance) to add default command line arguments, or set an active Spring
	 * profile.
	 * @param application a builder for the application context
	 * @see SpringApplicationBuilder
	 */
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootARGSecurity.class);
	}
	
	
}
