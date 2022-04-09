package com.vietnam.lottery.content.back.customer;

import com.vietnam.lottery.business.customer.request.AddOrUpdateRequest;
import com.vietnam.lottery.business.customer.response.CustomerListResponse;
import com.vietnam.lottery.business.customer.service.CustomerService;
import com.vietnam.lottery.common.config.JwtUtil;
import com.vietnam.lottery.common.utils.ResultModel;
import com.vietnam.lottery.common.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@Api(tags = "客服配置")
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/addOrUpdate")
    @ApiOperation("新增或者修改")
    public ResultModel addOrUpdate(@RequestBody AddOrUpdateRequest request, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(JwtUtil.getHeader());
        request.setCreateBy(JwtUtil.parseToken(token));
        return ResultUtil.success(customerService.addOrUpdate(request));
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public ResultModel<CustomerListResponse> detail() {
        return ResultUtil.success(customerService.detail());
    }

    @PostMapping("/upload")
    @ApiOperation("上传")
    public ResultModel upload(@RequestParam("file") MultipartFile file) {
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
        fileName = "http://admin.redpz.com" + "/img/" + fileName;
        return ResultUtil.success(fileName);
    }
}
