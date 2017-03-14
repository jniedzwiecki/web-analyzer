package webanalyzer;

import webanalyzer.configs.ServicesConfiguration;
import webanalyzer.exceptions.ToHttpErrorExceptionMapper;
import webanalyzer.services.WebAnalyzerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by jacekniedzwiecki on 03.03.2017.
 */
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                ServicesConfiguration.class,
                WebAnalyzerService.class,
                ToHttpErrorExceptionMapper.class
        );
        ctx.start();
    }
}
