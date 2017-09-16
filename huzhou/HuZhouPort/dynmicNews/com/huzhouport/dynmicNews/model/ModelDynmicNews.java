package com.huzhouport.dynmicNews.model;

import com.huzhouport.dynmicNews.action.ModelActionDynmicNews;

public class ModelDynmicNews {
	private String titile;//标题
	private String date;//时间
	private String url;//连接
	private String contenct;// 返回详细内容
	private String photoUrl;//图片地址
	public String getTitile() {
		return titile;
	}
	public void setTitile(String titile) {
		this.titile = titile;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContenct() {
		return contenct;
	}
	public void setContenct(String contenct) {
		this.contenct = contenct;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public static void main(String[] args) throws Exception {
		new ModelActionDynmicNews().findDetailInfo();
	}
}
