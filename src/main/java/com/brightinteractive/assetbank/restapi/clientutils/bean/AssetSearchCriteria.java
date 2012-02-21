package com.brightinteractive.assetbank.restapi.clientutils.bean;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.brightinteractive.assetbank.restapi.clientutils.util.CollectionUtils;


public class AssetSearchCriteria 
{
	//param name constants for the Asset Bank search..
	private static final String PARAM_ACCESSLEVELS = "permissionCategoryForm.categoryIds";
	private static final String PARAM_ORIGINALFILENAME = "filename";
	
	//member variables for search criteria values...
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
