package com.ghj.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class IdWorker {

    private static long lastTimes = 0;
    private static AtomicInteger currentSequence = new AtomicInteger(100);

    /**
     * 
     * @param machineId
     *            机器编码：0000-9999，分布式部署的时候指定不同的机器编号
     * @return
     */
    public static String nextId(String machineId) {
        StringBuffer sb = new StringBuffer(machineId);
        long currentTimes = System.currentTimeMillis();
        if (lastTimes == currentTimes) {
            int result = currentSequence.incrementAndGet();
            if (result > 999) {
                currentTimes = genTimes(lastTimes);
            }
            sb.append(getTimeString(currentTimes)).append(result);
        } else {
            lastTimes = currentTimes;
            currentSequence = new AtomicInteger(100);
            sb.append(getTimeString(currentTimes)).append(currentSequence);
        }

        return sb.toString();
    }

    private static long genTimes(long lastTimes) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            // do nothing
        }
        long times = System.currentTimeMillis();
        if (times == lastTimes) {
            return genTimes(lastTimes);
        }
        return times;
    }

    private static String getTimeString(long currentTimes) {
        Date date = new Date(currentTimes);
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
        return sdf.format(date);
    }

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    for(;;) {
                        System.out.println("t_"+IdWorker.nextId("1"));
                    }
                }
            }).start();
        }
//        Thread t1 = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                for(;;) {
//                    System.out.println("t1"+IdWorker.nextId("1"));
//                }
//            }
//        });
//        Thread t2 = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                for(;;) {
//                    System.out.println("t2"+IdWorker.nextId("1"));
//                }
//            }
//        });
//        t1.start();
//        t2.start();

    }
}
