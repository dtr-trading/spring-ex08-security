package com.dtr.oas.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.dtr.oas.model.Strategy;

@Repository
public class StrategyDAOImpl implements StrategyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void addStrategy(Strategy strategy) {
        getCurrentSession().save(strategy);
    }

    public void updateStrategy(Strategy strategy) {
        Strategy strategyToUpdate = getStrategy(strategy.getId());
        strategyToUpdate.setName(strategy.getName());
        strategyToUpdate.setType(strategy.getType());
        getCurrentSession().update(strategyToUpdate);
    }

    public Strategy getStrategy(int id) {
        Strategy strategy = (Strategy) getCurrentSession().get(Strategy.class, id);
        return strategy;
    }

    public void deleteStrategy(int id) {
        Strategy strategy = getStrategy(id);
        if (strategy != null)
            getCurrentSession().delete(strategy);
    }

    @SuppressWarnings("unchecked")
    public List<Strategy> getStrategies() {
        return getCurrentSession().createQuery("from Strategy").list();
    }

}
