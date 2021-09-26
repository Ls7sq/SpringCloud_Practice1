package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Resource //same as Autowried
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("insert result: " + result);

        if (result > 0){
            return new CommonResult(200, "successful for insert & serverPort: "+ serverPort,result);
        }else{
            return new CommonResult(444,"unsuccessful for insert",null);
        }
    }

    @GetMapping (value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("result: " + payment+"checkcheck");

        if (payment != null){
            return new CommonResult(200, "successful for find & serverPort: "+ serverPort, payment);
        }else{
            return new CommonResult(444,"unsuccessful for find ID:" + id,null);
        }
    }

}
