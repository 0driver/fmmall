package com.qfedu.fmmall.service;

import com.qfedu.fmmall.vo.ResultVO;

public interface UserService {
    //用户注册
    public ResultVO userResgit(String username,String pwd);

    //用户登录
    public ResultVO checkLogin(String username,String pwd);
}
