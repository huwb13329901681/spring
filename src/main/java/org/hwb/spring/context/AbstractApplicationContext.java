package org.hwb.spring.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huwenbin
 */
@Slf4j
public abstract class AbstractApplicationContext implements ApplicationContext, BeanFactory {

    private String[] configLocations;

    private BeanDefinitionReader beanDefinitionReader;

    /**
     * 保存已注册的所有 BeanDefinition, 实际上就是所谓的 "IOC 容器"
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(10);

    /**
     * 用来保存注册式单例的容器
     */
    private final Map<String, Object> singletonBeanCacheMap = new ConcurrentHashMap<>(10);

    private String id = this.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(this));


    AbstractApplicationContext(String... configLocations) {
        this.setConfigLocations(configLocations[0]);
    }

    private void setConfigLocations(String... locations) {
        if (locations != null) {
            Assert.noNullElements(locations, "Config locations must not be null");
            this.configLocations = new String[locations.length];
            for (int i = 0; i < locations.length; i++) {
                this.configLocations[i] = locations[i].trim();
            }
        } else {
            this.configLocations = null;
        }
    }

    void refresh() throws Exception {
        /* 1.定位  2.加载*/
        beanDefinitionReader = new BeanDefinitionReader(configLocations);
        /* 3.注册, 到这一步为止容器初始化完毕 */
        doRegisterBeanDefinition(beanDefinitionReader.getBeanDefinitions());
    }

    /**
     * 向 Spring IOC 容器中注册 BeanDefinition
     *
     * @param beanDefinitionList beanDefinitionList
     */
    private void doRegisterBeanDefinition(List<String> beanDefinitionList) {
        if (null == beanDefinitionList || beanDefinitionList.isEmpty()) {
            log.error("beanDefinitionList 为空, 无法继续执行注册操作!");
            throw new NullPointerException("beanDefinitionList 为空, 无法继续执行注册操作!");
        }
        for (String beanDefinitionName : beanDefinitionList) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(beanDefinitionName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // 默认是类名首字母小写, 或是自定义名称, 或是接口注入
            // 接口无法实例化, 需要使用其实现类来实例化
            if (null != clazz && clazz.isInterface()) {
                continue;
            }

            // 将 BeanDefinition 注册到 IOC 容器中
            BeanDefinition beanDefinition = beanDefinitionReader.registerBeanDefinition(beanDefinitionName);
            beanDefinitionMap.putIfAbsent(beanDefinition.getFactoryBeanName(), beanDefinition);

            // 如果该 Bean 实现了接口, 则保留该接口以便后面属性的自动注入(简化版, 实际上未必实现的接口中的方法会被调用)
            Class<?>[] clazzInterfaces;
            if (clazz != null) {
                clazzInterfaces = clazz.getInterfaces();
                for (Class<?> clz : clazzInterfaces) {
                    // 多个实现类时直接覆盖, Spring 原生处理会报错(按类型注入时)
                    beanDefinitionMap.put(clz.getName(), beanDefinition);
                }
            }
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getApplicationName() {
        return null;
    }

    @Override
    public Object getBean(String beanName) {
        String beanClassName = "";
        if (null == beanDefinitionMap){
            log.error("beanDefinitionMap 为空, 加载配置文件失败!");
            throw new NullPointerException("beanDefinitionMap 为空, 加载配置文件失败!");
        }
        Set<String> strings = beanDefinitionMap.keySet();
        Iterator<Map.Entry<String, BeanDefinition>> entries = beanDefinitionMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, BeanDefinition> entry = entries.next();
            String key = entry.getKey();
            if (beanName.equals(key)){
                BeanDefinition value = entry.getValue();
                beanClassName = value.getBeanClassName();
                break;
            }
        }
        return beanClassName;
    }
}
