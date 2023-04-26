package com.fit.resolver;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类名称：MyExceptionResolver.java
 * 类描述：
 */
public class MyExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("============== Exception Start=============");
        ex.printStackTrace();
        System.out.println("============== Exception End=============");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/error");
        mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));
        return mv;
    }

}
