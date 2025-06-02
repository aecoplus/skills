package com.mochi.demo.stream;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest {

    @Test
    public void map() {
        List<Food> list = DataUtil.genData();
        // ========== (f) -> f ============== 保证list中foodName唯一
        Map<String, Food> map = list.stream().collect(Collectors.toMap(Food::getName, (f) -> f));
        System.out.println(map);

        // list按某个属性值分组 并count分组
        Map<String, Long> countMap = list.stream().collect(
                Collectors.groupingBy(Food::getName, Collectors.counting()));
        System.out.println(countMap);

        // 分组统计
        Map<String, IntSummaryStatistics> summaryMap = list.stream().collect(
                Collectors.groupingBy(Food::getName, Collectors.summarizingInt(Food::getCalories)));
        System.out.println(summaryMap);
    }
    
    @Test
    public void locale() {
        String displayCountry = Locale.getDefault().getDisplayCountry();
        System.out.println(displayCountry); // 中国
        String country = Locale.getDefault().getCountry();
        System.out.println(country); // CN
    }

    @Test
    public void fileStream() {
        try(Stream<String> stream = Files.lines(Paths.get("F:\\e-book\\file01.txt"))) {
            List<String> lineList = stream.collect(Collectors.toList());
            lineList.forEach(line -> System.out.println(line));
        } catch (IOException e) {
            System.out.println("error, no file");
        }
    }

    @Test
    public void fileStreamTest() {
        try (Stream<String> stream = Files.lines(Paths.get(""));
            Stream<String> stream2 = Files.lines(Paths.get(""))) {



        } catch (IOException e) {

        }
    }

    @Test
    public void intro() {
        List<Food> list = DataUtil.genData();
        // 统计 summarizingInt
        IntSummaryStatistics summary = list.stream().collect(Collectors.summarizingInt(Food::getCalories));
        String intro = String.format("%s{count=%d, sum=%d, min=%d, average=%f, max=%d}",
                "listSummary", summary.getCount(), summary.getSum(), summary.getMin(), summary.getAverage(), summary.getMax());
        System.out.println(intro);
        // filter
        List<Food> matchedFood = list.stream().filter((f) -> f.getName().matches("[a-z]+"))
                .collect(Collectors.toList());
        System.out.println(matchedFood);
    }

    @Test
    public void partition() {
        // 分区 true/false两区
        List<Food> list = DataUtil.genData();
        Map<Boolean, List<Food>> map = list.stream()
                .collect(Collectors.partitioningBy((f) -> f.getCalories() > 100));
        System.out.println(map);
    }

    @Test
    public void group() {
        // 分组
        List<Food> list = DataUtil.genData();
        // 以下两个语句作用相同
        Map<String, List<Food>> collect = list.stream().collect(Collectors.groupingBy(Food::getName));
        list.stream().collect(Collectors.groupingBy(Food::getName, Collectors.toList()));

        Map<String, Long> collect1 = list.stream()
                .collect(Collectors.groupingBy(Food::getName, Collectors.counting()));
        Map<String, Optional<Food>> collect2 = list.stream().collect(Collectors.groupingBy(Food::getName,
                Collectors.maxBy(Comparator.comparingInt(Food::getCalories))));
        System.out.println(collect2);

        // collectingAndThen
        Map<String, Food> collectAndThen = list.stream().collect(Collectors.groupingBy(Food::getName,
                Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Food::getCalories)), Optional::get)));
        System.out.println(collectAndThen);

        // 分组找最大值
        Map<String, Optional<String>> maxMap = list.stream().collect(Collectors.groupingBy(Food::getName,
                Collectors.mapping(Food::getName, Collectors.maxBy(Comparator.comparing(String::length)))));
        System.out.println(maxMap);
    }

    @Test
    public void reduce_collect() {
        // 归约和汇总
        List<Food> list = DataUtil.genData();
        Optional<Food> maxCaloriesFood = list.stream().collect(Collectors.maxBy(Comparator.comparing(Food::getCalories)));
        Integer caloriesSum = list.stream().collect(Collectors.summingInt(Food::getCalories));

        // joining 使用的是StringBuilder
        String nameJoining = list.stream().map(Food::getName).collect(Collectors.joining(", "));
        System.out.println("nameJoining: " + nameJoining);

        Integer caloriesSumByReducing = list.stream().collect(Collectors.reducing(0, Food::getCalories, Integer::sum));
    }
    
    @Test
    public void streamGen() {
        // 构建流

        Stream<Integer> integerStream = Stream.of(1, 2, 3);
        int[] numbers = {2, 3, 5, 7, 11, 13};
        IntStream intStream = Arrays.stream(numbers);

        long uniqueWords = 0;
        try(Stream<String> lines =
                    Files.lines(Paths.get("F:\\e-book\\file01.txt"), Charset.defaultCharset())){
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
        } catch(IOException e){

        }
        System.out.println(uniqueWords);


        Stream<Integer> is = Stream.of(1, 2, 3);
        int[] arr = {1, 2};
        IntStream stream = Arrays.stream(arr);




    }

    @Test
    public void tripleItem() {
        Stream<int[]> pythagoreanTriples =
                IntStream.rangeClosed(1, 100).boxed()
                        .flatMap(a ->
                                IntStream.rangeClosed(a, 100)
                                        .filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
                                        .mapToObj(b ->
                                                new int[]{a, b, (int)Math.sqrt(a * a + b * b)})
                        );
        List<int[]> arrayList = pythagoreanTriples.collect(Collectors.toList());
        for (int[] array : arrayList) {
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i] + " ");
            }
            System.out.println();
        }

    }

    /**
     * 归约
     * reduce: https://blog.csdn.net/lijingronghcit/article/details/108348728
     */
    @Test
    public void reduce() {
        ArrayList<Double> myList = new ArrayList<>();
        myList.add(4.0);
        myList.add(16.0);
        // reduce参数 初始值的定义（Identity)，累加器（Accumulator)，组合器（Combiner）
        // Accumulator: 定义一个带两个参数的函数，第一个参数是上个归并函数的返回值，第二个是Strem 中下一个元素
        // Combiner: 调用一个函数来组合归并操作的结果，当归并是并行执行或者当累加器的函数和累加器的实现类型不匹配时才会调用此函数。
        double productOfSqrRoot = myList.parallelStream().reduce(1.0, (a,b)->a*Math.sqrt(b), (a,b)->a*b);
        System.out.println("reslut: "+productOfSqrRoot);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sum = numbers.stream().reduce(0, Integer::sum);
        Optional<Integer> sumOptional = numbers.stream().reduce(Integer::sum);
        Optional<Integer> maxOptional = numbers.stream().reduce(Integer::max);
        // 下面两个语句结果相同
        numbers.stream().map(d->1).reduce(0, Integer::sum);
        numbers.stream().count();

        // 找出calorie最低的food
        List<Food> list = DataUtil.genData();
        Optional<Food> minFood = list.stream().min(Comparator.comparing(Food::getCalories));
        Optional<Food> reduceFood = list.stream().reduce((f1, f2) -> f1.getCalories() <= f2.getCalories() ? f1 : f2);
        System.out.println("minFood: " + minFood);
        System.out.println("reduceFood: " + reduceFood);

        // 原始类型流特化 IntStream LongStream DoubleStream
        OptionalInt maxCalorieFood = list.stream().mapToInt(Food::getCalories).max();

        // 要将数值范围装箱成为一个一般流
        IntStream intStream = list.stream().mapToInt(Food::getCalories);
        Stream<Integer> boxed = intStream.boxed();
    }


    /**
     * 内部迭代和外部迭代
     * 数据量大的时候，一定要使用Stream迭代，内部迭代
     * 使用Collection接口需要用户去做迭代（比如用for-each），这称为外部迭代
     * Stream 是使用内部迭代的方式
     */
    @Test
    public void inOutIterate() {
        List<Food> list = DataUtil.genData();
        List<String> nameList = list.stream()
                .map(Food::getName)
                .collect(Collectors.toList());
    }

    /**
     * 和迭代器类似，流只能遍历一次
     */
    @Test
    public void times() {
        List<String> title = Arrays.asList("Java8", "In", "Action");
        Stream<String> s = title.stream();
        s.forEach(System.out::println);
        // java.lang.IllegalStateException:流已被操作或关闭
        s.forEach(System.out::println);
    }

    /**
     * 查找和匹配 短路求值
     */
    @Test
    public void searchMatch() {
        List<Food> list = DataUtil.genData();
        boolean b1 = list.stream().allMatch(f -> f.getCalories() < 200);
        boolean b = list.stream().anyMatch(f -> f.getCalories() > 100);
        Optional<Food> food = list.stream().filter(f -> f.getCalories() < 100).findAny();
        System.out.println();
    }

    @Test
    public void flatMap() {
        // 扁平化流 flatMap方法让你把一个流中的每个值都换成另一个流，然后把所有的流连接起来成为一个流
        String[] sarray = {"lvxin", "siyuan"};
        List<String> list = Arrays.asList(sarray);
        List<String> distinctAlpha = list.stream()
                .map((s) -> s.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(distinctAlpha);

        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs =
                numbers1.stream()
                        .flatMap(i -> numbers2.stream()
                                .map(j -> new int[]{i, j})
                        )
                        .collect(Collectors.toList());
        for (int[] pair : pairs) {
            for (int i = 0; i < pair.length; i++) {
                System.out.println(pair[i]);
            }
        }
    }

//    @Test
    public void parallelStream() {
        List<Food> list = DataUtil.genData();
        List<String> collect = list.parallelStream()
                .filter((food) -> food.getCalories() > 100)
                .sorted(Comparator.comparingInt(Food::getCalories).reversed())
                .limit(1)
                .map(Food::getName)
                .collect(Collectors.toList());
        System.out.println(collect);

        // 哈希码的作用是确定该对象在哈希表中的索引位置
        // 重写equals方法时 必须重写hashCode方法(hashCode: 堆上的对象计算出的一个值)
        // 两个对象equals时 hashCode也一定是相同的; 但是两个对象有相同的hashCode值 但他们并不是equals的
        HashSet<String> set = new HashSet<>();
        // add方法 会先调用hashCode判断是否相等 效率高
        set.add("s");

    }

    @Test
    public void distinct() {
        // distinct方法是根据流所生成元素的hashCode和equals方法实现
        List<Food> list = DataUtil.genData();
        List<Food> distinct = list.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(distinct);

    }
    
}
