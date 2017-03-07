package com.jani.webanalyzer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by jacekniedzwiecki on 03.03.2017.
 */
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("com.jani.webanalyzer.configs");
        ctx.start();
    }
}
