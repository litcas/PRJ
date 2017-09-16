package com.huzhouport.electricreport.service;

import java.util.List;
import java.util.Map;

import com.huzhouport.electricreport.model.Boatman;
import com.huzhouport.electricreport.model.ElectricArrival;
import com.huzhouport.electricreport.model.ElectricReportNew;


public interface ElectricReportService {
	// 查询条数
	public Map<String, Integer> CountListByInfo(ElectricReportNew electricReportNew, int pageSize)
			throws Exception;
	// 查询本页
	public List<?> SearchListByInfo(ElectricReportNew electricReportNew, int pageNo, int pageSize)throws Exception;
	//某条电子报港
	public List<?> ElectricReportByReportId(int reportID) throws Exception;
	
	//增加电子报港信息
	public String SaveElectricReport(ElectricReportNew electricReportNew) throws Exception;
	//修改电子报港信息
	public Boolean UpdateElectricReport(ElectricReportNew electricReportNew) throws Exception;
	// 航线调整修改目的港 参数
	public String updateReportArrivalPort(ElectricReportNew electricReportNew,ElectricArrival electricArrival) throws Exception;
	// 设置船员信息
	public String SetBoatUserInfo(Boatman boatman) throws Exception;
	// 显示船员信息
	public List<?> ShowBoatUserInfo(Boatman boatman) throws Exception;
	// 港口列表
	public List<?> showPortList() throws Exception;
	//显示始发港
	public String showStartPort(String shipName) throws Exception;
	//历史航线
	public List<ElectricArrival> GetOldReport(ElectricReportNew electricreportnew) throws Exception;
}
