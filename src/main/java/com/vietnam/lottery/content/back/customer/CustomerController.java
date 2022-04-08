package com.vietnam.lottery.content.back.customer;

import com.vietnam.lottery.business.customer.service.CustomerService;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@Api(tags = "客服配置")
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/upload")
    @ApiOperation("上传")
    public ResultModel add(@RequestParam("file") MultipartFile file) {
        //文件存放地址
        String filePath = "/home/wwwroot/lottery/img";
        //文件名称
        String fileName = file.getOriginalFilename();
        File fileDir = new File(filePath + "/" + fileName);
        try {
            file.transferTo(fileDir);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileName = filePath + "/" + fileName;
        return ResultUtil.success(fileName);
    }
}
