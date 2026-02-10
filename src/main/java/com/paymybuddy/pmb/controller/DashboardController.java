package com.paymybuddy.pmb.controller;

import com.paymybuddy.pmb.dto.*;
import com.paymybuddy.pmb.exceptions.InsufficientBalanceException;
import com.paymybuddy.pmb.exceptions.InvalidAmountException;
import com.paymybuddy.pmb.service.TransactionService;
import com.paymybuddy.pmb.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    /**
     * Displays the user dashboard.
     *
     * @param model     Model used to transmit data.
     * @param principal The current user logged.
     * @return The dashboard view.
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        UserDTO userDTO = userService.getUserByUsername(principal.getName());
        Set<ConnectionDTO> connectionDTOSet = userDTO.getConnections();
        List<TransactionDTO> transactionDTOList = transactionService.getTransactionsByUsername(principal.getName());

        model.addAttribute("user", userDTO);
        model.addAttribute("connections", connectionDTOSet);
        model.addAttribute("transactions", transactionDTOList);
        model.addAttribute("submitTransactionDTO", new SubmitTransactionDTO());
        model.addAttribute("balance", new WalletDTO());

        log.info("Dashboard view");

        return "dashboard";
    }

    /**
     * Validates the submission of a transaction.
     *
     * @param submitTransactionDTO DTO used to represent a transaction with a user.
     * @param bindingResult        The data validation, displays error message if any.
     * @param principal            The current logged user.
     * @param model                Model used to transmit data.
     * @param redirectAttributes   Attributes used to display message after redirect.
     * @return A redirect to the dashboard view.
     */
    @PostMapping("/transaction")
    public String submitTransaction(@Valid @ModelAttribute("transaction") SubmitTransactionDTO submitTransactionDTO, BindingResult bindingResult, Principal principal, Model model, RedirectAttributes redirectAttributes) {

        UserDTO userDTO = userService.getUserByUsername(principal.getName());
        Set<ConnectionDTO> connectionDTOSet = userDTO.getConnections();
        List<TransactionDTO> transactionDTOList = transactionService.getTransactionsByUsername(principal.getName());

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());

            return "redirect:/dashboard";
        }

        try {
            transactionService.createTransaction(submitTransactionDTO.getAmount(), principal.getName(), submitTransactionDTO.getReceiver(), submitTransactionDTO.getDescription());
        } catch (InsufficientBalanceException | InvalidAmountException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            model.addAttribute("user", userDTO);
            model.addAttribute("connections", connectionDTOSet);
            model.addAttribute("transactions", transactionDTOList);
            model.addAttribute("submitTransactionDTO", new SubmitTransactionDTO());
            model.addAttribute("balance", new WalletDTO());
        }

        return "redirect:/dashboard";
    }

    /**
     * Validates the deposit request.
     *
     * @param walletDTO          DTO used to represent a deposit amount.
     * @param bindingResult      The data validation, displays error message if any.
     * @param principal          The current logged user.
     * @param model              Model used to transmit data.
     * @param redirectAttributes Attributes used to display message after redirect.
     * @return A redirect to the dashboard view.
     */
    @PostMapping("/balance")
    public String depositBalance(@Valid @ModelAttribute("balance") WalletDTO walletDTO, BindingResult bindingResult, Principal principal, Model model, RedirectAttributes redirectAttributes) {

        UserDTO userDTO = userService.getUserByUsername(principal.getName());
        Set<ConnectionDTO> connectionDTOSet = userDTO.getConnections();
        List<TransactionDTO> transactionDTOList = transactionService.getTransactionsByUsername(principal.getName());

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());

            return "redirect:/dashboard";
        }

        try {
            transactionService.depositBalance(principal.getName(), walletDTO.getAmount());
        } catch (InvalidAmountException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            model.addAttribute("user", userDTO);
            model.addAttribute("connections", connectionDTOSet);
            model.addAttribute("transactions", transactionDTOList);
            model.addAttribute("submitTransactionDTO", new SubmitTransactionDTO());
            model.addAttribute("balance", new WalletDTO());
        }

        return "redirect:/dashboard";
    }
}
