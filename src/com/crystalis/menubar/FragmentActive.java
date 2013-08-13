package com.crystalis.menubar;

public enum FragmentActive {
	TAB_HOME("home"),
	TAB_CITAS("citas"),
	TAB_CLIENTES("clientes"), 
	TAB_PRODUCTOS("productos"), 
	TAB_VENTAS("ventas"),
	TAB_REPORTES("reportes"), 
	TAB_QRREAD("qrread");
	
	private String value;
	
	private FragmentActive(String value) {
		this.value = value;
	}
	
	public String toString() {
		return value;
	}
}