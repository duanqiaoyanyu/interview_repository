package com.cskaoyan;

import org.hamcrest.Matcher;

import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.*;

/**
 * ?
 */
public class Test19 {

    Seller seller = mock(Seller.class);
    Shop shop = new Shop(seller);

    public void shouldBuyBread() throws Exception {
        //given
        given(seller.askForBread()).willReturn(new Bread());

        //when
        Goods goods = shop.buyBread();

        //then
        assertThat(goods, containBread());
    }

    private Matcher<? super Goods> containBread() {
        return null;
    }

    static class Seller {
        public Bread askForBread() {
            return new Bread();
        }
    }

    static class Shop {
        private Seller seller;

        public Shop(Seller seller) {
            this.seller = seller;
        }

        public Goods buyBread() {
            return new Goods(seller.askForBread());
        }
    }

    static class Bread {
    }

    static class Goods {
        private Bread bread;

        public Goods(Bread bread) {
            this.bread = bread;
        }

        public Bread getBread() {
            return bread;
        }
    }

}
