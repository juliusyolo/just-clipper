package com.sdefaa.just.clipper.transformer.impl;

import com.sdefaa.just.clipper.transformer.AbstractTransformer;
import javassist.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Julius Wong
 * <p>
 * default implemented transformer for log
 * <p>
 * @since 1.0.0
 */
public class LogTransformer extends AbstractTransformer {

    public final static LogTransformer INSTANCE = new LogTransformer();
    private final static String TARGET_METHOD_AGENT_NAME_SUFFIX = "$logClipperAgent";
    private final static Map<String, String> TARGET_MAP = new HashMap<>();

    static {
        TARGET_MAP.put("org/apache/log4j/spi/LoggingEvent", "getRenderedMessage");
        TARGET_MAP.put("ch/qos/logback/classic/spi/LoggingEvent", "getFormattedMessage");
        TARGET_MAP.put("java/util/logging/LogRecord", "getMessage");
        TARGET_MAP.put("org/slf4j/helpers/FormattingTuple", "getMessage");
    }
    private LogTransformer(){}

    @Override
    public boolean isTarget(String className) {
        return TARGET_MAP.containsKey(className);
    }

    @Override
    public byte[] transform(ClassLoader classLoader, String className) throws CannotCompileException, NotFoundException, IOException, ClassNotFoundException {
        ClassPool classPool = new ClassPool(true);
        if (classLoader != null) {
            classPool.insertClassPath(new LoaderClassPath(classLoader));
        }
        CtClass targetClass = classPool.get(getFullyQualifiedClassName(className));
        CtMethod targetMethod = targetClass.getDeclaredMethod(TARGET_MAP.get(className));
        CtMethod targetAgentMethod = CtNewMethod.copy(targetMethod, TARGET_MAP.get(className).concat(TARGET_METHOD_AGENT_NAME_SUFFIX), targetClass, null);
        targetClass.addMethod(targetAgentMethod);
        String body = "{" +
                "com.sdefaa.just.clipper.core.clip.impl.LogClipper clipper = com.sdefaa.just.clipper.core.clip.impl.LogClipper.INSTANCE;" +
                "Object result = null;" +
                "try {result = clipper.clip(($w)" + TARGET_MAP.get(className).concat(TARGET_METHOD_AGENT_NAME_SUFFIX) + "($$));}catch (Throwable e){ return ($w)"+TARGET_MAP.get(className).concat(TARGET_METHOD_AGENT_NAME_SUFFIX)+"($$);}" +
                "return ($r)result;" +
                "}";
        targetMethod.setBody(body);
        byte[] bytecode = targetClass.toBytecode();
        targetClass.detach();
        if (isDebug()){
            Path byteCodePath = Paths.get(getFullyQualifiedClassName(className) + ".class");
            Files.write(byteCodePath, bytecode);
        }
        return bytecode;
    }

}
