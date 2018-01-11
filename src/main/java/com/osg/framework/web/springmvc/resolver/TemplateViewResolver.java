package com.osg.framework.web.springmvc.resolver;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * 
 * @author 张一杰
 */
public class TemplateViewResolver implements ViewResolver, Ordered {

    private Map<String, ViewResolver> viewResolvers;
    private int order;

    public View resolveViewName(String viewName, Locale locale) throws Exception {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        Assert.isInstanceOf(ServletRequestAttributes.class, attrs);
        String key = getRequestSuffix(((ServletRequestAttributes) attrs).getRequest());
        if (key == null) {
            return null;
        } else {
            ViewResolver vr = viewResolvers.get(key);
            if (vr == null) {
                return null;
            }
            return vr.resolveViewName(viewName, locale);
        }
    }

    private String getRequestSuffix(HttpServletRequest request) {
        String path = request.getRequestURI();
        return StringUtils.getFilenameExtension(path);
    }

    /**
     * 获取viewResolvers
     * 
     * @return viewResolvers
     */
    public Map<String, ViewResolver> getViewResolvers() {
        return viewResolvers;
    }

    /**
     * 设置viewResolvers
     * 
     * @param viewResolvers viewResolvers
     */
    public void setViewResolvers(Map<String, ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    public int getOrder() {
        return order;
    }

    /**
     * 设置order
     * 
     * @param order order
     */
    public void setOrder(int order) {
        this.order = order;
    }

}
