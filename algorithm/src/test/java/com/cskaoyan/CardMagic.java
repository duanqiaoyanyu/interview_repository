package com.cskaoyan;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

import java.util.*;

public class CardMagic {

    /**
     * 地域信息
     */
    private static final Map<String, Integer> REGIONS = MapUtil.newHashMap();

    /**
     * 扑克牌堆
     */
    private static final List<String> CARDS = CollUtil.newArrayList("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K");

    static {
        REGIONS.put("S", 1);
        REGIONS.put("N", 1);
        REGIONS.put("U", 3);
    }


    public static void main(String[] args) {
        List<String> cards = new ArrayList<>();

        Scanner nameScanner = new Scanner(System.in);
        System.out.println("请输入姓名:");
        int nameLength = nameScanner.nextLine().trim().length();
        Scanner regionScanner = new Scanner(System.in);
        System.out.println("请输入地域信息，南方-S, 北方-N, 不知道-U");
        int region = REGIONS.get(regionScanner.nextLine());
        Scanner genderScanner = new Scanner(System.in);
        System.out.println("请输入性别, 女生-F, 男生-M");
        int gender = StrUtil.equals(genderScanner.nextLine(), "M") ? 1 : 2;

        // 初始化扑克牌
        while (cards.size() != 4) {
            int randomInt = RandomUtil.randomInt(13);
            if (cards.contains(CARDS.get(randomInt))) {
                continue;
            }

            cards.add(CARDS.get(randomInt));
        }

        // 撕成两半，叠在下面
        for (int i = 0; i < 4; i++) {
            cards.add(cards.size(), cards.get(i));
        }

        System.out.println(cards);

        // 根据名字长度放多少张牌到最下面
        for (int i = 0; i < nameLength; i++) {
            cards.add(cards.remove(0));
        }
        System.out.println(cards);

        // 拿起顶部三张牌然后把他们插入到剩余5张牌中的随机位置
        List<String> topThree = cards.subList(0, 3);
        List<String> lastFive = cards.subList(3, 8);
        int location = RandomUtil.randomInt(1, lastFive.size() - 1);
        lastFive.addAll(location, topThree);
        cards = new ArrayList<>(lastFive);
        System.out.println(cards);

        // 拿起最上面的牌
        String underButt = cards.remove(0);
        System.out.println("屁股下的牌：" + underButt);

        // 根据地域信息拿起顶部几张牌，插入到剩余牌中的随机位置
        List<String> topCards = cards.subList(0, region);
        List<String> remainingCards = cards.subList(region, cards.size());
        location = RandomUtil.randomInt(1, remainingCards.size() - 1);
        remainingCards.addAll(location, topCards);
        cards = new ArrayList<>(remainingCards);
        System.out.println(cards);

        // 根据性别丢几张牌
        for (int i = 0; i < gender; i++) {
            cards.remove(0);
        }
        System.out.println(cards);

        System.out.println("见证奇迹的时刻");
        // 最上面的牌放在最下面，重复7次
        for (int i = 0; i < 7; i++) {
            String top = cards.remove(0);
            cards.add(cards.size(), top);
            System.out.println(cards);
        }

        for (int i = 0; i < 7; i++) {
            if (1 == cards.size()) {
                break;
            }

            // 好运留下来
            cards.add(cards.remove(0));
            // 烦恼丢出去
            cards.remove(0);
            System.out.println(cards);
        }

        Assert.equals(cards.get(0), underButt);
    }
}
