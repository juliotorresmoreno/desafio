package com.example.desafio.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafio.models.Bank;
import com.example.desafio.models.Debt;
import com.example.desafio.models.Payment;
import com.example.desafio.models.User;
import com.example.desafio.responses.ResponseError;
import com.example.desafio.responses.ResponseOK;
import com.example.desafio.services.BankService;
import com.example.desafio.services.DebtService;
import com.example.desafio.services.UserService;
import com.example.desafio.types.DebtStatus;
import com.example.desafio.utils.Calc;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.Instant;

@RestController
@RequestMapping("/me")
public class MeController {
    Logger logger;

    @Autowired
    UserService userService;

    @Autowired
    BankService bankService;

    @Autowired
    DebtService debtService;

    @Autowired
    private Environment env;

    public MeController() {
        this.logger = Logger.getGlobal();
    }

    public static class POSTAssignToBankBody {
        Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    interface POSTAssignToBankResponse {
    }

    class POSTAssignToBankOK extends ResponseOK implements POSTAssignToBankResponse {
        POSTAssignToBankOK() {
            super();
        }
    }

    class POSTAssignToBankError extends ResponseError implements POSTAssignToBankResponse {
        POSTAssignToBankError() {
            super();
        }

        POSTAssignToBankError(String err) {
            super(err);
        }
    }

    private void POSTAssignToBankValidate(POSTAssignToBankBody data) {

    }

