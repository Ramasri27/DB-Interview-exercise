package com.acme.mytrader.strategy;
import com.acme.mytrader.execution.ExecutionService;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Matchers.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;


@RunWith(MockitoJUnitRunner.class)
public class TradingStrategyTest {

    TradingStrategy tradingStrategy;

	
    @Mock 
	ExecutionService broker;
    
    @Before
    public void init() {
        tradingStrategy = new TradingStrategy(broker, 100);
        tradingStrategy.setBuyThreshold("CGI", 55.0);
        tradingStrategy.setSellThreshold("CGI", 550.0);
    }
    
    @Test
    public void testBuy() {
        // Price ABOVE buy-threshold.
        tradingStrategy.priceUpdate("CGI", 56.0);
		
		// Price dropped below threshold. Exactly one buy order must be placed.
        Mockito.verify(broker, Mockito.times(1)).buy("CGI", 54.0, 100);

        // No buy oders should be placed.
        Mockito.verify(broker, Mockito.times(0)).buy(anyString(), anyDouble(), anyInt());
        tradingStrategy.priceUpdate("CGI", 54.0);


    }
}

