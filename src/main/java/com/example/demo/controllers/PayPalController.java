package com.example.demo.controllers;

import com.example.demo.services.PayPalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/paypal")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PayPalController {

    @Autowired
    private PayPalService payPalService;

    @PostMapping("/pay")
    public Map<String, String> pay(@RequestParam("amount") Double amount, @RequestParam("abonnementId") Long abonnementId) {
        Map<String, String> response = new HashMap<>();
        try {
            Payment payment = payPalService.createPayment(
                    amount,
                    "USD",
                    "paypal",
                    "sale",
                    "Abonnement Gym",
                    "http://localhost:4200/cancel",
                    "http://localhost:4200/success"
            );

            String approvalUrl = payment.getLinks().stream()
                    .filter(link -> link.getRel().equals("approval_url"))
                    .findFirst()
                    .map(link -> link.getHref())
                    .orElse(null);

            if (approvalUrl != null) {
                response.put("approvalUrl", approvalUrl);
            } else {
                response.put("error", "No approval URL found");
            }

        } catch (PayPalRESTException e) {
            response.put("error", "Erreur lors du paiement : " + e.getMessage());
        }
        return response;
    }



    @GetMapping("/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,@RequestParam("abonnementId") Long abonnementId) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                payPalService.enregistrerPaiementPaypal(payment, abonnementId);

                // 👉 Tu peux ici enregistrer le paiement en base
                return "Paiement réussi ✅. Merci pour votre abonnement !";
            }
        } catch (PayPalRESTException e) {
            return "Erreur de validation : " + e.getMessage();
        }
        return "Paiement non approuvé ❌";
    }

    @GetMapping("/cancel")
    public String cancelPay() {
        return "Paiement annulé ❌";
    }
}
