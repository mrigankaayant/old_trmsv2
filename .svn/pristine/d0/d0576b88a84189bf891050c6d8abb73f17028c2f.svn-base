package com.ayantsoft.trms.jsf.converter;

import java.util.Arrays;
import java.util.List;

public class ListStringConverter{

	public static List<String> getAsList(String value){
		List<String> preffredLocation = null;
		if(value != null && !value.isEmpty()){
			preffredLocation = Arrays.asList(value.split("#"));
		}
		return preffredLocation;
	}


	public static String getAsString(List<String> value){
		String listString = null;
		if(value != null){
			listString = String.join("#", value);
		}
		return listString;
	}
}