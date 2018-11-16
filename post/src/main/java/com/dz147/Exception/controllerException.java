package com.dz147.Exception;

import com.dz147.controller.ExcelService;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class controllerException implements HandlerExceptionResolver {

    private Logger logger = Logger.getLogger(ExcelService.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        Map<String,Object> map = new HashMap<String,Object>();
        logger.error(e.getMessage(),e);
        if(e instanceof MaxUploadSizeExceededException){
            map.put("msg", "文件大小超过限制!");
            return new ModelAndView(new MappingJackson2JsonView(),map);
        }
        map.put("msg", "系统错误!");
        return new ModelAndView(new MappingJackson2JsonView(),map);
    }
}
