package org.hwb.spring.context;

/**
 *  模拟ApplicationContext接口，直接继承BeanFactory接口，以方便直接调用Bean方法
 *
 *  @author huwenbin
 */
public interface ApplicationContext extends BeanFactory{

    /**
     * 获取应用的唯一 id
     *
     * @return id
     */
    String getId();

    /**
     * 获取应用名称
     *
     * @return 应用名称
     */
    String getApplicationName();
}
