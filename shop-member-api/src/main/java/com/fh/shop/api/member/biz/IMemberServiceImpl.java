package com.fh.shop.api.member.biz;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.Constans;
import com.fh.common.ResponseEnum;
import com.fh.common.ServerResponse;
import com.fh.shop.api.member.mapper.IMemberMapper;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.util.KeyUtil;
import com.fh.shop.api.member.vo.MemberVo;

import com.fh.util.Md5Util;
import com.fh.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("memberService")
@Transactional(rollbackFor = Exception.class)
public class IMemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberMapper memberMapper;


    @Override
    public ServerResponse login(String memberName, String password) {
        //验证是否为空
        if (StringUtils.isEmpty(memberName)||StringUtils.isEmpty(password)){
            return ServerResponse.error(ResponseEnum.MEMBER_LOGIN_NULL);
        }
        //验证用户是否正确

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("memberName",memberName);
        Member member = memberMapper.selectOne(queryWrapper);
        if (member==null){
            return ServerResponse.error(ResponseEnum.MEMBER_USERNAME_NULL);
        }

        //验证密码是否正确
        if (!Md5Util.md5(password).equals(member.getPassword())){
            return ServerResponse.error(ResponseEnum.MEMBER_PASSWORD_NULL);
        }
        String status = member.getStatus();
        if (status.equals(Constans.INACTIVE)){

            String mail = member.getEmail();
            String uuid = UUID.randomUUID().toString();
            String key = KeyUtil.buildActiveMemberKey(uuid);
            Long id = member.getId();
            //存放到redis中
            RedisUtil.setex(key,id+"",10*60);
            Map<String,String>map=new HashMap<>();
            map.put("mail",mail);
            map.put("uuid",uuid);
            return ServerResponse.error(ResponseEnum.MEMBER_LOGIN_INACTIVE,map);

        }
        //生成签名
        //创建memberVo
        MemberVo memberVo = new MemberVo();
        Long id = member.getId();
        memberVo.setId(id);
        memberVo.setMemberName(member.getMemberName());
        memberVo.setNickName(member.getNickName());
        //将java对象装换为json格式的字符串
        String memberVoJson = JSON.toJSONString(memberVo);
        //签名
        String sign = Md5Util.sign(memberVoJson,Constans.SECERT);
        //将信息存入到redis中
        RedisUtil.setex("member:"+id,"",30*60);
        //将用户信息和相应前台对应
        //将用户信息和签名通过，分隔成一个字符串传给前台
        //将用户信息进行base64编码
        String encodeToString = Base64.getEncoder().encodeToString(memberVoJson.getBytes());
        String signToString = Base64.getEncoder().encodeToString(sign.getBytes());
        return ServerResponse.success(encodeToString+"."+signToString);

    }


}
