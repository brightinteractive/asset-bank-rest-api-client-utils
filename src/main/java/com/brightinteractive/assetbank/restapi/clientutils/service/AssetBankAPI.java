package com.brightinteractive.assetbank.restapi.clientutils.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.brightinteractive.assetbank.restapi.clientutils.bean.AssetSearchCriteria;
import com.brightinteractive.assetbank.restapi.clientutils.client.AssetBankAPIClient_1_3;
import com.brightinteractive.assetbank.restapi.representations.AccessLevelRepr;
import com.brightinteractive.assetbank.restapi.representations.AssetRepr;
import com.brightinteractive.assetbank.restapi.representations.CategoryRepr;
import com.brightinteractive.assetbank.restapi.representations.LightweightAssetRepr;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * Class that provides methods for calling actions on the Asset Bank API version 1.2.
 * Usage: Construct an instance of this object providing, as a string, the URL of the 
 * rest API on the Asset Bank that you want to access...
 * 
 * e.g. AssetBankAPI_1_2 api = new AssetBankAPI_1_2("http://yourdomain.com/asset-bank/rest");
 * 
 * ...then call the required API methods to retrieve Asset Bank API representations. The representation
 * objects that this class returns can be found in the 'Asset Bank REST API Reprs' package.
 * 
 * Currently supported operations:
 * 
 * 	- findCategories - find all categories in Asset Bank matching the given criteria (currently just the
 * 					   parent id which restricts the returned categories to those below the given parent)
 * 					   results are returned as a collection of CategoryRepr objects each containing their
 * 					   own collection of CategoryRepr's representing their children.
 * 
 * 	- findAccessLevels - find all access levels in Asset Bank matching the given criteria (currently just the
 * 					     parent id which restricts the returned access levels to those below the given parent)
 * 					     results are returned as a collection of AccessLevelRepr objects each containing their
 * 					     own collection of AccessLevelRepr's representing their children.
 * 
 * 	- findAssets	 - find all assets in Asset Bank matching the given criteria (specified via the 
 * 					   AssetSearchCriteria object).
 * 					   results are returned as a list of LightweightAssetRepr objects ordered according to the
 * 					   default search ordering of the Asset Bank in question.
 * 
 *  - getAsset	- get a full asset representation from the Asset Bank API
 *  
 *  - getAssetFile - request that the given asset's file is saved to the given file location. Returns a file object
 * 
 * 
 * @author Bright Interactive
 *
 */
public class AssetBankAPI 
{
	private AssetBankAPIClient_1_3 client = null;
	
	
	public AssetBankAPI (String abRootUrl)
	{
		this.client = new AssetBankAPIClient_1_3(abRootUrl);
	}
	
	
	public Collection<CategoryRepr> findCategories (long parentId)
	{
		//TODO - what to do about expected parameter names? Should they be part of the resource in the same way the url is?
		WebResource categorySearchResource = client.getClient().resource(client.getRootService().categorySearchUrl.toString()+"?parentId="+parentId);	
		Collection<CategoryRepr> categoryReprs = this.requestGET_XML(categorySearchResource, new GenericType<Collection<CategoryRepr>>() {});
		return categoryReprs;
	}
	
	
	
	public Collection<AccessLevelRepr> findAccessLevels (long parentId)
	{
		//TODO - what to do about expected parameter names? Should they be part of the resource in the same way the url is?
		WebResource alSearchResource = client.getClient().resource(client.getRootService().accessLevelSearchUrl.toString()+"?parentId="+parentId);	
		Collection<AccessLevelRepr> alReprs = this.requestGET_XML(alSearchResource, new GenericType<Collection<AccessLevelRepr>>() {});
		return alReprs;
	}
		
	
	
	public List<LightweightAssetRepr> findAssets (AssetSearchCriteria criteria)
	{
		WebResource assetSearchResource = client.getClient().resource(client.getRootService().assetSearchUrl.toString()+"?"+criteria.getQueryString());	
		List<LightweightAssetRepr> assetReprs = this.requestGET_XML(assetSearchResource, new GenericType<List<LightweightAssetRepr>>() {});
		return assetReprs;
	}
	
	
	
	public AssetRepr getAsset (long assetId)
	{
		WebResource assetResource = client.getClient().resource(client.getRootService().assetsUrl.toString() + "/" + assetId);
		AssetRepr asset = this.requestGET_XML(assetResource, AssetRepr.class);	
		return asset;
	}
	
	
	public File getAssetFile (long assetId, String assetFilePath)
	{
		WebResource assetContentResource = client.getClient().resource(client.getRootService().assetsUrl.toString() + "/" + assetId + "/content");
		ClientResponse response = this.requestGET(assetContentResource);	
		InputStream stream = response.getEntity(InputStream.class);
		File file = new File(assetFilePath);
		
		try
		{
			//read the response stream to the given file...
			OutputStream out=new FileOutputStream(file);
			
			byte[] buffer = new byte[1024];
			int length;
			
			while((length=stream.read(buffer))>0)
			{
			  out.write(buffer,0,length);
			}
			
			out.close();
			stream.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException ("Unable to save asset file ", e);
		}
		
		return file;
	}
	
	
	
	//============================================
	//Private util / helper methods....
	//============================================
	
	
	private <T> T requestGET_XML (WebResource resource, GenericType<T> type)
	{
		T reprs;
		try
		{
			reprs = resource.type(MediaType.APPLICATION_XML).get(type);
		}
		catch (Exception e)
		{
			throw new RuntimeException ("API GET request for XML failed ", e);
		}
		
		return reprs;
	}
	
	
	private <T> T requestGET_XML (WebResource resource, Class<T> type)
	{
		T reprs;
		try
		{
			reprs = resource.type(MediaType.APPLICATION_XML).get(type);
		}
		catch (Exception e)
		{
			throw new RuntimeException ("API GET request for XML failed ", e);
		}
		
		return reprs;
	}
	
	private ClientResponse requestGET (WebResource resource)
	{
		ClientResponse response;
		try
		{
			response = resource.get(ClientResponse.class);
		}
		catch (Exception e)
		{
			throw new RuntimeException ("API GET request failed ", e);
		}
		
		if (response.getStatus() > 300)
		{
			throw new RuntimeException ("API GET request failed. Response code "+response.getStatus());
		}
		
		return response;
	}
}
