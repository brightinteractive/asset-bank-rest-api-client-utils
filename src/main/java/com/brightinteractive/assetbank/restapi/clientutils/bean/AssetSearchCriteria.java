package com.brightinteractive.assetbank.restapi.clientutils.bean;

import java.util.ArrayList;
import java.util.Collection;

import com.brightinteractive.assetbank.restapi.clientutils.util.CollectionUtils;


public class AssetSearchCriteria 
{
	//param name constants for the Asset Bank search..
	private static final String PARAM_DESCRIPTIVECATEGORIES = "permissionCategoryForm.categoryIds";
	
	//member variables for search criteria values...
	private Collection<Long> accessLevelIds;
	
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
	
	public String getQueryString ()
	{
		String query = "";
		if (this.accessLevelIds != null && this.accessLevelIds.size() > 0)
		{
			query = PARAM_DESCRIPTIVECATEGORIES + "=" + CollectionUtils.convertToString(CollectionUtils.convertLongsToStrings(this.accessLevelIds), ",");
		}
		return query;
	}
}
