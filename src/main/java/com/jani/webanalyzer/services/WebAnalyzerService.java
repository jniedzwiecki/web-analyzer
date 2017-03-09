package com.jani.webanalyzer.services;

import com.jani.webanalyzer.request.AddRequest;
import com.jani.webanalyzer.response.AddResponse;
import com.jani.webanalyzer.response.GetResponse;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;

/**
 * Created by jacekniedzwiecki on 06.03.2017.
 */
@Service
public class WebAnalyzerService implements WebAnalyzer {

    private final List<String> paths = new ArrayList<>();

    @Override
    public AddResponse add(@Valid AddRequest addRequest) {
        paths.add(addRequest.getPath());
        return AddResponse.response(CREATED, paths.size() - 1);
    }

    @Override
    public GetResponse get(int id) {
        return GetResponse.response(paths.get(id));
    }
}
