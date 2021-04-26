package com.mzy.concurrent.copy_on_write;

import com.mzy.concurrent.config.ThreadPoolConfig;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;

/**
 * @author Jack Miao
 * @date 2021/4/21 10:52
 * @desc
 */
public class CopyOnWriteArrayDemo {

    public static void main(String[] args) {
        /**
        * CopyOnWriteArrayList:
        *  线程安全的List,在改变底层数据的时候,会将之前数组copy一份,加入新元素
        *  用于读多写少的情况,add(),remove() 使用Lock锁保证线程安全
        */
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        /**
        * CopyOnWriteArraySet:
        *    使用的是CopyOnWriteArrayList
        */
        CopyOnWriteArraySet set = new CopyOnWriteArraySet();

        set.add(1);
        set.add(2);
        set.remove(1);

        System.out.println(set);
        /*public boolean add(E e) {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                // 获取原始数组
                Object[] elements = getArray();
                int len = elements.length;
                // 将原始数组长度加1 copy
                Object[] newElements = Arrays.copyOf(elements, len + 1);
                // 指定新数组尾部元素为加入元素
                newElements[len] = e;
                // 新元素赋值
                setArray(newElements);
                return true;
            } finally {
                lock.unlock();
            }
        }*/
        //list.add("1");
        demo1();
    }

    public static void demo1(){
        //List<Integer> list = new ArrayList<>();
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        ExecutorService pool = ThreadPoolConfig.getThreadPool();
        list.add(1);
        list.add(2);

        pool.execute(()->{
        });
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            pool.execute(()->{
                list.add(finalI);
            });
        }
        System.out.println(list);
        Iterator<Integer> iterator = list.iterator();
        pool.execute(()->{
            while (iterator.hasNext()){
                System.out.println(iterator.next());
            }
        });
    }
}
