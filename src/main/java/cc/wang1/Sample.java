package cc.wang1;


import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Sample {

    private static final int SIZE = 10;

    public static void main(String[] args) {
        int[] result = new int[SIZE];
        AtomicInteger dataIndex = new AtomicInteger();

        IntStream.generate(() -> ThreadLocalRandom.current().nextInt())
                 .limit(Integer.MAX_VALUE)
                 .forEach(data -> {
                     int targetIndex = pick(SIZE, dataIndex.getAndIncrement());
                     if (targetIndex != -1) {
                         result[targetIndex] = data;
                     }
                 });

        System.out.println(Arrays.toString(result));
    }


    public static int pick(int targetSize, int dataIndex) {
        // 蓄水池未填满 直接将该元素放入
        if (dataIndex < targetSize) {
            return dataIndex;
        }

        // 蓄水池已填满 取 0~当前元素位序 范围的随机值
        int randIndex = ThreadLocalRandom.current().nextInt(0, dataIndex+1);
        // 如果随机值落到 前targetSize 范围，则将已存在于蓄水池randIndex位置的元素替换为当前位置的元素
        if (randIndex < targetSize) {
            return randIndex;
        }

        return -1;
    }

}
