package com.atguigu.springcloud.alibaba.controller;

import com.atguigu.springcloud.alibaba.domain.CommonResult;
import com.atguigu.springcloud.alibaba.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StorageController {
    @Resource
    private StorageService storageService;

    @GetMapping("/storage/decrease")
    public CommonResult decrease(@RequestParam("productId") Long productId
            , @RequestParam("count")Integer count){
        storageService.decrease(productId, count);
        return new CommonResult(200,"storage decrease successful!!");
    }
}
