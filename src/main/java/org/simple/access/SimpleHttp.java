package org.simple.access;

// Begin imports

import java.io.File;
import java.util.HashMap;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.simple.internal.SimplePath;
import org.simple.servlet.RootServlet;
import org.simple.servlet.StopServlet;

// End imports

public class SimpleHttp implements Runnable {

		// Begin declarations

	private int port;
	private String resourcesDir;
	
	private Server server;
	private HashMap<String, String> parms = new HashMap<String, String>();
	public static SimpleHttp common;

		// End declarations
	
	public SimpleHttp(int port, String resourcesDir) {
		this.port = port;
		this.resourcesDir = resourcesDir;
	}

	public String getResourcesDir() {
		return resourcesDir;
	}

	public void start() {
		// Begin start
		new Thread(this).start();
		common = this;
		// End start
	}
	
	public void stop() {
		// Begin stop
		try {
			server.stop();
			common = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// End stop
	}
	
	public void run() {
		try {
			server = new Server();
	
			ServletContextHandler sHandler = defineServletContextHandler();
			server.setHandler(sHandler);

			HttpConfiguration config = new HttpConfiguration();
		    config.setRequestHeaderSize(65535);
		    ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(config));
		    http.setPort(port);
		    server.setConnectors(new Connector[] {http});
		    
			server.start();
			server.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ServletContextHandler defineServletContextHandler() {
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		handler.setContextPath("/simple");
		ResourceCollection recoll = new ResourceCollection();
		Resource[] resource = new Resource[2]; 
		resource[0] = new SimplePath();
		resource[1] = new PathResource(new File(resourcesDir));
		recoll.setResources(resource);
		handler.setBaseResource(recoll);
		
		handler.addServlet(RootServlet.class, "/root/*");
		handler.addServlet(StopServlet.class, "/stop/*");
		handler.addServlet(DefaultServlet.class, "/");

		for (String name: parms.keySet()) {
			handler.setInitParameter(name, parms.get(name));
		}

		return handler;
	}
	
	public void setInitParameter(String name, String value) {
		parms.put(name,  value);
	}
	
	public static void main(String[] args) {
		
		try {
		
			// Start server test
			
			SimpleHttp http = new SimpleHttp(8083, args[0]); 

//			Set init parameters before starting
//			http.setInitParameter("parm.id", "value");

			http.start();
						
			// End server test
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
