package com.brightinteractive.assetbank.restapi.clientutils.util;

import com.brightinteractive.assetbank.restapi.representations.RootRepr;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.FeaturesAndProperties;


public class ClientUtils 
{
	public static Client getClient ()
	{
		// Turn on the FEATURE_XMLROOTELEMENT_PROCESSING
		// (=com.sun.jersey.config.feature.XmlRootElementProcessing)
		// property. This is necessary to match the
		// com.sun.jersey.config.feature.XmlRootElementProcessing
		// init-param to the jersey servlet on the server side.  If we
		// didn't do this then the this client and the server would not
		// interoperate because they'd serialize the XML in different
		// ways.
		DefaultClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(FeaturesAndProperties.FEATURE_XMLROOTELEMENT_PROCESSING, Boolean.TRUE);

		Client clientInstance = Client.create(clientConfig);
		clientInstance.setFollowRedirects(false);
		return clientInstance;
	}
	
	
	
	public static RootRepr getRootReprFromClient (Client client, String rootUrl)
	{
		WebResource webResource = client.resource(rootUrl);
		
		// Get root representation:
		RootRepr rootResource;
		try
		{
			rootResource = webResource.get(RootRepr.class);
		}
		catch (Exception e)
		{
			throw new RuntimeException ("Rest API call failed ", e);
		}
		
		return rootResource;
	}
}
