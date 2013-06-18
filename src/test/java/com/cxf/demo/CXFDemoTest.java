package com.cxf.demo;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;

/**
 * A sample test program for CXFDemoService
 * Makes use of JSONAssert, developed by SkyScreamer (http://jsonassert.skyscreamer.org/)
 * @author Obaid.Siddiqui
 *
 */
public class CXFDemoTest {
	
	String endpointUrl;

	@Before
	public void setUp() throws Exception {
		endpointUrl = "http://localhost:8080/CXFDemoService-1.0/customerservice/";
	}

	@Test
	public void testCustomer() {
		try {
		
		WebClient client = WebClient.create(endpointUrl + "customers/123", "john", "johnspassword", null);
		Response r = client.get();
		assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
		String value = IOUtils.toString((InputStream)r.getEntity());
		System.out.println("Response: " + value);
		
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
		
	/**
	 * Test getting an Order and confirm the order using JSONAssert
	 * @throws Exception
	 */
	@Test
	public void testOrders() throws Exception {
		List<Object> providers = new ArrayList<Object>();
	    providers.add(new org.codehaus.jackson.jaxrs.JacksonJsonProvider());
	    
	    
		WebClient client = WebClient.create(endpointUrl + "orders/223", "john", "johnspassword", null);
		Response r = client.get();
		assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
		
		/*
		MappingJsonFactory factory = new MappingJsonFactory();
		JsonParser parser = factory.createJsonParser((InputStream)r.getEntity());
		JsonBean output = parser.readValueAs(JsonBean.class);
		assertEquals("Maple", output.getVal2()); */
		
		String value = IOUtils.toString((InputStream)r.getEntity());
		System.out.println("Response: " + value);
		
		String expected = "{\"Order\":{\"id\":223,\"description\":\"order 223\"}}";
		JSONAssert.assertEquals(expected, value, false);
	} 

}
