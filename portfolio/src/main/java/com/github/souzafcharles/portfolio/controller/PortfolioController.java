package com.github.souzafcharles.portfolio.controller;

import com.github.souzafcharles.portfolio.dto.PortfolioRequestDTO;
import com.github.souzafcharles.portfolio.model.Portfolio;
import com.github.souzafcharles.portfolio.service.PortfolioService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService service;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.process.name}")
    private String routerKey;

    @PostMapping
    public String create(@RequestBody Portfolio portfolio) {
        Portfolio created = service.create(portfolio);
        PortfolioRequestDTO dto = new PortfolioRequestDTO(created.getId(), created.getTitle());
        rabbitTemplate.convertAndSend("", routerKey, dto);
        return "Portfolio saved and sent for processing: " + portfolio.getTitle();
    }

    @GetMapping
    public List<Portfolio> readAll() {
        return service.readAll();
    }
}