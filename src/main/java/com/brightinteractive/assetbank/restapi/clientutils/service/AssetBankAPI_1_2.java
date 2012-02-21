package com.brightinteractive.assetbank.restapi.clientutils.service;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.brightinteractive.assetbank.restapi.clientutils.bean.AssetSearchCriteria;
import com.brightinteractive.assetbank.restapi.representations.AccessLevelRepr;
import com.brightinteractive.assetbank.restapi.representations.CategoryRepr;
import com.brightinteractive.assetbank.restapi.representations.LightweightAssetRepr;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

//TODO Javadoc...
//TODO Restructure to prepare for potential 1.3 client (refactor common stuff)
public class AssetBankAPI_1_2 
{
	private AssetBankAPIClient_1_2 client = null;
		
	public AssetBankAPI_1_2 (String abRootUrl)
	{
		this.client = new AssetBankAPIClient_1_2(abRootUrl);
	}
	
	
	//TODO Add the userId parameter supported by the categorySearch resource to this method (and associated tests)...
	public Collection<CategoryRepr> findCategories (long parentId)
	{
		//TODO - what to do about expected parameter names? Should they be part of the resource in the same way the url is?
		WebResource categorySearchResource = client.getClient().resource(client.getRootService().categorySearchUrl.toString()+"?parentId="+parentId);	
		Collection<CategoryRepr> categoryReprs = null;
		try
		{
			categoryReprs = categorySearchResource.type(MediaType.APPLICATION_XML).get(new GenericType<Collection<CategoryRepr>>() {});
		}
		catch (Exception e)
		{
			throw new RuntimeException ("Category search call failed ", e);
		}
		return categoryReprs;
	}
	
	
	//TODO Add the userId parameter supported by the accessLevelSearch resource to this method (and associated tests)...
	public Collection<AccessLevelRepr> findAccessLevels (long parentId)
	{
		//TODO - what to do about expected parameter names? Should they be part of the resource in the same way the url is?
		WebResource alSearchResource = client.getClient().resource(client.getRootService().accessLevelSearchUrl.toString()+"?parentId="+parentId);	
		Collection<AccessLevelRepr> alReprs = null;
		try
		{
			alReprs = alSearchResource.type(MediaType.APPLICATION_XML).get(new GenericType<Collection<AccessLevelRepr>>() {});
		}
		catch (Exception e)
		{
			throw new RuntimeException ("Access Level search call failed ", e);
		}
		return alReprs;
	}
		
		
	public List<LightweightAssetRepr> findAssets (AssetSearchCriteria criteria)
	{
		WebResource assetSearchResource = client.getClient().resource(client.getRootService().assetSearchUrl.toString()+"?"+criteria.getQueryString());	
		List<LightweightAssetRepr> assetReprs = null;
		try
		{
			assetReprs = assetSearchResource.type(MediaType.APPLICATION_XML).get(new GenericType<List<LightweightAssetRepr>>() {});
		}
		catch (Exception e)
		{
			throw new RuntimeException ("Asset search call failed ", e);
		}
		return assetReprs;
	}
}
