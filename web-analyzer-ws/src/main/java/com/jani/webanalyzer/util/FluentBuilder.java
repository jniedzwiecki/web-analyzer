package com.jani.webanalyzer.util;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by jacekniedzwiecki on 12.03.2017.
 */
public class FluentBuilder<T> {

    private T t;

    public static <T> FluentBuilder<T> with(T t) {
        return new FluentBuilder<>(t);
    }

    private FluentBuilder(T t) {
        this.t = t;
    }

    public FluentBuilder<T> op(Consumer<T> func) {
        func.accept(t);
        return this;
    }

    public T get() {
        return t;
    }

    public <U> U get(Function<T, U> func) {
        return func.apply(t);
    }
}
