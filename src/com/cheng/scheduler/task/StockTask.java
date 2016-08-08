package com.cheng.scheduler.task;

import com.cheng.model.Stock;

public class StockTask implements Runnable{

	@Override
	public void run() {
		Stock.dao.setAllStockCount(80);
	}

}
