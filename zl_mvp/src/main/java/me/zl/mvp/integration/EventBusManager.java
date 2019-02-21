package me.zl.mvp.integration;

import java.lang.reflect.Method;

import static me.zl.mvp.base.Platform.DEPENDENCY_ANDROID_EVENTBUS;
import static me.zl.mvp.base.Platform.DEPENDENCY_EVENTBUS;

/**
 * EventBus 的管理类
 */
public final class EventBusManager {
    private static volatile EventBusManager sInstance;

    public static EventBusManager getInstance() {
        if (sInstance == null) {
            synchronized (EventBusManager.class) {
                if (sInstance == null) {
                    sInstance = new EventBusManager();
                }
            }
        }
        return sInstance;
    }

    private EventBusManager() { }

    /**
     * 注册订阅者, 允许在项目中同时依赖两个 EventBus, 只要您喜欢
     *
     * @param subscriber 订阅者
     */
    public void register(Object subscriber) {
        if (DEPENDENCY_ANDROID_EVENTBUS) {
            org.simple.eventbus.EventBus.getDefault().register(subscriber);
        }
        if (DEPENDENCY_EVENTBUS) {
            if (haveAnnotation(subscriber)) {
                org.greenrobot.eventbus.EventBus.getDefault().register(subscriber);
            }
        }
    }

    /**
     * 注销订阅者, 允许在项目中同时依赖两个 EventBus, 只要您喜欢
     *
     * @param subscriber 订阅者
     */
    public void unregister(Object subscriber) {
        if (DEPENDENCY_ANDROID_EVENTBUS) {
            org.simple.eventbus.EventBus.getDefault().unregister(subscriber);
        }
        if (DEPENDENCY_EVENTBUS) {
            if (haveAnnotation(subscriber)) {
                org.greenrobot.eventbus.EventBus.getDefault().unregister(subscriber);
            }
        }
    }

    /**
     * 发送事件, 如果您在项目中同时依赖了两个 EventBus, 请自己使用想使用的 EventBus 的 Api 发送事件
     *
     * @param event 事件
     */
    public void post(Object event) {
        if (DEPENDENCY_ANDROID_EVENTBUS) {
            org.simple.eventbus.EventBus.getDefault().post(event);
        } else if (DEPENDENCY_EVENTBUS) {
            org.greenrobot.eventbus.EventBus.getDefault().post(event);
        }
    }

    /**
     * 清除订阅者和事件的缓存, 如果您在项目中同时依赖了两个 EventBus, 请自己使用想使用的 EventBus 的 Api 清除订阅者和事件的缓存
     */
    public void clear() {
        if (DEPENDENCY_ANDROID_EVENTBUS) {
            org.simple.eventbus.EventBus.getDefault().clear();
        } else if (DEPENDENCY_EVENTBUS) {
            org.greenrobot.eventbus.EventBus.clearCaches();
        }
    }

    private boolean haveAnnotation(Object subscriber) {
        boolean skipSuperClasses = false;
        Class<?> clazz = subscriber.getClass();
        //查找类中符合注册要求的方法, 直到Object类
        while (clazz != null && !isSystemCalss(clazz.getName()) && !skipSuperClasses) {
            Method[] allMethods;
            try {
                allMethods = clazz.getDeclaredMethods();
            } catch (Throwable th) {
                try {
                    allMethods = clazz.getMethods();
                }catch (Throwable th2){
                    continue;
                }finally {
                    skipSuperClasses = true;
                }
            }
            for (int i = 0; i < allMethods.length; i++) {
                Method method = allMethods[i];
                Class<?>[] parameterTypes = method.getParameterTypes();
                //查看该方法是否含有 Subscribe 注解
                if (method.isAnnotationPresent(org.greenrobot.eventbus.Subscribe.class) && parameterTypes.length == 1) {
                    return true;
                }
            } //end for
            //获取父类, 以继续查找父类中符合要求的方法
            clazz = clazz.getSuperclass();
        }
        return false;
    }

    private boolean isSystemCalss(String name) {
        return name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.");
    }
}
