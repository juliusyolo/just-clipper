package com.sdefaa.just.clipper.transformer;

import javassist.CannotCompileException;
import javassist.NotFoundException;

import java.io.IOException;

/**
 * @author Julius Wong
 * @date 2022/8/31 23:47
 * @since 1.0.0
 */
public interface ITransformer {

    default boolean isTarget(String className){
        return false;
    }

    default boolean isTarget(ClassLoader classLoader,String className){
        return false;
    }

    byte[] transform(ClassLoader classLoader, String className) throws CannotCompileException, NotFoundException, IOException, ClassNotFoundException;
}
