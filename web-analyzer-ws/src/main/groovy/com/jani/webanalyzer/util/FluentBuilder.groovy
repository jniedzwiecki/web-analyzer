package com.jani.webanalyzer.util

import java.util.function.Consumer
import java.util.function.Function

/**
 * Created by jacekniedzwiecki on 12.03.2017.
 */
class FluentBuilder<T> {

    final T t

    static <T> FluentBuilder<T> with(T t) {
        return new FluentBuilder<>(t)
    }

    private FluentBuilder(T t) {
        this.t = t
    }

    FluentBuilder<T> op(Consumer<T> func) {
        func.accept(t)
        this
    }

    T get() { t }

    def <U> U get(Function<T, U> func) {
        func.apply(t)
    }
}
