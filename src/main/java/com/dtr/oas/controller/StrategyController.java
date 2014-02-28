package com.dtr.oas.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dtr.oas.model.Strategy;
import com.dtr.oas.service.StrategyService;

@Controller
@RequestMapping(value = "/strategy")
public class StrategyController {
    static Logger logger = LoggerFactory.getLogger(StrategyController.class);

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String listOfStrategies(Model model) {
        logger.info("IN: Strategy/list-GET");

        List<Strategy> strategies = strategyService.getStrategies();
        model.addAttribute("strategies", strategies);

        // if there was an error in /add, we do not want to overwrite
        // the existing strategy object containing the errors.
        if (!model.containsAttribute("strategy")) {
            logger.info("Adding Strategy object to model");
            Strategy strategy = new Strategy();
            model.addAttribute("strategy", strategy);
        }
        return "strategy-list";
    }              
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addingStrategy(@Valid @ModelAttribute Strategy strategy,
            BindingResult result, RedirectAttributes redirectAttrs) {

        logger.info("IN: Strategy/add-POST");

        if (result.hasErrors()) {
            logger.info("Strategy-add error: " + result.toString());
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.strategy", result);
            redirectAttrs.addFlashAttribute("strategy", strategy);
            return "redirect:/strategy/list";
        } else {
            strategyService.addStrategy(strategy);
            String message = "Strategy " + strategy.getId() + " was successfully added";
            redirectAttrs.addFlashAttribute("message", message);
            return "redirect:/strategy/list";
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editStrategyPage(@RequestParam(value = "id", required = true) Integer id, Model model) {
        logger.info("IN: Strategy/edit-GET:  ID to query = " + id);

        if (!model.containsAttribute("strategy")) {
            logger.info("Adding Strategy object to model");
            Strategy strategy = strategyService.getStrategy(id);
            logger.info("Strategy/edit-GET:  " + strategy.toString());
            model.addAttribute("strategy", strategy);
        }

        return "strategy-edit";
    }
        
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editingStrategy(@Valid @ModelAttribute Strategy strategy,
            BindingResult result, RedirectAttributes redirectAttrs,
            @RequestParam(value = "action", required = true) String action) {

        logger.info("IN: Strategy/edit-POST: " + action);

        if (action.equals(messageSource.getMessage("button.action.cancel", null, Locale.US))) {
            String message = "Strategy " + strategy.getId() + " edit cancelled";
            redirectAttrs.addFlashAttribute("message", message);
        } else if (result.hasErrors()) {
            logger.info("Strategy-edit error: " + result.toString());
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.strategy", result);
            redirectAttrs.addFlashAttribute("strategy", strategy);
            return "redirect:/strategy/edit?id=" + strategy.getId();
        } else if (action.equals(messageSource.getMessage("button.action.save",  null, Locale.US))) {
            logger.info("Strategy/edit-POST:  " + strategy.toString());
            strategyService.updateStrategy(strategy);
            String message = "Strategy " + strategy.getId() + " was successfully edited";
            redirectAttrs.addFlashAttribute("message", message);
        }

        return "redirect:/strategy/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteStrategyPage(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "phase", required = true) String phase,
            Model model) {

        Strategy strategy = strategyService.getStrategy(id);
        logger.info("IN: Strategy/delete-GET | id = " + id + " | phase = " + phase + " | " + strategy.toString());

        if (phase.equals(messageSource.getMessage("button.action.cancel", null, Locale.US))) {
            String message = "Strategy delete was cancelled.";
            model.addAttribute("message", message);
            return "redirect:/strategy/list";
        } else if (phase.equals(messageSource.getMessage("button.action.stage", null, Locale.US))) {
            String message = "Strategy " + strategy.getId() + " queued for display.";
            model.addAttribute("strategy", strategy);
            model.addAttribute("message", message);
            return "strategy-delete";
        } else if (phase.equals(messageSource.getMessage("button.action.delete", null, Locale.US))) {
            strategyService.deleteStrategy(id);
            String message = "Strategy " + strategy.getId() + " was successfully deleted";
            model.addAttribute("message", message);
            return "redirect:/strategy/list";
        }

        return "redirect:/strategy/list";
    }
}
