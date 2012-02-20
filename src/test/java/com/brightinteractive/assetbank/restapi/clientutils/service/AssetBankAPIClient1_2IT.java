package com.brightinteractive.assetbank.restapi.clientutils.service;

import org.junit.Before;
import org.junit.Test;



public class AssetBankAPIClient1_2IT 
{
	//private static final String TEST_ASSET_BANK_URL = "http://localhost:8080/asset-bank-current/rest/";

	//private AssetBankAPIClient1_2 apiClient;
	
	@Before
	public void setUp()
	{
		//apiClient = new AssetBankAPIClient1_2(TEST_ASSET_BANK_URL);
	}
	
	
	@Test(expected = RuntimeException.class)
	public void findCategoriesWhereUrlDoesNotPointToAnAssetBank()
	{
		AssetBankAPIClient1_2 invalidApiClient = new AssetBankAPIClient1_2("this-is-not-an-asset-bank");
		invalidApiClient.findCategories(1, 1);
	}

	
	/*@Test
	public void findChildCategoriesWhereParentDoesntExist()
	{
		//get all categories from the Asset Bank api..
		Collection<CategoryRepr> categories = apiClient.findCategories(-1, -1);
		SortedSet<Long> catIds = new TreeSet<Long>();
		for (CategoryRepr category : categories)
		{
			catIds.add(category.id);
		}
			
		//use the set to identify an id that doesn't exist
		long testCategoryId = catIds.last()+999;
		
		//poll the api with that id
		Collection<CategoryRepr> matchingCategories = apiClient.findCategories(testCategoryId, -1);
		Assert.assertTrue(matchingCategories.isEmpty());				
	}*/
	
	/*@Test(expected = RuntimeException.class)
	public void findUserVisibleCategoriesWhereUserDoesntExist()
	{
	}*/
	
	//findChildCategoriesWhereParentHasNoChildren
	//findChildCategoriesForParentThatHasSingleChild
	//findChildCategoriesForParentThatHasMultipleChildren
	//findUserVisibleCategoriesWhereUserCanSeeNoCategories
	//findUserVisibleCategoriesForUserThatCanSeeSingleCategory
	//findUserVisibleCategoriesForUserThatCanSeeMultipleCategories
	//findUserVisibleCategoriesForParent / No / Single / Multiple
	
	//findAssetsWhereUrlDoesNotPointToAnAssetBank
	//findAssetsWithEmptySearchCriteria
	//findAssetsWithSearchCriteriaThatMatchesNoAssets
	//findAssetsWithSearchCriteriaThatMatchesSingleAsset
	//findAssetsWithSearchCriteriaThatMatchesMultipleAssets
}
