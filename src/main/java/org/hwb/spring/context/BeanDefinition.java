package org.hwb.spring.context;

/**
 * @author huwenbin
 */
public interface BeanDefinition {
    /**
     * 获取 Bean className
     *
     * @return Bean className
     */
    String getBeanClassName();

    /**
     * 设置 Bean className
     *
     * @param className className
     */
    void setBeanClassName(String className);

    /**
     * 设置 Factory BeanName
     *
     */
    void setFactoryBeanName(String factoryBeanName);

    /**
     * 获取 Factory BeanName
     *
     * @return Factory BeanName
     */
    String getFactoryBeanName();

    /**
     * 设置 Bean 是否为延时加载
     *
     * @param lazyInit 延时加载标志
     */
    void setLazyInit(boolean lazyInit);

    /**
     * 获取 Bean 是否为延时加载
     *
     * @return 是|否
     */
    boolean isLazyInit();

    /**
     * 获取 Bean 是否为单例
     *
     * @return 是|否
     */
    boolean isSingleton();

    /**
     * 获取 Bean 是否为原型
     *
     * @return 是|否
     */
    boolean isPrototype();
}
