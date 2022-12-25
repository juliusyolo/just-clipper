package com.sdefaa.just.clipper.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Julius Wong
 * <p>
 * 运行该测试main方法，需要配置-javaagent: <AbsoluteDir>/just-clipper-agent-1.0.0-RELEASE.jar=clipper.yml
 * </p>
 * @since 1.0.0
 */
public class Main {
    public static void main(String[] args) {


        /**
         * 使用SLF4J门面模式获取Logger，
         * 底层使用那种日志类型取决于jar依赖，这里使用的是JDK日志
         */
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("客户姓名:{},身份证号:{},手机号:{},邮箱:{},银行卡号:{}",
                "郭德纲", "41242219991125211X", "18616887790",
                "guodg@gmail.com", "6001111001020241238");
        System.out.println(new CustomerController().queryCustomer());
    }

}