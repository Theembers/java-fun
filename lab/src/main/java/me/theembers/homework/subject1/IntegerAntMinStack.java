package me.theembers.homework.subject1;


/**
 * 一个 Integer 的 AntMinStack 实现
 */
public class IntegerAntMinStack extends AbstractAntMinStack<Integer> {
    /**
     * 实现比较器
     *
     * @param target
     * @param source
     * @return
     */
    @Override
    boolean compareLessThan(Integer target, Integer source) throws RuntimeException {
        if (target == null || target == null) {
            throw new RuntimeException("target or source couldn't be null.");
        }
        if (target > source) {
            return true;
        }
        return false;
    }
}
