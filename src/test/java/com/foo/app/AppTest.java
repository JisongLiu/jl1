package com.foo.app;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        Runner runner = new Runner();

//        assertTrue( runner.run() );
//        assertTrue( runner.crawler(""));
        assertTrue( runner.crawlerUsingHttpClient());
    }
}
