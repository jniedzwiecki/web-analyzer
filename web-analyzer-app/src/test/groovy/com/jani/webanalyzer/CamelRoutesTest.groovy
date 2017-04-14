package com.jani.webanalyzer

import com.fasterxml.jackson.databind.ObjectMapper
import com.jani.webanalyzer.request.AddPathsRequest
import com.jani.webanalyzer.request.AddSinglePathRequest
import org.apache.camel.EndpointInject
import org.apache.camel.Produce
import org.apache.camel.ProducerTemplate
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.component.mock.MockEndpoint
import org.apache.camel.test.junit4.CamelTestSupport
import org.junit.Test

import static com.jani.webanalyzer.utils.FluentBuilder.with
import static java.util.stream.Collectors.toList
/**
 * Created by jacekniedzwiecki on 12.04.2017.
 */
class CamelRoutesTest extends CamelTestSupport {

    static final List<String> PATHS = ["http://A.0", "http://B.1", "http://C.2"]

    @Produce(uri = "direct:addPathsReqEndpoint")
    ProducerTemplate addPathsReqEndpoint

    @EndpointInject(uri = "mock:addSinglePathEndpoint")
    MockEndpoint addSinglePathEndpoint

    @EndpointInject(uri = "mock:storageServiceEndpoint")
    MockEndpoint storageServiceEndpoint

    ObjectMapper objectMapper = new ObjectMapper()

    @Test
    void checkSplitterTest() {
        addPathsReqEndpoint.sendBody(
                with(new AddPathsRequest()).op { it.paths = PATHS }.map { objectMapper.writeValueAsString(it) }
        )

        def addSinglePathRequestsPaths = storageServiceEndpoint.exchanges.stream()
                .map { (it.in.body as AddSinglePathRequest).path }
                .collect(toList())
        assert [addSinglePathRequestsPaths, PATHS].transpose().stream().allMatch { it[0] == it[1] }

        def addSinglePathRequestsAsStrings = addSinglePathEndpoint.exchanges.stream()
                .map { it.in.body as String }
                .collect(toList())
        assert [addSinglePathRequestsAsStrings, PATHS].transpose().stream().allMatch { (it[0] as String).contains(it[1] as String) }
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            void configure() throws Exception {
                from(addPathsReqEndpoint.defaultEndpoint)
                        .process(WebAnalyzerRoutesBuilder.singlePathExtractingProcessor)
                        .split(simple('${body}'))
                        .wireTap(storageServiceEndpoint)
                        .process(WebAnalyzerRoutesBuilder.objectToJSonProcessor)
                        .to(addSinglePathEndpoint)
            }
        }
    }
}
