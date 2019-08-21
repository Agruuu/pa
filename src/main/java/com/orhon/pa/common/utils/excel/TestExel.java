package com.orhon.pa.common.utils.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestExel {

	public static void main(String[] args) {

		Map<String, String> entity = new HashMap<String, String>();
		entity.put("id", "6");
		entity.put("parentId", "");
		entity.put("name", "6");
		Map<String, String> entity1 = new HashMap<String, String>();
		entity1.put("id", "5");
		entity1.put("parentId", "6");
		entity1.put("name", "5");
		Map<String, String> entity2 = new HashMap<String, String>();
		entity2.put("id", "4");
		entity2.put("parentId", "");
		entity2.put("name", "4");
		Map<String, String> entity3 = new HashMap<String, String>();
		entity3.put("id", "3");
		entity3.put("parentId", "4");
		entity3.put("name", "3");
		Map<String, String> entity4 = new HashMap<String, String>();
		entity4.put("id", "2");
		entity4.put("parentId", "4");
		entity4.put("name", "2");
		Map<String, String> entity5 = new HashMap<String, String>();
		entity5.put("id", "1");
		entity5.put("parentId", "5");
		entity5.put("name", "1");
		Map<String, String> entity6 = new HashMap<String, String>();
//		entity6.put("id", "7");
//		entity6.put("parentId", "");
//		entity6.put("name", "7");
		
		List<Map> dataList = new ArrayList<Map>();
		
		dataList.add(entity);
		dataList.add(entity1);
		dataList.add(entity2);
		dataList.add(entity3);
		dataList.add(entity4);
		dataList.add(entity5);
//		dataList.add(entity6);
		
		for(int i = 0 ; i<dataList.size(); i++){
			int parent;
			if("".equals(dataList.get(i).get("parentId"))){
				parent = i;
				for(int j = 0 ; j<dataList.size(); j++){
					if(dataList.get(j).get("parentId").equals(dataList.get(parent))){
						
					}
					
				}
			}
			
		}
	}

}
