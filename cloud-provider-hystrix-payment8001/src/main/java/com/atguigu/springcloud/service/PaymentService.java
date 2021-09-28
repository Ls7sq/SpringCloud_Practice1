package com.atguigu.springcloud.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id){
        return "Thread Pool: " + Thread.currentThread().getName()
                + "\t" +" paymentInfo_OK, id: " + id + "\t";
    }

    public String paymentInfo_Timeout(Integer id){

        int timeNumber = 3;
        try{
            TimeUnit.SECONDS.sleep(timeNumber);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "Thread Pool: " + Thread.currentThread().getName()
                + "\t" +" paymentInfo_Timeout, id: " + id + "\t"
                + "time consuming: " + timeNumber;
    }
}
