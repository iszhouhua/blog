package com.iszhouhua.blog.controller.front;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 前台错误页面
 * @author ZhouHua
 * @date 2019/1/6
 */
@Controller
public class ErrorController {
    /**
     * 404页面
     * @return
     */
    @GetMapping("404.html")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String error404(){
        return "error/404";
    }

    /**
     * 500页面
     * @return
     */
    @GetMapping("500.html")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String error500(){
        return "error/500";
    }
}
