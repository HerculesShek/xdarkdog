package com.xdarkdog.web.util.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.xdarkdog.web.util.OrderInformation;

public class OrderDataUtil {
	public static List<OrderData> parseOrderInformationToOrderData(List<OrderInformation> infos){
		List<OrderData> datas = new ArrayList<OrderData>();
		HashMap<String, OrderData> data_map = new HashMap<String, OrderData>();
		for (OrderInformation i : infos) {
			OrderDetailData detailData = new OrderDetailData();
			detailData.setFruit_count(i.getFruit_count());
			detailData.setName(i.getName());
			detailData.setPrice(i.getPrice());
			detailData.setMeasurement_type(i.getMeasurement_type());
			detailData.setLevel(i.getLevel());
			if (data_map.containsKey(i.getOrder_id())) { // 已有 增加订单详情
				data_map.get(i.getOrder_id()).getDetails().add(detailData);
			} else { // 还没有这个订单
				OrderData orderData = new OrderData();
				orderData.setOrder_id(i.getOrder_id());
				orderData.setOrder_type(i.getOrder_type());
				orderData.setSubscribe_delivery_time(i.getSubscribe_delivery_time());
				orderData.setCreate_time(i.getCreate_time());
				orderData.setStatus(i.getStatus());
				orderData.setRealname(i.getRealname());
				orderData.setGender(i.getGender());
				orderData.setPhone(i.getPhone());
				orderData.setLocation(i.getLocation());
				orderData.setFruit_shop_name(i.getFruit_shop_name());
				orderData.setComm_name(i.getComm_name());
				List<OrderDetailData> detail_datas = new ArrayList<OrderDetailData>();
				detail_datas.add(detailData);
				orderData.setDetails(detail_datas);
				data_map.put(i.getOrder_id(), orderData);
			}
		}
		
		for (Entry<String, OrderData> entry: data_map.entrySet()) {
			OrderData value = entry.getValue();
			datas.add(value);
		}
		return datas;
	}
}
