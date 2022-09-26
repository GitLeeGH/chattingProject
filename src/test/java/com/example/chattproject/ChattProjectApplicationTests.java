package com.example.chattproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChattProjectApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void test1() {

        int v1 =10;
        int v2 =20;

        Assertions.assertEquals(v1,v2);
    }

}
