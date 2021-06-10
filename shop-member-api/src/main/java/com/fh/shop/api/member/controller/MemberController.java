package com.fh.shop.api.member.controller;

import com.fh.common.ServerResponse;
import com.fh.shop.api.common.Constants;
import com.fh.shop.api.member.biz.IMemberService;
import com.fh.shop.api.member.util.KeyUtil;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/members")
@CrossOrigin
@Api(tags="会员接口")
@Slf4j
public class MemberController {

    @Resource(name="memberService")
    private IMemberService memberService;

    @Autowired
    private HttpServletRequest request;

    @ApiOperation(value="会员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberName",value = "会员名",dataType = "java.lang.String",required = true),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "java.lang.String",required = true)
    })
    @PostMapping("/login")
    public ServerResponse login(String memberName, String password){
        log.debug("-----*****登录*****-----");
        return memberService.login(memberName,password);
    }


    @ApiOperation(value="注销")
    @GetMapping("/logout")
    public ServerResponse logout(){
        MemberVo memberVo = (MemberVo) request.getAttribute(Constants.CURR_MEMBER);
        RedisUtil.del(KeyUtil.buildMemberKey(memberVo.getId()));
        return ServerResponse.success();
    }



}
