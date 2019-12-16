package me.theembers.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import org.springframework.stereotype.Component;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2019-10-26 2:03 下午
 */
@Component
public class AllBean implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

        System.out.println("-->"+beanName + ":" + beanClass);
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println(beanName + ":" + bean);
        return true;
    }
}
