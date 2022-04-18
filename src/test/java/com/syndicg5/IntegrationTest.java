package com.syndicg5;

import com.syndicg5.SyndicWebApp;
import com.syndicg5.config.EmbeddedMongo;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = SyndicWebApp.class)
@EmbeddedMongo
public @interface IntegrationTest {
}
