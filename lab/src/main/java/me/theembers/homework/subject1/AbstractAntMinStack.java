package me.theembers.homework.subject1;

import java.util.Stack;

/**
 * IAntMinStack 栈的抽象实现
 * <p>
 * 思路：
 * 1. 使用原生 stack 实现基本的 元素存储
 * 2. 通过 minStack
 * 记录每次 dataStack 压栈时的最小元素，如果当前元素小于 minStack 栈顶元素 则压栈 minStack
 * 每次 dataStack 出站时  minStack 同步出栈（相同元素同步出栈）
 *
 * @param <E>
 */
public abstract class AbstractAntMinStack<E> implements IAntMinStack<E> {
    Stack<E> dataStack = new Stack<>();
    Stack<E> minStack = new Stack<>();

    /**
     * 抽象比较器，需要个具体栈自行实现比较：
     * 约定：source 小于等于 target 则 返回 true （必须包含等于，如果没有等于，连续相同的最小值后出栈会有问题）
     *
     * @param target
     * @param source
     * @return
     */
    abstract boolean compareLessThan(E target, E source) throws RuntimeException;


    /**
     * push 放入元素
     *
     * @param data
     */
    @Override
    public E push(E data) {
        dataStack.push(data);
        if (minStack.isEmpty()) {
            minStack.push(data);
        } else {
            E min = minStack.peek();
            // 始终保持最小元素放最顶端
            if (min != null && compareLessThan(min, data)) {
                minStack.push(data);
            }
        }
        return data;
    }

    /**
     * pop 推出元素
     *
     * @return
     */
    @Override
    public E pop() {
        E data = dataStack.pop();
        if (data != null && !minStack.isEmpty() && minStack.peek() != null && minStack.peek() == data) {
            minStack.pop();
        }
        return data;
    }

    /**
     * min 最小函数，调用该函数，可直接返回当前AntMinStack的栈的最小值
     *
     * @return
     * @throws Exception
     */
    @Override
    public E min() throws Exception {
        return minStack.peek();
    }

    /**
     * 判空只需要验证 minStack 即可，因为如果有入栈操作总归会在 minStack 有值
     *
     * @return
     */
    public boolean empty() {
        return minStack.isEmpty();
    }
}
