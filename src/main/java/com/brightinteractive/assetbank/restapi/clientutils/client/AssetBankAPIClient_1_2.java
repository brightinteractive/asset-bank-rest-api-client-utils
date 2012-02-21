package com.brightinteractive.assetbank.restapi.clientutils.client;

import com.brightinteractive.assetbank.restapi.clientutils.util.ClientUtils;
import com.brightinteractive.assetbank.restapi.representations.RootRepr;
import com.sun.jersey.api.client.Client;

public class AssetBankAPIClient_1_2 
{
	private RootRepr.RootServices_v_1_2 assetBankAPI;
	private Client clientInstance = null;
	
	
	public AssetBankAPIClient_1_2 (String abRootUrl)
	{
		initAPI(abRootUrl);
	}
	
	
	private void initAPI (String abRootUrl)
	{
		if (assetBankAPI==null)
		{
			clientInstance = ClientUtils.getClient();
			RootRepr assetBankRoot = ClientUtils.getRootReprFromClient(clientInstance, abRootUrl);
			assetBankAPI = assetBankRoot.api_version_1_2;
		}
	}
	
	public RootRepr.RootServices_v_1_2 getRootService ()
	{
		return this.assetBankAPI;
	}
	
	public Client getClient ()
	{
		return this.clientInstance;
	}
}
