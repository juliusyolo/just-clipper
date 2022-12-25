package com.sdefaa.just.clipper.transformer.impl;

import com.sdefaa.just.clipper.core.configuration.JustClipperConfigManager;
import com.sdefaa.just.clipper.transformer.AbstractTransformer;
import javassist.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Julius Wong
 * <p>
 * default implemented transformer for view
 * <p>
 * @since 1.0.0
 */
public class ViewTransformer extends AbstractTransformer {

    public final static ViewTransformer INSTANCE = new ViewTransformer();

    private final static String TARGET_METHOD_AGENT_NAME_SUFFIX = "$aspectClipperAgent";
    private final static String TARGET_REST_CONTROLLER_ASPECT = "@org.springframework.web.bind.annotation.RestController";
    private final static String TARGET_CONTROLLER_ASPECT = "@org.springframework.stereotype.Controller";
    private final static String TARGET_POST_ANNOTATION = "@org.springframework.web.bind.annotation.PostMapping";
    private final static String TARGET_GET_ANNOTATION = "@org.springframework.web.bind.annotation.GetMapping";
    private final static String TARGET_PUT_ANNOTATION = "@org.springframework.web.bind.annotation.PutMapping";
    private final static String TARGET_PATCH_ANNOTATION = "@org.springframework.web.bind.annotation.PatchMapping";
    private final static String TARGET_DELETE_ANNOTATION = "@org.springframework.web.bind.annotation.DeleteMapping";
    private final static String TARGET_REQUEST_ANNOTATION = "@org.springframework.web.bind.annotation.RequestMapping";
    private final static String PATH_REGEX = ".*(path=\\{([^}]*)}).*";
    private final static String VALUE_REGEX = ".*(value=\\{([^}]*)}).*";

    private ViewTransformer() {
    }


    public static boolean hasMethod(Class<?> aClass) {
        try {
            aClass.getMethod("length");
        } catch (NoSuchMethodException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isTarget(ClassLoader classLoader, String className) {
        try {
            if (!isTargetPackage(getFullyQualifiedClassName(className))) {
                return false;
            }
            ClassPool classPool = new ClassPool(true);
            if (classLoader != null) {
                classPool.insertClassPath(new LoaderClassPath(classLoader));
            }
            CtClass ctClass = classPool.get(getFullyQualifiedClassName(className));
            Object[] annotations = ctClass.getAnnotations();
            for (Object annotation : annotations) {
                if (annotation.toString().startsWith(TARGET_REST_CONTROLLER_ASPECT) || annotation.toString().startsWith(TARGET_CONTROLLER_ASPECT)) {
                    return true;
                }
            }
            return false;
        } catch (NotFoundException | ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public byte[] transform(ClassLoader classLoader, String className) throws CannotCompileException, NotFoundException, IOException, ClassNotFoundException {
        ClassPool classPool = new ClassPool(true);
        if (classLoader != null) {
            classPool.insertClassPath(new LoaderClassPath(classLoader));
        }
        CtClass targetClass = classPool.get(getFullyQualifiedClassName(className));
        CtMethod[] declaredMethods = targetClass.getDeclaredMethods();
        for (CtMethod declaredMethod : declaredMethods) {
            if (!Modifier.isStatic(declaredMethod.getModifiers()) && !Modifier.isPrivate(declaredMethod.getModifiers())) {
                if (this.isTargetAnnotation(declaredMethod)) {
                    CtMethod targetAgentMethod = CtNewMethod.copy(declaredMethod, declaredMethod.getName().concat(TARGET_METHOD_AGENT_NAME_SUFFIX), targetClass, null);
                    targetClass.addMethod(targetAgentMethod);
                    String body = "{" +
                            "com.sdefaa.just.clipper.core.clip.impl.ViewClipper clipper = com.sdefaa.just.clipper.core.clip.impl.ViewClipper.INSTANCE;" +
                            "Object result = null;" +
                            "try {result = clipper.clip(($w)" + declaredMethod.getName().concat(TARGET_METHOD_AGENT_NAME_SUFFIX) + "($$));}catch (Throwable e){return ($w)" + declaredMethod.getName().concat(TARGET_METHOD_AGENT_NAME_SUFFIX) + "($$);}" +
                            "return ($r)result;" +
                            "}";
                    declaredMethod.setBody(body);
                }
            }
        }
        byte[] bytecode = targetClass.toBytecode();
        targetClass.detach();
        if (isDebug()) {
            Path byteCodePath = Paths.get(getFullyQualifiedClassName(className) + ".class");
            Files.write(byteCodePath, bytecode);
        }
        return bytecode;
    }


    private boolean isTargetAnnotation(CtMethod ctMethod) throws ClassNotFoundException {
        Object[] annotations = ctMethod.getAnnotations();
        for (Object annotation : annotations) {
            String annotationStr = annotation.toString();
            if (isTargetMethodAnnotation(annotationStr) && isTargetUrl(annotationStr)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTargetUrl(String annotationStr) {
        List<String> urls = JustClipperConfigManager.INSTANCE.getViewConfig().getUrls();
        Set<String> urlSet = new HashSet<>(urls);
        return urls.isEmpty() || isTargetUrl(annotationStr, urlSet);
    }

    private boolean isTargetPackage(String className) {
        List<String> scanPackages = JustClipperConfigManager.INSTANCE.getViewConfig().getScanPackages();
        for (String scanPackage : scanPackages) {
            String prefix = scanPackage.replaceAll(".\\*", "");
            if (className.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTargetUrl(String annotationStr, Set<String> urls) {
        String[] urlStr = annotationStr.replaceAll(PATH_REGEX, "$1").replaceAll(VALUE_REGEX, "$1").replaceAll("path=\\{", "").replaceAll("value=\\{", "").replaceAll("}", "").replaceAll("\"", "").replaceAll(" ", "").trim().split(",");
        Set<String> urlSet = new HashSet<>(Arrays.asList(urlStr));
        urlSet.retainAll(urls);
        return !urlSet.isEmpty();
    }

    private boolean isTargetMethodAnnotation(String annotationStr) {
        return annotationStr.startsWith(TARGET_POST_ANNOTATION) || annotationStr.startsWith(TARGET_GET_ANNOTATION) ||
                annotationStr.startsWith(TARGET_PUT_ANNOTATION) || annotationStr.startsWith(TARGET_PATCH_ANNOTATION) ||
                annotationStr.startsWith(TARGET_DELETE_ANNOTATION) || annotationStr.startsWith(TARGET_REQUEST_ANNOTATION);
    }

    private boolean containsUrl(String[] urls) {
        if (urls == null) {
            return false;
        }
        for (String url : urls) {
            if (isTargetUrl(url)) {
                return true;
            }
        }
        return false;
    }
}
