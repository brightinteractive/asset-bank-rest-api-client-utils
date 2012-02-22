package com.brightinteractive.assetbank.restapi.clientutils.service;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.brightinteractive.assetbank.restapi.clientutils.bean.AssetSearchCriteria;
import com.brightinteractive.assetbank.restapi.representations.AccessLevelRepr;
import com.brightinteractive.assetbank.restapi.representations.AssetRepr;
import com.brightinteractive.assetbank.restapi.representations.AttributeValueRepr;
import com.brightinteractive.assetbank.restapi.representations.CategoryRepr;
import com.brightinteractive.assetbank.restapi.representations.LightweightAssetRepr;



public class AssetBankAPI_1_2IT 
{
	private AssetBankAPI_1_2 apiClient;
	
	//Test data ids etc...
	private static final long CATEGORY_PARENT_WITH_NO_CHILDREN = 27;
	private static final long CATEGORY_PARENT_WITH_SINGLE_CHILD = 28;
	private static final long CATEGORY_PARENT_WITH_MULTIPLE_CHILDREN = 29;
	private static final long ACCESS_LEVEL_PARENT_WITH_NO_CHILDREN = 35;
	private static final long ACCESS_LEVEL_PARENT_WITH_SINGLE_CHILD = 36;
	private static final long ACCESS_LEVEL_PARENT_WITH_MULTIPLE_CHILDREN = 37;
	private static final long ACCESS_LEVEL_CONTAINING_NO_ASSETS = 42;
	private static final long ACCESS_LEVEL_CONTAINING_SINGLE_ASSET = 43;
	private static final long ACCESS_LEVEL_CONTAINING_MULTIPLE_ASSETS = 44;
	private static final String ORIGINAL_FILENAME_NOT_MATCHED = "xcnhiow2mcfi9w9e39e3.gif";
	private static final long INVALID_ASSET_ID = -99;
	private static final long ATTRIBUTE_ID = 2;
	
	private static final long VALID_ASSET_ID = 295;
	private static final long VALID_ASSET_BYTE_SIZE = 3286;
	private static final String VALID_ASSET_FILENAME = "rest_api_test.gif";
	
	private static final String TEST_ASSET_BANK_URL = "http://192.168.1.16:8180/asset-bank-mysql-trunk/rest";
	private static final String NOT_ASSET_BANK_URL = "this-is-not-an-asset-bank";
	private static final String VALID_FILE_LOCATION = VALID_ASSET_FILENAME;
	
	
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
	
	
	@Test
	public void findChildCategoriesWhereParentHasNoChildren ()
	{
		Collection<CategoryRepr> categories = apiClient.findCategories(CATEGORY_PARENT_WITH_NO_CHILDREN);
		Assert.assertEquals(categories.size(), 0);
	}
	
	
	@Test
	public void findChildCategoriesForParentThatHasSingleChild ()
	{
		Collection<CategoryRepr> categories = apiClient.findCategories(CATEGORY_PARENT_WITH_SINGLE_CHILD);
		Assert.assertEquals(categories.size(), 1);
	}
	
	
	@Test
	public void findChildCategoriesForParentThatHasMultipleChildren ()
	{
		Collection<CategoryRepr> categories = apiClient.findCategories(CATEGORY_PARENT_WITH_MULTIPLE_CHILDREN);
		Assert.assertTrue(categories.size()>1);
	}
	
	
	@Test
	public void findChildAccessLevelsWhereParentHasNoChildren ()
	{
		Collection<AccessLevelRepr> als = apiClient.findAccessLevels(ACCESS_LEVEL_PARENT_WITH_NO_CHILDREN);
		Assert.assertEquals(als.size(), 0);
	}
	
	
	@Test
	public void findChildAccessLevelsForParentThatHasSingleChild ()
	{
		Collection<AccessLevelRepr> als = apiClient.findAccessLevels(ACCESS_LEVEL_PARENT_WITH_SINGLE_CHILD);
		Assert.assertEquals(als.size(), 1);
	}
	
	
	@Test
	public void findChildAccessLevelsForParentThatHasMultipleChildren ()
	{
		Collection<AccessLevelRepr> als = apiClient.findAccessLevels(ACCESS_LEVEL_PARENT_WITH_MULTIPLE_CHILDREN);
		Assert.assertTrue(als.size()>1);
	}
	
	
	
