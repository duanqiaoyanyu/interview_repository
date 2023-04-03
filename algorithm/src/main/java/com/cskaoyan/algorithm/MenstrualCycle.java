package com.cskaoyan.algorithm;

import cn.hutool.core.date.TemporalUtil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author duanqiaoyanyu
 * @date 2023/3/6 11:03
 */
public class MenstrualCycle {
    /**
     * 默认最早开始
     */
    private static final LocalDate DEFAULT_EARLIEST = LocalDate.of(2023, 3, 6);
    /**
     * 默认最晚结束
     */
    private static final LocalDate DEFAULT_LATEST = LocalDate.of(2023, 3, 10);
    /**
     * 世纪末的钟声, 其实不能这么算, 因为后续的范围会越来越大, 而且女性还有绝经的情况.
     */
    private static final LocalDate centuryEnd = LocalDate.of(2099, 12, 31);
    /**
     * 周期范围
     */
    private static final Integer[] cycleRange = new Integer[] {24, 28};

    public static void main(String[] args) {
        LocalDate earliest = DEFAULT_EARLIEST;
        LocalDate latest = DEFAULT_LATEST;

        int times = 0;
        // 考虑到经期的可预测性, 由于周期区间大小是一个以 5 为公差的等差数列, 所以暂时只模拟三个月内的情况, 再长一点的时间参考意义不大
        while (times++ < 3) {
            Integer early = cycleRange[0];
            Integer late = cycleRange[1];

            LocalDate happenDate = earliest;
            long betweenDays = TemporalUtil.between(earliest, latest, ChronoUnit.DAYS);
            for (int i = early; i <= early + betweenDays; i++) {
                System.out.println(happenDate);
                happenDate = happenDate.plusDays(1);
            }

            System.out.println("================================");

            earliest = earliest.plusDays(early);
            latest = happenDate.plusDays(late);
        }

    }
}
