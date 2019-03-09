package org.dhbw.mosbach.ai.service;

import javax.jws.WebService;

// see http://cxf.apache.org/docs/developing-a-service.html
@WebService(endpointInterface = "org.dhbw.mosbach.ai.service.HelloWorldService", serviceName = "helloWorldService")
public class HelloWorldServiceImpl implements HelloWorldService
{
	@Override
	public String helloWorld()
	{
		return "hello world";
	}
}
