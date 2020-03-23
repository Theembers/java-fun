package me.theembers.utils.progressbar;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @created by IntelliJ IDEA.
 * @copyright: Copyright(c) 2018
 * @author: hanmin
 * @date: 2020/3/6 0006 16:32
 * @description:
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProgressbarUtil {
    public static String buildEventName(String key, String eventName) {
        return String.format("%s_%s", key, eventName);
    }
}
