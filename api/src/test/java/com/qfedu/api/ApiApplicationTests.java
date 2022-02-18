package com.qfedu.api;

import com.qfedu.fmmall.ApiApplication;
import com.qfedu.fmmall.dao.CategoryMapper;
import com.qfedu.fmmall.entity.CategoryVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = ApiApplication.class)
class ApiApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void contextLoads() {

        List<CategoryVO> categoryVOS = categoryMapper.selectAllCategories2(0);
        System.out.println(categoryVOS);
    }

}
