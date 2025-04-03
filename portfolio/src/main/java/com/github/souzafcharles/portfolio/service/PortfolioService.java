package com.github.souzafcharles.portfolio.service;

import com.github.souzafcharles.portfolio.model.Asset;
import com.github.souzafcharles.portfolio.model.Portfolio;
import com.github.souzafcharles.portfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository repository;

    public Portfolio create(Portfolio entity) {
        if (entity.getAssets() != null) {
            for (Asset asset : entity.getAssets()) {
                asset.setPortfolio(entity);
            }
        }
        return repository.save(entity);
    }

    public List<Portfolio> readAll() {
        return repository.findAll();
    }
}