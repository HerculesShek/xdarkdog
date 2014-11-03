package com.xdarkdog.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xdarkdog.dbutil.DaoSupport;
import com.xdarkdog.pojo.OrderDetail;

@Repository
public class OrderDetailDao extends DaoSupport {

    // 添加订单详情
    public int addOrderDatail(OrderDetail detail) {
        String sql = "INSERT INTO `ddcommunity`.`tbl_order_detail` (`order_id`, `fruit_id`, `fruit_count`, `level`) VALUES (?,?,?,?);";
        Object[] params = {detail.getOrder_id(), detail.getFruit_id(), detail.getFruit_count(), detail.getLevel()};
        return execOther(sql, params);
    }

    // 根据订单id获取所有的订单详情
    public List<OrderDetail> getDetailsByOrder_id(String orderId) {
        String sql = "SELECT * FROM ddcommunity.tbl_order_detail where order_id=?;";
        Object[] params = {orderId};
        return executeQuery(sql, OrderDetail.class, params);
    }

}
