package com.ldzx.ldzxcodesodebox.unsafe;

import java.sql.SQLOutput;

public class SleepError {
    public static void main(String[] args) throws InterruptedException {
        long ONE = 60 * 60 * 1000L;
        Thread.sleep(ONE);
        System.out.println("睡死了");
    }
}
