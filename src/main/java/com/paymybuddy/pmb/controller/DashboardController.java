package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.dto.ConnectionDTO;
import com.paymybuddy.pmb.dto.TransactionDTO;
import com.paymybuddy.pmb.dto.TransactionSubmitDTO;
import com.paymybuddy.pmb.dto.UserDTO;
import com.paymybuddy.pmb.service.TransactionService;
import com.paymybuddy.pmb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
public class DashboardController {

    private final UserService userService;
    private final TransactionService transactionService;

    public DashboardController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        UserDTO userDTO = userService.getUserByUsername(principal.getName());
        Set<ConnectionDTO> connectionDTOSet = userDTO.getConnections();
        List<TransactionDTO> transactionDTOList = transactionService.getTransactionsByUsername(principal.getName());
        model.addAttribute("user", userDTO);
        model.addAttribute("connections", connectionDTOSet);
        model.addAttribute("transactions", transactionDTOList);
        model.addAttribute("transactionSubmitDTO", new TransactionSubmitDTO());
        return "dashboard";
        }

    @PostMapping("/transaction")
    public String TransactionSubmit(@ModelAttribute("transaction") TransactionSubmitDTO transactionSubmitDTO, Principal principal) {
        transactionService.createTransaction(transactionSubmitDTO.getAmount(), principal.getName(), transactionSubmitDTO.getReceiver());
        return "redirect:/dashboard";
    }

}
