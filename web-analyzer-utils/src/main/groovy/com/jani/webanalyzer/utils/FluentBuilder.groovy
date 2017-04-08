package com.jani.webanalyzer.utils

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

    FluentBuilder<T> op(List<Consumer<T>> list) {
        list.forEach { it.accept(t) }
        this
    }

    T lastOp(Consumer<T> func) {
        func.accept(t)
        t
    }

    def <S> S andGet(Function<T, S> func) {
        func.apply(t)
    }

    T get() { t }

    def <U> U map(Function<T, U> func) {
        func.apply(t)
    }
}
