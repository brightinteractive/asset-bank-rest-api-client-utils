package com.brightinteractive.assetbank.restapi.clientutils;


public class AssetBankAPIClient 
{
	/*private RootRepr.RootServices_v_1_2 m_assetBankAPI;
	private static Client c_clientInstance = null;
	
	private static Client getClient ()
	{
		if (c_clientInstance == null)
		{
			DefaultClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(FeaturesAndProperties.FEATURE_XMLROOTELEMENT_PROCESSING, Boolean.TRUE);
			
			c_clientInstance = Client.create(clientConfig);
			c_clientInstance.setFollowRedirects(false);
		}
		
		return c_clientInstance;
	}
	
	
	private ClientResponse requestGETJSON(String url)
	{
		return requestGET(url, MediaType.APPLICATION_JSON_TYPE);
	}

	
	private ClientResponse requestGETXML(String url)
	{
		return requestGET(url, MediaType.APPLICATION_XML_TYPE);
	}

	
	private ClientResponse requestGET(String url, MediaType accept)
	{
		WebResource resource = getClient().resource(url);
		ClientResponse response = resource.accept(accept).get(ClientResponse.class);
		return response;
	}

	
	private ClientResponse requestPOST (String url)
	{
		return requestPOST(url, null);
	}
	
	
	private ClientResponse requestPOST (String url, MultivaluedMap<String, String> queryParams)
	{
		WebResource wrDeps = getClient().resource(url);		
		ClientResponse response = null;
		if (queryParams != null)
		{
			response = wrDeps.queryParams(queryParams).type("multipart/mixed").post(ClientResponse.class);
		}
		else
		{
			response = wrDeps.type("multipart/mixed").post(ClientResponse.class);
		}
		return response;
	}
	
	
	private ClientResponse requestDELETE (String url)
	{
		WebResource wrDeps = getClient().resource(url);		
		ClientResponse response = wrDeps.delete(ClientResponse.class);
		return response;
	}*/
}
