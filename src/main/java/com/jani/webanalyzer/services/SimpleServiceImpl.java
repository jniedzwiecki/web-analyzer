package com.jani.webanalyzer.services;

import org.springframework.stereotype.Service;

/**
 * Created by jacekniedzwiecki on 06.03.2017.
 */
@Service
public class SimpleServiceImpl implements SimpleService {

    @Override
    public String apply(String name) {
        return "simple " + name;
    }
}
