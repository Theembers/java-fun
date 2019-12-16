package me.theembers.homework.subject1;

/**
 * 一个 string 的 AntMinStack 实现
 */
public class StringAntMinStack extends AbstractAntMinStack<String> {
    /**
     * 这里通过 string 原生的比较方式
     *
     * @param target
     * @param source
     * @return
     */
    @Override
    boolean compareLessThan(String target, String source) throws RuntimeException {
        if (target.compareTo(source) >= 0) {
            return true;
        }
        return false;
    }
}
