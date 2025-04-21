package com.example.demo.services;

import com.example.demo.entities.Abonnement;
import com.example.demo.entities.ModePaiement;
import com.example.demo.entities.Paiement;
import com.example.demo.repository.AbonnementRepo;
import com.example.demo.repository.PaiementRepo;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@Service
public class PayPalService {

    @Autowired
    private APIContext apiContext;
    @Autowired
    private PaiementRepo paiementRepo;
    @Autowired
    private AbonnementRepo abonnementRepo;

    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException {

        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.US,"%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = Collections.singletonList(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toUpperCase());

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    public Paiement enregistrerPaiementPaypal(Payment payment, Long abonnementId) {
        Paiement paiement = new Paiement();

        paiement.setMontant(Double.parseDouble(payment.getTransactions().get(0).getAmount().getTotal()));
        paiement.setDatePaiement(new Date());
        paiement.setModePaiement(ModePaiement.valueOf("PayPal"));
        paiement.setReference(payment.getId());

        Abonnement abonnement = abonnementRepo.findById(abonnementId).orElseThrow(() -> new RuntimeException("Abonnement non trouvé"));
        paiement.setAbonnement(abonnement);

        return paiementRepo.save(paiement);
    }


}


