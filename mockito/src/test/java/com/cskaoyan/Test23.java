package com.cskaoyan;

public class Test23 {

    public void test() {

        //instead:
//        @Spy BeerDrinker drinker = new BeerDrinker();
//        //you can write:
//        @Spy BeerDrinker drinker;
//
//        //same applies to @InjectMocks annotation:
//        @InjectMocks LocalPub;
    }

    static class BeerDrinker {
        public void drink(Beer beer) {
            //drink beer
        }
    }

    static class Beer {
    }

    static class LocalPub {
        private BeerDrinker drinker;
        private Beer beer;

        public void drinkBeer() {
            drinker.drink(beer);
        }
    }

}
