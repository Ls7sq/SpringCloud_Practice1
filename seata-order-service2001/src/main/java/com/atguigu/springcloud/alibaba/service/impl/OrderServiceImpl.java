package com.atguigu.springcloud.alibaba.service.impl;

import com.atguigu.springcloud.alibaba.dao.OrderDao;
import com.atguigu.springcloud.alibaba.domain.Order;
import com.atguigu.springcloud.alibaba.service.AccountService;
import com.atguigu.springcloud.alibaba.service.OrderService;
import com.atguigu.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    @Override
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("----->Initialize Order creating");
        orderDao.create(order);

        log.info("----->Order service invoke Storage service, Count refresh");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("----->Order service invoke Storage service, Count refresh successful");

        log.info("----->Order service invoke Account service, Money decrease");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("----->Order service invoke Account service, Money decrease successful");

        //修改订单状态 0 -> 1
        log.info("----->Update Order status");
        orderDao.update(order.getUserId(),0);
        log.info("----->Update Order status successful");

        log.info("----->Order finish");
    }
}
