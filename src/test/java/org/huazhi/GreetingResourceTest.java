package org.huazhi;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
class GreetingResourceTest {
    @Test
    void testHelloEndpoint() {
        System.out.println("testHelloEndpoint");
    }

}