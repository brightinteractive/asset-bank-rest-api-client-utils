package com.brightinteractive.assetbank.restapi.clientutils.util;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionUtils 
{
	public static String convertToString (Collection<String> strings, String delimiter)
	{
		if (strings != null)
		{
			StringBuilder result = new StringBuilder();
		    for(String string : strings) 
		    {
		        result.append(string);
		        result.append(delimiter);
		    }
		    return result.substring(0, result.length() - 1);
		}
		return null;
	}
	
	
	public static Collection<String> convertLongsToStrings (Collection<Long> longs)
	{
		if (longs != null)
		{
			Collection<String> strings = new ArrayList<String>(longs.size());
			for (long longNumber : longs)
			{
				strings.add(String.valueOf(longNumber));
			}
			return strings;
		}
		return null;
	}
}
