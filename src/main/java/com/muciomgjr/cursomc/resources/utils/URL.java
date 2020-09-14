package com.muciomgjr.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	public static String decodeParam(String str){
		try {
			return URLDecoder.decode(str, "UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	public static List<Integer> convertionToIntList(String str){
		
		String[] vector = str.split(",");
		
		List<Integer> list = new ArrayList<>();
		
		for (int i = 0; i < vector.length; i++) {
			list.add(Integer.parseInt(vector[i]));
		}
		
		return list;
		
		//return Arrays.asList(str.split(",")).stream().map(c -> Integer.parseInt(c)).collect(Collectors.toList());
	}

}
