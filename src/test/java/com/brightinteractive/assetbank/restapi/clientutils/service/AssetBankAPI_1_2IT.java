package com.brightinteractive.assetbank.restapi.clientutils.service;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import com.brightinteractive.assetbank.restapi.clientutils.bean.AssetSearchCriteria;
import com.brightinteractive.assetbank.restapi.representations.AccessLevelRepr;
import com.brightinteractive.assetbank.restapi.representations.CategoryRepr;



public class AssetBankAPI_1_2IT 
{
	private static final String TEST_ASSET_BANK_URL = "http://192.168.1.243:8080/asset-bank/rest";
	//private static final String TEST_ASSET_BANK_URL = "http://localhost:8080/asset-bank-current/rest";
	private static final String NOT_ASSET_BANK_URL = "this-is-not-an-asset-bank";
	private AssetBankAPI_1_2 apiClient;
	
	@Before
	public void setUp()
	{
		apiClient = new AssetBankAPI_1_2(TEST_ASSET_BANK_URL);
	}
	
	
	
	@Test(expected = RuntimeException.class)
	public void findCategoriesWhereUrlDoesNotPointToAnAssetBank()
	{
		AssetBankAPI_1_2 invalidApi = new AssetBankAPI_1_2(NOT_ASSET_BANK_URL);
		invalidApi.findCategories(1);
	}

	
	
	@Test(expected = RuntimeException.class)
	public void findChildCategoriesWhereParentDoesntExist()
	{
		//get all categories from the Asset Bank api..
		Collection<CategoryRepr> categories = apiClient.findCategories(-1);
		SortedSet<Long> catIds = new TreeSet<Long>();
		for (CategoryRepr category : categories)
		{
			catIds.add(category.id);
		}
			
		//use the set to identify an id that doesn't exist
		long testCategoryId = catIds.last()+999;
		
		//poll the api with that id
		apiClient.findCategories(testCategoryId);
	}
	
	
	
	@Test(expected = RuntimeException.class)
	public void findAccessLevelWhereUrlDoesNotPointToAnAssetBank()
	{
		AssetBankAPI_1_2 invalidApi = new AssetBankAPI_1_2(NOT_ASSET_BANK_URL);
		invalidApi.findAccessLevels(1);
	}

	
	
	@Test(expected = RuntimeException.class)
	public void findChildAccessLevelWhereParentDoesntExist()
	{
		//get all access levels from the Asset Bank api..
		Collection<AccessLevelRepr> als = apiClient.findAccessLevels(-1);
		SortedSet<Long> alIds = new TreeSet<Long>();
		for (AccessLevelRepr al : als)
		{
			alIds.add(al.id);
		}
			
		//use the set to identify an id that doesn't exist
		long testCategoryId = alIds.last()+999;
		
		//poll the api with that id
		apiClient.findAccessLevels(testCategoryId);
	}
	
	
	
	@Test(expected = RuntimeException.class)
	public void findAssetsWhereUrlDoesNotPointToAnAssetBank ()
	{
		AssetBankAPI_1_2 invalidApi = new AssetBankAPI_1_2(NOT_ASSET_BANK_URL);
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.addAccessLevelId(1);
		invalidApi.findAssets(criteria);
	}
	

	
	@Test(expected = RuntimeException.class)
	public void findAssetsWithEmptySearchCriteria ()
	{
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		apiClient.findAssets(criteria);
	}
	
	
	//findChildCategoriesWhereParentHasNoChildren
	//findChildCategoriesForParentThatHasSingleChild
	//findChildCategoriesForParentThatHasMultipleChildren
	//findChildAccessLevelWhereParentHasNoChildren
	//findChildAccessLevelForParentThatHasSingleChild
	//findChildAccessLevelForParentThatHasMultipleChildren
	//findAssetsWithAccessLevelThatMatchesNoAssets
	//findAssetsWithAccessLevelThatMatchesSingleAsset
	//findAssetsWithAccessLevelThatMatchesMultipleAssets
	
	//findAssetsWithOriginalFilenameThatMatchesNoAssets	
	//findAssetsWithOriginalFilenameThatMatchesSingleAssets
	//findAssetsWithOriginalFilenameThatMatchesMultipleAssets
	//findAssetsWithFullyPopulatedSearchCriteria
}
