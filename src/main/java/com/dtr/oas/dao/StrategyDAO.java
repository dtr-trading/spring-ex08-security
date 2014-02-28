package com.dtr.oas.dao;

import java.util.List;
import com.dtr.oas.model.Strategy;

public interface StrategyDAO {

    public void addStrategy(Strategy strategy);

    public Strategy getStrategy(int id);

    public void updateStrategy(Strategy strategy);

    public void deleteStrategy(int id);

    public List<Strategy> getStrategies();

}
