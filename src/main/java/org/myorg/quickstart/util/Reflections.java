package org.myorg.quickstart.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Reflections {

    private Reflections() {

    }

    //Map无需保证可见性，其引用不会修改；需要保证可见性的是Map内的引用，故使用ConcurrentHashMap
    private static final Map<Class<?>, Map<String, Field>> CLASS_FIELD_CACHE = new ConcurrentHashMap<>();
    private static final Map<String, Constructor<?>> CONSTRUCTOR_CACHE = new ConcurrentHashMap<>();
    private static final Map<String, Class<?>> CLASS_CACHE = new ConcurrentHashMap<>();

    public static Field getCachedField(Class<?> clazz, String filedName) throws NoSuchFieldException {
        Field field = getClassFieldCachedMap(clazz).get(filedName);
        if (field == null) {
            throw new NoSuchFieldException("class:[" + clazz.getName() + "] not has no such field:[" + filedName + "]");
        }
        return field;
    }

    public static String[] getAllFieldNames(Class<?> clazz) {
        return getAllFields(clazz).stream().map(Field::getName).toArray(String[]::new);
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        return new ArrayList<>(getClassFieldCachedMap(clazz).values());
    }

    public static Map<String, Field> getClassFieldMap(Class<?> clazz) {
        return new HashMap<>(getClassFieldCachedMap(clazz));
    }

    private static Map<String, Field> getClassFieldCachedMap(Class<?> clazz) {
        return CLASS_FIELD_CACHE.computeIfAbsent(clazz, cla -> {
            Map<String, Field> map = new LinkedHashMap<>();
            while (cla != null) {
                Stream.of(cla.getDeclaredFields()).forEach(field -> {
                    field.setAccessible(true);
                    map.put(field.getName(), field);
                });
                cla = cla.getSuperclass();
            }
            return map;
        });
    }


    @SuppressWarnings("unchecked")
    public static <T> Constructor<T> getCachedConstructor(Class<T> clazz, Class<?>... parameterTypes)
            throws NoSuchMethodException {
        StringBuilder cacheKeyBuilder = new StringBuilder(clazz.getName());
        for (Class<?> param : parameterTypes) {
            cacheKeyBuilder.append(param.getSimpleName()).append('#');
        }
        String cacheKey = cacheKeyBuilder.toString();
        Constructor<T> constructor = (Constructor<T>) CONSTRUCTOR_CACHE.get(cacheKey);
        if (constructor == null) {
            synchronized (CONSTRUCTOR_CACHE) {
                constructor = (Constructor<T>) CONSTRUCTOR_CACHE.get(cacheKey);
                if (constructor == null) {
                    constructor = clazz.getDeclaredConstructor(parameterTypes);
                    constructor.setAccessible(true);
                    CONSTRUCTOR_CACHE.put(cacheKey, constructor);
                }
            }
        }
        return constructor;
    }

    public static Class<?> getCachedClass(String className) throws ClassNotFoundException {
        Class<?> clazz = CLASS_CACHE.get(className);
        if (clazz == null) {
            synchronized (CLASS_CACHE) {
                clazz = CLASS_CACHE.get(className);
                if (clazz == null) {
                    clazz = Class.forName(className);
                    CLASS_CACHE.put(className, clazz);
                }
            }
        }
        return clazz;
    }


}
