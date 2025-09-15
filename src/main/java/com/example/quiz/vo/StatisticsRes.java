package com.example.quiz.vo;

import java.util.List;

public class StatisticsRes extends BasicRes {

	private List<StatisticsVo> statisticsVoList;

	public StatisticsRes() {
		super();
	}

	public List<StatisticsVo> getStatisticsVoList() {
		return statisticsVoList;
	}

	public void setStatisticsVoList(List<StatisticsVo> statisticsVoList) {
		this.statisticsVoList = statisticsVoList;
	}

	public StatisticsRes(int code, String message) {
		super(code, message);
	}

	public StatisticsRes(int code, String message, List<StatisticsVo> statisticsVoList) {
		super(code, message);
		this.statisticsVoList = statisticsVoList;
	}

}
