package org.hwb.spring.context;

/**
 * @author huwenbin
 */
public class AbstractBeanDefinition implements BeanDefinition{

    private String beanClassName;

    private boolean isLazyInit = false;

    private String factoryBeanName;

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }

    @Override
    public void setBeanClassName(String className) {
        this.beanClassName = className;
    }

    @Override
    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    @Override
    public String getFactoryBeanName() {
        return this.factoryBeanName;
    }

    @Override
    public void setLazyInit(boolean lazyInit) {
        this.isLazyInit = lazyInit;
    }

    @Override
    public boolean isLazyInit() {
        return this.isLazyInit;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public boolean isPrototype() {
        return false;
    }
}
