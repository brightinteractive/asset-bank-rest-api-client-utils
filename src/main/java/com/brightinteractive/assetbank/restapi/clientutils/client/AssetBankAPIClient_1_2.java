package com.brightinteractive.assetbank.restapi.clientutils.client;

import com.brightinteractive.assetbank.restapi.representations.RootRepr;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.FeaturesAndProperties;

//TODO Restructure to prepare for potential 1.3 client (refactor common stuff?)
public class AssetBankAPIClient_1_2 
{
	private RootRepr.RootServices_v_1_2 assetBankAPI;
	private Client clientInstance = null;
	
	
	public AssetBankAPIClient_1_2 (String abRootUrl)
	{
		initAPI(abRootUrl);
	}
	
	
	private void initAPI (String abRootUrl)
	{
		if (assetBankAPI==null)
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

			clientInstance = Client.create(clientConfig);
			clientInstance.setFollowRedirects(false);
	
			WebResource assetBankResource = clientInstance.resource(abRootUrl);
			
			// Get root representation:
			RootRepr assetBankRoot;
			try
			{
				assetBankRoot = assetBankResource.get(RootRepr.class);
			}
			catch (Exception e)
			{
				throw new RuntimeException ("Rest API call failed with HTTP error code greater than 300", e);
			}
			
			// Get the dependencies service:
			assetBankAPI = assetBankRoot.api_version_1_2;
		}
	}
	
	
	
	public RootRepr.RootServices_v_1_2 getRootService ()
	{
		return this.assetBankAPI;
	}
	
	public Client getClient ()
	{
		return this.clientInstance;
	}
}
