package com.levent.pcd.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Tailors{
	
	@Id private String username;
	@Default
	private List<String> categoryList = new ArrayList<>();
	@Default
	private List<String> searchList = new ArrayList<>();
	
	public void addToCategory(String cat) {
		if( categoryList.contains(cat) ) {
			int index = categoryList.indexOf(cat);
			if(index > 0) {
				String rep = categoryList.get(index-1);
				categoryList.set(index, rep);
				categoryList.set(index-1, cat);
			}
		}else if( categoryList.size() == 5 ) {
			categoryList.add(0, cat);
			categoryList.remove(5);
		}
		else
			categoryList.add(0, cat);
	}
	
	public void addToSearch(String search) {
		if( searchList.contains(search)) {
			searchList.remove(search);
			searchList.add(0, search);
		}else if( searchList.size() == 3 ) {
			searchList.add(0, search);
			searchList.remove(3);
		}
		else
			searchList.add(0, search);
	}
	
}
