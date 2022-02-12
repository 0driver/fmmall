package com.qfedu.fmmall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel
public class ResultVO {
    //相应给前端的状态码
    @ApiModelProperty(value = "响应状态码")
    private int code;


    //相应给前端的消息
    @ApiModelProperty(value = "响应信息")
    private String msg;
    //相应给前端的数据
    @ApiModelProperty(value = "响应数据 ")
    private Object data;
}
