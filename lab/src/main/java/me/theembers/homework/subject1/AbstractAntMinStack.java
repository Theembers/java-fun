package me.theembers.homework.subject1;

import java.util.Stack;

/**
 * IAntMinStack 栈的抽象实现
 * <p>
 * 思路：
 * 1. 使用原生 stack 实现基本的 元素存储
 * 2. 通过 minElStock
 * 记录每次 elementStock 压栈时的最小元素，如果当前元素小于 minElStock 栈顶元素 则压栈 minElStock
 * 每次 elementStock 出站时  minElStock 同步出栈（如果是相同元素同步出栈）
 *
 * @param <E>
 */
public abstract class AbstractAntMinStack<E> implements AntMinStack<E> {
    Stack<E> elementStock = new Stack<>();
    Stack<E> minElStock = new Stack<>();

    /**
     * 抽象比较器，需要个具体栈自行实现比较：
     * 约定：source 小于等于 target 则 返回 true （必须包含等于，如果没有等于，连续相同的最小值后出栈会有问题）
     *
     * @param target 比较值
     * @param source 当前值
     * @return
     */
    abstract boolean compareLessThan(E target, E source) throws RuntimeException;


    /**
     * push 元素入栈
     *
     * @param data
     */
    @Override
    public E push(E data) {
        elementStock.push(data);
        if (minElStock.isEmpty()) {
            minElStock.push(data);
        } else {
            E min = minElStock.peek();
            // 始终保持最小元素放最顶端
            if (min != null && compareLessThan(min, data)) {
                minElStock.push(data);
            }
        }
        return data;
    }

    /**
     * pop 元素出栈
     *
     * @return
     */
    @Override
    public E pop() {
        E data = elementStock.pop();
        if (data != null && !minElStock.isEmpty() && minElStock.peek() != null && minElStock.peek() == data) {
            minElStock.pop();
        }
        return data;
    }

    /**
     * min 最小函数
     *
     * @return
     */
    @Override
    public E min() {
        return minElStock.peek();
    }

    /**
     * 判空只需要验证 minElStock 即可，因为如果有入栈操作总归会在 minElStock 有值
     *
     * @return
     */
    public boolean empty() {
        return minElStock.isEmpty();
    }
}
