package com.brightinteractive.assetbank.restapi.clientutils.service;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.brightinteractive.assetbank.restapi.representations.CategoryRepr;
import com.brightinteractive.assetbank.restapi.representations.LightweightAssetRepr;
import com.brightinteractive.assetbank.restapi.representations.RootRepr;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.FeaturesAndProperties;

//TODO Javadoc...
public class AssetBankAPIClient1_2 
{
	private RootRepr.RootServices_v_1_2 assetBankAPI;
	private Client clientInstance = null;
	private String abRootUrl = null;
	
	
	public AssetBankAPIClient1_2 (String abRootUrl)
	{
		this.abRootUrl = abRootUrl;
		initAPI();
	}
	
	
	public Collection<CategoryRepr> findCategories (long parentId, long userId)
	{
		//TODO - what to do about expected parameter names? Should they be part of the resource in the same way the url is?
		WebResource categorySearchResource = clientInstance.resource(assetBankAPI.categorySearchUrl.toString()+"?parentId="+parentId+"&userId="+userId);	
		Collection<CategoryRepr> categoryReprs = categorySearchResource.type(MediaType.APPLICATION_XML).get(new GenericType<Collection<CategoryRepr>>() {});
		return categoryReprs;
	}
	
	
	public List<LightweightAssetRepr> findAssets (String requestParameters)
	{
		WebResource assetSearchResource = clientInstance.resource(assetBankAPI.assetSearchUrl.toString()+"?"+requestParameters);	
		List<LightweightAssetRepr> assetReprs = assetSearchResource.type(MediaType.APPLICATION_XML).get(new GenericType<List<LightweightAssetRepr>>() {});
		return assetReprs;
	}
	
	private void initAPI ()
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
			// Create the jersey client. This is an expensive operation, apparently, hence the instance var.
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
}
