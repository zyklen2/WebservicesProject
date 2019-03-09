/**
 *
 */
package org.dhbw.mosbach.ai;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

/**
 * @author Alexander.Auch
 *
 */
@RunWith(Arquillian.class)
@DefaultDeployment
@RunAsClient
public class TestHelloWorldService
{
	@ArquillianResource
	private URL url;

	@Test
	public void testHelloWorldService() throws Exception
	{
		// use Apache CXF dynamic client
		// see http://cxf.apache.org/docs/dynamic-clients.html
		final JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		final Client helloWorldClient = dcf.createClient(getWsdlUrl());

		// Invoke helloWorld operation on service
		final Object[] result = helloWorldClient.invoke("helloWorld");

		Assert.assertTrue((result != null) && (result.length == 1));
		Assert.assertTrue(result[0] instanceof String);
		Assert.assertEquals("hello world", result[0]);
	}

	private URL getWsdlUrl()
	{
		try
		{
			return url.toURI().resolve("helloWorldService?wsdl").toURL();
		} catch (final MalformedURLException | URISyntaxException e)
		{
			throw new RuntimeException(e);
		}
	}
}
