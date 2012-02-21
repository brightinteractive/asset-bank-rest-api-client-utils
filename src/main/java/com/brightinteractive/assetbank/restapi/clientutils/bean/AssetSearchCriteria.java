package com.brightinteractive.assetbank.restapi.clientutils.bean;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.brightinteractive.assetbank.restapi.clientutils.util.CollectionUtils;


/**
 * Object representing the request parameters passed to an Asset Bank REST API AssetSearchResource.
 * These parameters are a subset of the parameters that are used on the Asset Bank advanced search
 * page for the Asset Bank in question.
 * 
 * Currently support parameters are:
 * 
 * - access level ids - find assets in a particular access level, set either as a complete collection
 * 						add one at a time to the criteria
 * 
 * - original filename - find assets with a filename that matches the given search string (can include
 * 						 wildcards, i.e. the string "*.jpg" should return jpg assets.
 * 
 * 
 * @author Bright Interactive
 *
 */
public class AssetSearchCriteria 
{
	
	//param name constants for the Asset Bank search..
	private static final String PARAM_ACCESSLEVELS = "permissionCategoryForm.categoryIds";
	private static final String PARAM_ORIGINALFILENAME = "filename";
	
	
	//member variables for available search criteria values...
	private Collection<Long> accessLevelIds;
	private String originalFilename;
	
	
	public void setAccessLevelIds (Collection<Long> accessLevelIds)
	{
		this.accessLevelIds = accessLevelIds;
	}
	
	public void addAccessLevelId (long id)
	{
		if (this.accessLevelIds == null)
		{
			this.accessLevelIds = new ArrayList<Long>();
		}
		this.accessLevelIds.add(id);
	}
	
	public void setOriginalFilename (String originalFilename)
	{
		this.originalFilename = originalFilename;
	}
	
	
	public String getQueryString ()
	{
		String query = "";
		if (this.accessLevelIds != null && this.accessLevelIds.size() > 0)
		{
			query += PARAM_ACCESSLEVELS + "=" + CollectionUtils.convertToString(CollectionUtils.convertLongsToStrings(this.accessLevelIds), ",");
		}
		
		if (StringUtils.isNotEmpty(this.originalFilename))
		{
			if (StringUtils.isNotEmpty(query))
			{
				query += "&";
			}
			query += PARAM_ORIGINALFILENAME + "=" + this.originalFilename;
		}
		return query;
	}
}