	@Test
	public void findAssetsWithAccessLevelThatMatchesNoAssets ()
	{
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.addAccessLevelId(ACCESS_LEVEL_CONTAINING_NO_ASSETS);
		List<LightweightAssetRepr> assets = apiClient.findAssets(criteria);
		Assert.assertEquals(assets.size(), 0);
	}
	
	
	@Test
	public void findAssetsWithAccessLevelThatMatchesSingleAssets ()
	{
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.addAccessLevelId(ACCESS_LEVEL_CONTAINING_SINGLE_ASSET);
		List<LightweightAssetRepr> assets = apiClient.findAssets(criteria);
		Assert.assertEquals(assets.size(), 1);
	}
	
	@Test
	public void findAssetsWithAccessLevelThatMatchesMultipleAssets ()
	{
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.addAccessLevelId(ACCESS_LEVEL_CONTAINING_MULTIPLE_ASSETS);
		List<LightweightAssetRepr> assets = apiClient.findAssets(criteria);
		Assert.assertTrue(assets.size()>1);
	}
	
	
	@Test
	public void findAssetsWithOriginalFilenameThatMatchesNoAssets ()
	{
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.setOriginalFilename(ORIGINAL_FILENAME_NOT_MATCHED);
		List<LightweightAssetRepr> assets = apiClient.findAssets(criteria);
		Assert.assertEquals(assets.size(), 0);
	}
	
	
	@Test
	public void findAssetsWithOriginalFilenameThatMatchesSingleAssets ()
	{
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.setOriginalFilename(VALID_ASSET_FILENAME);
		List<LightweightAssetRepr> assets = apiClient.findAssets(criteria);
		Assert.assertTrue(assets.size()>0);
	}
	
	
	@Test
	public void findAssetsWithFullyPopulatedSearchCriteria ()
	{
		AssetSearchCriteria criteria = new AssetSearchCriteria();
		criteria.setOriginalFilename(VALID_ASSET_FILENAME);
		criteria.addAccessLevelId(ACCESS_LEVEL_CONTAINING_MULTIPLE_ASSETS);
		List<LightweightAssetRepr> assets = apiClient.findAssets(criteria);
		Assert.assertTrue(assets.size()>0);
	}
	
	
	@Test(expected = RuntimeException.class)
	public void getAssetWithInvalidId ()
	{
		apiClient.getAsset(INVALID_ASSET_ID);
	}
	
	@Test
	public void getAssetWithValidId ()
	{
		AssetRepr repr = apiClient.getAsset(VALID_ASSET_ID);
		Assert.assertNotNull(repr);
		long id = -1;
		for (AttributeValueRepr attVal : repr.attributes)
		{
			if (attVal.id == ATTRIBUTE_ID)
			{
				id = Long.parseLong(attVal.value);
			}
		}
		Assert.assertEquals(VALID_ASSET_ID, id);
	}
	
	
	@Test(expected = RuntimeException.class)
	public void getAssetFileWithInvalidIdValidLocation()
	{
		apiClient.getAssetFile(INVALID_ASSET_ID, VALID_FILE_LOCATION);
	}
	
	@Test(expected = RuntimeException.class)
	public void getAssetFileWithValidIdInvalidLocation()
	{
		String filelocation = "";
		apiClient.getAssetFile(VALID_ASSET_ID, filelocation);
	}
	
	@Test
	public void getAssetFileWithValidIdValidLocation()
	{
		File file = apiClient.getAssetFile(VALID_ASSET_ID, VALID_FILE_LOCATION);
		Assert.assertEquals(VALID_ASSET_BYTE_SIZE, file.length());
	}
}
