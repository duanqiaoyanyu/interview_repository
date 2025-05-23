package com.cskaoyan;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Test24 {

    @Test
    public void test() {
        Car boringStubbedCar = when(mock(Car.class).shiftGear()).thenThrow(EngineNotStarted.class).getMock();
    }

    static class Car {
        public Integer shiftGear() {
            throw new EngineNotStarted();
        }
    }

    static class EngineNotStarted extends RuntimeException {
    }
}
