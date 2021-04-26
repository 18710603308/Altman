package com.mzy.concurrent.future;

import com.mzy.concurrent.config.ThreadPoolConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeoutException;

/**
 * @author Jack Miao
 * @date 2021/4/15 16:38
 * @desc
 */
@RestController
@RequestMapping("/concurrent")
public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService pool = ThreadPoolConfig.getThreadPool();
        /**
         * Future 线程异步返回类
         * 在线程执行后可以做其他事情,在需要使用线程的返回值时再
         * 从Future中get(),get()建议设置等待时间,否则会一直阻塞,
         * 占用资源
         **/
        /*Future<String> submit = pool.submit(() -> {
            System.out.println("---------执行线程---------");
            Thread.sleep(5000);
            return "aaa";
        });
        System.out.println("做其他事情");
        for (;;){
            if(submit.isDone()){
                System.out.println(submit.get());
                break;
            }
        }*/
        //String s = submit.get(50, TimeUnit.NANOSECONDS);
        //System.out.println(s);

        /*CompletableFuture<Void> future = CompletableFuture.runAsync(()-> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---------线程正在执行-----------");
        });
*/

        /*CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync: " + Thread.currentThread().isDaemon());
            return "aaa";
        }).whenCompleteAsync((r, e)->{
            System.out.println("whenCompleteAsync: " + Thread.currentThread().isDaemon());
            System.out.println("aaaa");
        }).exceptionally(e ->{
            System.out.println("exceptionally: " + Thread.currentThread().isDaemon());
            return e.toString();
        });

        System.out.println("--------做其他事情----------" + Thread.currentThread().isDaemon());
        System.out.println(async.get());*/

        /*CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("--------开始执行--------");
            System.out.println("supplyAsync:" + Thread.currentThread());
            return "hello world";
        }).whenComplete((r, e) -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println("whenComplete:" + Thread.currentThread());
            System.out.println(r + ", Java");
        });
        System.out.println("main:" + Thread.currentThread());
        System.out.println("------------做其他事情-------------");
        // 将会阻塞主线程
        future.join();*/

        //testThenAccept();

        thenCombine();
    }

    @RequestMapping("/demo1")
    public static String demo1() {
        /**
         * CompletableFuture 异步线程返回类,
         * 同Future区别:
         *      future在get()时是阻塞的,或者轮询 isDone() 判断是否执行完毕,然后才能取到值
         *      CompletableFuture 是有异步的通知机制,返回的结果会用另外一条线程来通知,若此次
         *     请求已执行完毕,异步通知结果还是会执行
         **/
        CompletableFuture.supplyAsync(() -> {
            System.out.println("---------线程正在执行-----------");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(1/0);
            return "success";
        }).whenComplete((r, e) -> {
            System.out.println("--------------");
            demo2(r);
        }).exceptionally(e -> {

            throw new RuntimeException(e);
        });
        //Thread.sleep(5000);
        System.out.println("---------做其他事情啦~----------");
        return "ok!";
    }

    private static void demo2(String r) {
        System.out.println(r);
    }

    @RequestMapping("/demo2")
    public void testAllOfAndAnyOf() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "aaa";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "bbb";
        });

        /**
         *  allOf是等所有入参的 CompletableFuture 都执行完后返回
         *  返回Void
         */
        CompletableFuture<Void> allOf = CompletableFuture.allOf(future1, future2);
        System.out.println(allOf.get());

        /**
         *  anyOf是入参 CompletableFuture[] 中任何一个执行完, 就返回先执行完的result
         */
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(future1, future2);
        System.out.println(anyOf.get());
    }

    private static void testThenAccept() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync" + Thread.currentThread());
            return "Hello";
        });

        CompletableFuture<Void> accept = future.thenAccept(r -> {
            System.out.println(r + " Java!!!");
            System.out.println(Thread.currentThread());
        });

        System.out.println(future.get());
        System.out.println(accept.get());
    }


    private static void thenCombine() throws ExecutionException, InterruptedException {
        /**
        * thenCombine()
        *  执行多个supplyAsync(),
        *  入参:
        *    CompletionStage<? extends U> other,
        *      异步执行的状态值
        *    BiFunction<? super T,? super U,? extends V> fn)
        *      本次结果和上次结果进行操作
        *
        *
        */
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync 1 " + Thread.currentThread());
            return "sb";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync 2 " + Thread.currentThread());
            return "333";
        }), (s1, s2) -> {
            System.out.println(Thread.currentThread());
            return s1 + s2;
        }).thenCombine(CompletableFuture.supplyAsync(()->{
            System.out.println("supplyAsync 3 " + Thread.currentThread());
            return "666";
        }),(s1, s2) ->{
            System.out.println(Thread.currentThread());
            return s1 + s2;
        });

        System.out.println(future.get());
    }
}
