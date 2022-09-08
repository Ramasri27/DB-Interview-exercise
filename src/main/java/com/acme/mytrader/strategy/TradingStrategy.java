package com.acme.mytrader.strategy;
import java.util.HashMap;
import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;
/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy implements PriceListener{
	
    private Integer lot;
	
    public void setLot(Integer lot) {
        this.lot = lot;
    }
    public Integer getLot(){
        return lot;
    }
	
    private ExecutionService executionService;
    private HashMap<String, Double> buyThresholds;
    private HashMap<String, Double> sellThresholds;
    private static Integer defaultLot = 100;

    public TradingStrategy(ExecutionService broker, Integer lot) {
        this.executionService = broker;
        this.buyThresholds = new HashMap<String,Double>();
        this.sellThresholds = new HashMap<String,Double>();
        this.lot = lot;
    }

    public TradingStrategy(ExecutionService broker) {
        this(broker, defaultLot);
    }

    public void priceUpdate(String security, double price)
    {
        stockStrategy(security, price);
    }

    /**
     * Assume the price will bounce back or fall back from current price, hence buy or sell at low or high prices.
     * @param security The name of the security
     * @param price The current market price
     */
    private void stockStrategy(String security, Double price){
        Double buyThreshold = buyThresholds.get(security);
        Double sellThreshold = sellThresholds.get(security);
        if(buyThreshold != null && price < buyThreshold){
            executionService.buy(security, price, getLot());
        }
        if (sellThreshold != null && price > sellThreshold) {
            executionService.sell(security, price, getLot());
        }
    }
    

    public void setBuyThreshold(String security, Double buyThreshold){
        buyThresholds.put(security, buyThreshold);
    }

    public void setSellThreshold(String security, Double sellThreshold){     
        sellThresholds.put(security, sellThreshold);
    }

  
}
