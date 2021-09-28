package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id){
        return "Thread Pool: " + Thread.currentThread().getName()
                + "\t" +" paymentInfo_OK, id: " + id + "\t";
    }
    //====fallback
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public String paymentInfo_Timeout(Integer id){

//        int age = 10/0;
        int timeNumber = 3000;
        try{
            TimeUnit.MILLISECONDS.sleep(timeNumber);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "Thread Pool: " + Thread.currentThread().getName()
                + "\t" +" paymentInfo_Timeout, id: " + id + "\t"
                + "time consuming: " + timeNumber;
    }

    public String paymentInfo_TimeoutHandler(Integer id){
        return "Thread Pool: " + Thread.currentThread().getName()
                + "\t" +" paymentInfo_TimeoutHandler, id: " + id + "\t"
                + "Sorry 8001 Timeout or operation abnormal";
    }

    //====circuit breaker
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value="true"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60")
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id < 0){
            throw new RuntimeException("*********id can not load");
        }
        String serialNumber = IdUtil.simpleUUID();  //UUID.randomUUID().toString()

        return Thread.currentThread().getName()
                + "\t" + "successful, serial number: " + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id can not load, please try later. id: " + id;
    }
}
