package com.bolaneradarmini.web;

import com.bolaneradarmini.entity.Bank;
import com.bolaneradarmini.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private BankRepository bankRepository;

    @GetMapping("/banks")
    public String showBanks(Model model) {
        List<Bank> banks = bankRepository.findAll();
        model.addAttribute("banks", banks);
        return "banks";
    }
}