package com.sh;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lxy 953250192@qq.com
 * @version 2019/4/16 14:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class A_19_4_16_Test {

    /**
     * 找出一组数据中大于5小于20并且是偶数的集合
     */
    @Test
    public void a1_Predicate(){
        int[] numbers= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        List<Integer> list=new ArrayList<>();
        for(int i:numbers) {
            list.add(i);
        }
        Predicate<Integer> p1= i -> i > 5;
        Predicate<Integer> p2= i -> i < 20;
        Predicate<Integer> p3= i -> i % 2 == 0;
        List test=list.stream().filter(p1.and(p2).and(p3)).collect(Collectors.toList());
        System.out.println(test.toString());
    }
}
