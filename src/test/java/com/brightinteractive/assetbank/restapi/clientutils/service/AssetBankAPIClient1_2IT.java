package com.brightinteractive.assetbank.restapi.clientutils.service;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import com.brightinteractive.assetbank.restapi.clientutils.bean.AssetSearchCriteria;
import com.brightinteractive.assetbank.restapi.representations.CategoryRepr;



public class AssetBankAPIClient1_2IT 
{
	private static final String TEST_ASSET_BANK_URL = "http://192.168.1.243:8080/asset-bank/rest";
	//private static final String TEST_ASSET_BANK_URL = "http://localhost:8080/asset-bank-current/rest";
	private static final String NOT_ASSET_BANK_URL = "this-is-not-an-asset-bank";
	private AssetBankAPIClient1_2 apiClient;
	
	@Before
	public void setUp()
	{
		apiClient = new AssetBankAPIClient1_2(TEST_ASSET_BANK_URL);
	}
	
	
	@Test(expected = RuntimeException.class)
	public void findCategoriesWhereUrlDoesNotPointToAnAssetBank()
	{
		AssetBankAPIClient1_2 invalidApiClient = new AssetBankAPIClient1_2(NOT_ASSET_BANK_URL);
		invalidApiClient.findCategories(1);
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
	
	
	//findChildCategoriesWhereParentHasNoChildren
	//findChildCategoriesForParentThatHasSingleChild
	//findChildCategoriesForParentThatHasMultipleChildren
	
	@Test(expected = RuntimeException.class)
	public void findAssetsWhereUrlDoesNotPointToAnAssetBank ()
	{
		AssetBankAPIClient1_2 invalidApiClient = new AssetBankAPIClient1_2(NOT_ASSET_BANK_URL);
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.addAccessLevelId(1);
		invalidApiClient.findAssets(criteria);
	}
	
	//findAssetsWithEmptySearchCriteria
	//findAssetsWithAccessLevelThatMatchesNoAssets
	//findAssetsWithAccessLevelThatMatchesSingleAsset
	//findAssetsWithAccessLevelThatMatchesMultipleAssets
	//findAssetsWithOriginalFilenameThatMatchesNoAssets
	//findAssetsWithOriginalFilenameThatMatchesSingleAssets
	//findAssetsWithOriginalFilenameThatMatchesMultipleAssets
	//findAssetsWithFullyPopulatedSearchCriteria
}
