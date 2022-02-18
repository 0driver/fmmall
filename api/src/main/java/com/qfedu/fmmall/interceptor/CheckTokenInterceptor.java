package com.qfedu.fmmall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.fmmall.vo.ResStatus;
import com.qfedu.fmmall.vo.ResultVO;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //解决浏览器的预检机制
        String method=request.getMethod();
        if(method.equalsIgnoreCase("options")){
            return true;
        }
        String token = request.getHeader("token");
        System.out.println("Interceptor received a token :***********"+token);
        if(token==null) {
            ResultVO resultVO=new ResultVO(ResStatus.NO,"请先登录",null);

            doResponse(response,resultVO);
            return false;
        }else{
            try {
                JwtParser parser = Jwts.parser();
                parser.setSigningKey("helloworld");//设置解析密钥,与生成token时所用的密码一致
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);//如果校验token不正确，抛出异常
                return true;
            } catch (ExpiredJwtException e) {
                ResultVO resultVO=new ResultVO(ResStatus.NO,"登录过期，请重新登录",null);
                doResponse(response,resultVO);
                return false;
            }catch (UnsupportedJwtException e){
                ResultVO resultVO=new ResultVO(ResStatus.NO,"Token不正确，请自重！",null);
                doResponse(response,resultVO);
                return false;
            }catch (Exception e){
                ResultVO resultVO=new ResultVO(ResStatus.NO,"校验Token时出错，请联系客服",null);
                System.out.println(e);
                doResponse(response,resultVO);
                return false;
            }
        }

    }

    private void doResponse(HttpServletResponse response,ResultVO resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVO);
        writer.print(s);
        writer.flush();
        writer.close();
    }
}
