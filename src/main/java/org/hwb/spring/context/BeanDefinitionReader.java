package org.hwb.spring.context;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * 模拟Spring下的XmlBeanDefinitionReader，作为查找，读取。解析xml配置的类
 *
 * @author huwenbin
 */
@Slf4j
public class BeanDefinitionReader {

    /**
     * 存储被注册过的 Bean Class 名称
     */
    private final List<String> beanDefinitionClasses = new ArrayList<>(10);

    /**
     * Properties 代替 XML 配置
     */
    private final Properties config = new Properties();

    BeanDefinitionReader(String... configLocations) throws FileNotFoundException{
        // 加载配置文件
        this.doLoadBeanDefinitions(configLocations);
        // 扫描basePackage
        this.doScan(config.getProperty("basePackage"));
    }

    private void doScan(String packageName)throws FileNotFoundException {
        // 简单实现，直接读取指定包下的文件
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/"));
        if (null == url) {
            throw new FileNotFoundException("未找到指定路径！");
        }
        File urlFile = new File(url.getFile());
        for (File file : Objects.requireNonNull(urlFile.listFiles())) {
            if (file.isDirectory()) {
                // 如果找到的是文件夹, 则递归调用本方法
                log.info("doScan() 找到了文件夹, 将继续递归查找... packageName = {}", packageName + "." + file.getName());
                doScan(packageName + "." + file.getName());
            } else {
                log.info("doScan() 找到了文件, 准备添加至集合中... packageName = {}", packageName + "." + file.getName());
                beanDefinitionClasses.add(packageName + "." + file.getName().replace(".class", ""));
            }
        }
    }

    private void doLoadBeanDefinitions(String... configLocations) {
        // 使用输入流读取properties 文件的内容
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configLocations[0]);
        try {
            config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<String> getBeanDefinitions() {
        return beanDefinitionClasses;
    }

    public BeanDefinition registerBeanDefinition(String className) {
        if (null == className){
            return null;
        }else if (beanDefinitionClasses.contains(className)){
            BeanDefinition beanDefinition = new AbstractBeanDefinition();
            beanDefinition.setBeanClassName(className);
            beanDefinition.setFactoryBeanName(
                    lowerFirstChar(className.substring(className.lastIndexOf(".") + 1, className.length())));
            return beanDefinition;
        }
        return null;
    }

    private String lowerFirstChar(String originalStr) {
        if (null == originalStr || "".equals(originalStr)) {
            return originalStr;
        }
        char[] array = originalStr.toCharArray();
        array[0] += 32;
        return String.valueOf(array);
    }
}
