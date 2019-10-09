package me.theembers.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author TheEmbers Guo
 * createTime 2019-10-09 16:57
 */
@Slf4j
@Component
public class BeanInitAndDestroy implements InitializingBean, DisposableBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("===>> TestBeanInit init...");
    }

    @Override
    public void destroy() throws Exception {
        log.info("===>> TestBeanInit destroy...");
    }
}
