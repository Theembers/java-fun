package me.theembers.utils.progressbar;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2020-03-05 11:26
 */
@Component
@Slf4j
public class ProgressServer {

    private static final ThreadLocal<String> eventThreadLocal = new ThreadLocal<>();

    public static void aroundRefresh(Long num, Long total) {
        String eventName = eventThreadLocal.get();
        if (StringUtils.isEmpty(eventName)) {
            return;
        }
        ProgressContainer.refreshProgress(eventName, num, total);
    }

    @Aspect
    @Component
    public static class ProgressAspect {
        /**
         * 进度条切面
         */
        @Pointcut("@annotation(ProgressEvent)")
        public void progressEventCut() {
        }

        @Before(value = "progressEventCut() && @annotation(progressEvent)")
        public void doBefore(JoinPoint joinPoint, ProgressEvent progressEvent) throws Throwable {
            String eventName = progressEvent.name();
            Object[] args = joinPoint.getArgs();
            eventName = ProgressbarUtil.buildEventName(args[0].toString(), eventName);

            ProgressContainer.initEventProgress(eventName);
            eventThreadLocal.set(eventName);
        }

        @After(value = "progressEventCut() && @annotation(progressEvent)")
        public void doAfter(JoinPoint joinPoint, ProgressEvent progressEvent) throws Throwable {
            log.debug(ProgressContainer.getProgressBy(eventThreadLocal.get()).toString());
            eventThreadLocal.remove();
        }

    }


    public static class ProgressContainer {

        private static final Map<String, EventInfo> container = new ConcurrentHashMap<>();

        public static synchronized void initEventProgress(String eventName) {
            container.put(eventName, EventInfo.initBuild(eventName));
        }

        public static synchronized void refreshProgress(String eventName, Long num, Long total) {
            if (StringUtils.isEmpty(eventName)) {
                return;
            }
            EventInfo eventInfo = ProgressContainer.container.getOrDefault(eventName, EventInfo.initBuild(eventName));
            eventInfo.flush(num);
            eventInfo.setTotal(total);
            ProgressContainer.container.put(eventName, eventInfo);
        }

        public static Map<String, EventInfo> getProgressMap() {
            return container;
        }

        public static EventInfo getProgressBy(String eventName) {
            return container.getOrDefault(eventName, null);
        }
    }
}
