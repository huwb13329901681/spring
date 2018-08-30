package org.hwb.spring.context;

/**
 * @author huwenbin
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String[] configLocations) throws Exception {
        this(configLocations, true);
    }

    private ClassPathXmlApplicationContext(String[] configLocations, boolean refresh) throws Exception {
        super(configLocations);
        if (refresh) {
            // 刷新
            refresh();
        }
    }
}
