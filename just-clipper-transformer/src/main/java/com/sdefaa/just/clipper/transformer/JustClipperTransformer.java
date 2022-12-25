package com.sdefaa.just.clipper.transformer;

import com.sdefaa.just.clipper.transformer.impl.LogTransformer;
import com.sdefaa.just.clipper.transformer.impl.ViewTransformer;
import javassist.CannotCompileException;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.List;

/**
 * @author Julius Wong
 * @date 2022/8/31 23:49
 * @since 1.0.0
 */
public class JustClipperTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        List<AbstractTransformer> transformerList = Arrays.asList(LogTransformer.INSTANCE,ViewTransformer.INSTANCE);
        for (int i = 0; i < transformerList.size(); i++) {
            AbstractTransformer transformer = transformerList.get(i);
            if (transformer.isTarget(className)||transformer.isTarget(loader,className)){
                try {
                    return transformer.transform(loader,className);
                } catch (CannotCompileException | ClassNotFoundException | IOException | NotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return new byte[0];
    }
}