    @PostMapping(value = "/assignToBank", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<POSTAssignToBankResponse> POSTAssignToBank(HttpServletRequest request,
            @RequestBody POSTAssignToBankBody content) {
        try {
            POSTAssignToBankValidate(content);

            /**
             * Esto no deberia estar aqui pero para poder tener un api funcional y para
             * fines
             * demostrativos lo pongo aca. Normalmente esto estaria en users y tendria un
             * estricto
             * control de roles y permisos.
             */

            final var session = request.getSession();
            final var user = (User) session.getAttribute("user");

            final var _bank = bankService.findById(content.id);

            if (_bank.isEmpty() || _bank == null) {
                final var response = new POSTAssignToBankError();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            final var bank = _bank.get();

            user.getBanks().add(bank);

            userService.save(user);

            return new ResponseEntity<>(new POSTAssignToBankOK(), HttpStatus.OK);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            final var response = new POSTAssignToBankError();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    interface GETBanksResponse {
    }

    class GETBankskOK extends ArrayList<Bank> implements GETBanksResponse {
        GETBankskOK(List<Bank> banks) {
            for (int i = 0; i < banks.size(); i++) {
                this.add(banks.get(i));
            }
        }
    }

    class GETBanksError extends ResponseError implements GETBanksResponse {
        GETBanksError() {
            super();
        }
    }

    @GetMapping(value = "/banks", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<GETBanksResponse> GETBanks(HttpServletRequest request) {
        try {
            final var session = request.getSession();
            final var user = (User) session.getAttribute("user");
            final var result = userService.findUserByEmail(user.getEmail());
            final var response = new GETBankskOK(result.getBanks());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            final var response = new GETBanksError();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    interface GETDebtsResponse {
    }

    class GETDebtsOK extends ArrayList<Debt> implements GETDebtsResponse {
        GETDebtsOK() {
        }

        GETDebtsOK(List<Debt> debts) {
            for (int i = 0; i < debts.size(); i++) {
                var debt = debts.get(i);
                // debt.setUser(null);
                debt.getUser().setPassword("");
                this.add(debt);
            }
        }
    }

    class GETDebtsError extends ResponseError implements GETDebtsResponse {
        GETDebtsError() {
            super();
        }
    }

    @GetMapping(value = "/debts", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<GETDebtsResponse> GETDebts(HttpServletRequest request) {
        try {
            final var session = request.getSession();
            final var user = (User) session.getAttribute("user");

            final var debts = this.debtService.findByUserId(user.getId());

            final var response = new GETDebtsOK(debts);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            final var response = new GETDebtsError();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    interface POSTCreateDebtsResponse {
    }

    class POSTCreateDebtsOK extends ResponseOK implements POSTCreateDebtsResponse {
        POSTCreateDebtsOK() {
            super();
        }
    }

    class POSTCreateDebtsError extends ResponseError implements POSTCreateDebtsResponse {
        POSTCreateDebtsError() {
            super();
        }
    }

    public static class POSTCreateDebtsBody {
        Long bankId;
        Long amount;
        int dues;
        String concept;

        public POSTCreateDebtsBody() {
        }

        public Long getBankId() {
            return bankId;
        }

        public void setBankId(Long bankId) {
            this.bankId = bankId;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public String getConcept() {
            return concept;
        }

        public void setConcept(String concept) {
            this.concept = concept;
        }

        public int getDues() {
            return dues;
        }

        public void setDues(int dues) {
            this.dues = dues;
        }
    }

    private void POSTCreateDebtsValidate(POSTCreateDebtsBody data) {

    }

    @PostMapping(value = "/createDebts", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<POSTCreateDebtsResponse> POSTCreateDebts(
            HttpServletRequest request,
            @RequestBody POSTCreateDebtsBody payload) {
        try {
            POSTCreateDebtsValidate(payload);

            /**
             * Esto no deberia estar aqui pero para poder tener un api funcional y para
             * fines
             * demostrativos lo pongo aca. Normalmente esto estaria en users y tendria un
             * estricto
             * control de roles y permisos.
             */

            final var session = request.getSession();
            final var user = (User) session.getAttribute("user");
            final var _bank = bankService.findById(payload.bankId);

            if (_bank.isEmpty() || _bank == null) {
                final var response = new POSTCreateDebtsError();
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            var bank = _bank.get();

            var interest = Calc.rateOfInterest(user);
            var monthlyPayment = (long) Calc.calculate(payload.amount, interest, payload.dues);
            var debt = new Debt();
            debt.setBank(bank);
            debt.setUser(user);
            debt.setConcept(payload.concept);
            debt.setCurrentBalance(monthlyPayment * payload.dues);
            debt.setInitialBalance(payload.amount);
            debt.setDues(payload.dues);
            debt.setMonthlyPayment(monthlyPayment);
            debt.setFeesPaid((long) 0);
            debt.setStatus(DebtStatus.ACTIVE);
            debt.setCreatedDate(Instant.now());
            debt.setLastModifiedDate(Instant.now());
            this.debtService.save(debt);

            userService.save(user);

            return new ResponseEntity<>(new POSTCreateDebtsOK(), HttpStatus.OK);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            final var response = new POSTCreateDebtsError();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    interface POSTPayFeeResponse {
    }

    class POSTPayFeeOK extends ResponseOK implements POSTPayFeeResponse {
        POSTPayFeeOK() {
            super();
        }
    }

    class POSTPayFeeError extends ResponseError implements POSTPayFeeResponse {
        POSTPayFeeError() {
            super();
        }

        POSTPayFeeError(String err) {
            super(err);
        }
    }

    public static class POSTPayFeeBody {
        Long id;
        Long amount;

        public POSTPayFeeBody() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }
    }

    private void POSTPayFeeValidate(POSTPayFeeBody data) {

    }

    @PostMapping(value = "/payFee", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    }, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<POSTPayFeeResponse> POSTPayFee(
            HttpServletRequest request,
            @RequestBody POSTPayFeeBody payload) {
        try {
            POSTPayFeeValidate(payload);

            /**
             * Esto no deberia estar aqui pero para poder tener un api funcional y para
             * fines
             * demostrativos lo pongo aca. Normalmente esto estaria en users y tendria un
             * estricto
             * control de roles y permisos.
             */

            final var session = request.getSession();
            final var user = (User) session.getAttribute("user");
            final var _debt = debtService.findByUserIdAndId(user.getId(), payload.getId());

            if (_debt == null) {
                final var response = new POSTPayFeeError("No se encontro el credito");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            var debt = _debt.get();

            if (debt.getStatus() == DebtStatus.INACTIVE) {
                final var response = new POSTPayFeeError("Esta deuda ya ha sido pagada!.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            var feesPaid = debt.getFeesPaid();
            var currentBalance = debt.getCurrentBalance();
            var monthlyPayment = debt.getMonthlyPayment();
            // var lastPayment = debt.getLastPayment();

            // TODO: Validar si el pago se da en fecha correcta o si lleva mora y 
            // aplicar los intereses que correspondan. Aca no tengo ni idea de si 
            // los intereses se calculan sobre la cuota o sobre el monto total, 
            // por lo que he decidido mantener los valores.

            if (!monthlyPayment.equals(payload.amount)) {
                final var response = new POSTPayFeeError("El monto a pagar debe ser " + monthlyPayment + "!.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            debt.setFeesPaid(feesPaid + 1);
            debt.setCurrentBalance(currentBalance - payload.amount);
            debt.setLastPayment(Instant.now());
            debt.setLastModifiedDate(Instant.now());
            if (debt.getCurrentBalance() <= 0) {
                debt.setCurrentBalance((long)0);
                debt.setStatus(DebtStatus.INACTIVE);
            }

            this.debtService.save(debt);

            var payment = new Payment();
            payment.setAmount(payload.amount);
            payment.setDebt(debt);
            payment.setPaymentDate(Instant.now());
            payment.setCreatedDate(Instant.now());
            payment.setLastModifiedDate(Instant.now());

            return new ResponseEntity<>(new POSTPayFeeOK(), HttpStatus.OK);
        } catch (Exception e) {
            logger.warning(e.getMessage());
            final var response = new POSTPayFeeError();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
