package com.crystalis.model;

import java.util.List;

import com.crystalis.menubar.FragmentActive;

public class DataManager {
	// Variables
	private static DataManager instance = new DataManager();
	
	private FragmentActive currentTab;
	private List<String> countryList;
	private List<String> regionList;
	private String useralias;
	
	private Object data;

	public static DataManager getInstance() {
	    return instance;
	}

	public static void setInstance(DataManager instance) {
	    DataManager.instance = instance;
	}

	public FragmentActive getCurrentTab() {
	    return currentTab;
	}
	
	public List<String> getCountryList() {
	    return countryList;
	}

	public void setCountryList(List<String> countryList) {
	    this.countryList = countryList;
	}

	public void setCurrentTab(FragmentActive currentTab) {
	    this.currentTab = currentTab;
	}

	public Object getData() {
	    return data;
	}

	public void setData(Object data) {
	    this.data = data;
	}

	public List<String> getRegionList() {
	    return regionList;
	}

	public void setRegionList(List<String> regionList) {
	    this.regionList = regionList;
	}

	public String getUseralias() {
	    return useralias;
	}

	public void setUseralias(String useralias) {
	    this.useralias = useralias;
	}

}
