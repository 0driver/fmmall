package com.qfedu.fmmall.service.impl;

import com.qfedu.fmmall.dao.UsersMapper;
import com.qfedu.fmmall.entity.Users;
import com.qfedu.fmmall.service.UserService;
import com.qfedu.fmmall.utils.MD5Utils;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UsersMapper usersMapper;

    @Override
    @Transactional
    public ResultVO userResgit(String username, String pwd) {
        //1、查询该用户名是否已经注册
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        List<Users> users = usersMapper.selectByExample(example);
        //2、如果没被注册进行保存操作
        if(users.size()<=0){
            String md5Pwd= MD5Utils.md5(pwd);
            Users user=new Users();
            user.setUsername(username);
            user.setPassword(md5Pwd);
            user.setUserRegtime(new Date());
            user.setUserModtime(new Date());
            user.setUserImg("img/default.png");
            int i=usersMapper.insertUseGeneratedKeys(user);
            if(i>0){
                return new ResultVO(ResStatus.OK,"注册成功",user);
            }else{
                return new ResultVO(ResStatus.NO,"注册失败",null);
            }
        }else {
                return new ResultVO(ResStatus.NO,"用户名已存在",null);
        }
    }

    @Override
    public ResultVO checkLogin(String username, String pwd) {

        Example example=new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        //先查询数据库是否有该用户
        List<Users> users = usersMapper.selectByExample(example);
        if(users.size()<=0){
            return new ResultVO(ResStatus.NO,"登陆失败，用户名不存在",null);
        }else{
            String md5Pwd = MD5Utils.md5(pwd);
            if(md5Pwd.equals(users.get(0).getPassword())){
                //生成token
//                此map存放一些需要放到token里面的用户信息
                Map<String,Object> map=new HashMap<>();
                map.put("key1","fuckyou!");
                JwtBuilder builder = Jwts.builder();
                String token = builder.setSubject(username)//jwt payload里面的内容
                        .setIssuedAt(new Date())//设置token生成时间
                        .setId(users.get(0).getUserId() + "")//设置token的id为用户id
                        .setClaims(map)//map里面可以存放一些用户角色权限信息
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))//设置token到期时间为1天
                        .signWith(SignatureAlgorithm.HS256, "helloworld").compact();//设置hash函数和密码

                return new ResultVO(ResStatus.OK,token,users.get(0));
            }else{
                return new ResultVO(ResStatus.NO,"登录失败，密码错误",null);
            }

        }
    }
}
