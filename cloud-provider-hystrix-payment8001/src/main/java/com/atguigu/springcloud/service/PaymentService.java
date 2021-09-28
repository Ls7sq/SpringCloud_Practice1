package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id){
        return "Thread Pool: " + Thread.currentThread().getName()
                + "\t" +" paymentInfo_OK, id: " + id + "\t";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_Timeout(Integer id){

        int age = 10/0;
        int timeNumber = 5;
//        try{
//            TimeUnit.SECONDS.sleep(timeNumber);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
        return "Thread Pool: " + Thread.currentThread().getName()
                + "\t" +" paymentInfo_Timeout, id: " + id + "\t"
                + "time consuming: " + timeNumber;
    }

    public String paymentInfo_TimeoutHandler(Integer id){
        return "Thread Pool: " + Thread.currentThread().getName()
                + "\t" +" paymentInfo_TimeoutHandler, id: " + id + "\t"
                + "Sorry Timeout or operation abnormal";
    }
}
