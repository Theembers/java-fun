package me.theembers.homework.subject1;

/**
 * 定义 AntMinStack 栈接口
 *
 * @param <E>
 */
public interface AntMinStack<E> {
    /**
     * push 放入元素
     *
     * @param data
     */
    E push(E data);

    /**
     * pop 推出元素
     *
     * @return
     * @throws Exception
     */
    E pop() throws Exception;

    /**
     * min 最小函数，调用该函数，可直接返回当前AntMinStack的栈的最小值
     *
     * @return
     * @throws Exception
     */
    E min() throws Exception;

    /**
     * 元素判空
     *
     * @return
     */
    boolean empty();
}
