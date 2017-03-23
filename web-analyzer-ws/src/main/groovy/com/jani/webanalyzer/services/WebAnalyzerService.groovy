package com.jani.webanalyzer.services

import com.jani.webanalyzer.request.AddRequest
import com.jani.webanalyzer.response.AddResponse
import com.jani.webanalyzer.response.GetResponse
import groovy.transform.CompileStatic
import org.apache.activemq.ActiveMQConnectionFactory
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import javax.jms.ExceptionListener
import javax.jms.JMSException
import javax.jms.Session

import static org.slf4j.LoggerFactory.getLogger
/**
 * Created by jacekniedzwiecki on 06.03.2017.
 */
@Service
@CompileStatic
class WebAnalyzerService implements WebAnalyzer {

    private static Logger logger = getLogger(WebAnalyzerService.class)

    private Session session

    WebAnalyzerService(@Value("activemq.endpoint") String activeMqEndpoint,
                       @Value("addPaths.request.endpoint") String addPathsReqEndpoint) {

        def connection = new ActiveMQConnectionFactory(activeMqEndpoint).createConnection()
        connection.start()
        connection.setExceptionListener(new ExceptionListener() {

            @Override
            void onException(JMSException exception) {
                logger.debug(exception.stackToString())
            }
        })

        session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE)
    }

    @Override
    AddResponse add(AddRequest addRequest) {
//        return AddResponse.response(CREATED, paths.size() - 1)
        null
    }

    @Override
    GetResponse get(int id) {
//        return GetResponse.response(paths.get(id))
        null
    }
}
