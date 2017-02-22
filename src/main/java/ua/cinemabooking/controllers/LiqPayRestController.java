package ua.cinemabooking.controllers;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.cinemabooking.model.BillOrder;
import ua.cinemabooking.repository.BillOrderRepository;
import ua.cinemabooking.service.EmailService;

import javax.xml.bind.DatatypeConverter;
import java.math.BigDecimal;

/**
 * @author Kobylyatskyy Alexander
 */
@Controller
@RequestMapping("/api/rest/liqpay")
public class LiqPayRestController {

    @Autowired
    private BillOrderRepository billOrderRepository;

    @Autowired
    private EmailService emailService;
    /**
     * Catch callback from bank
     */
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public ResponseEntity<Void> callback(@RequestParam(value = "signature", required = false, defaultValue = "") String signature,
                         @RequestParam(value = "data", required = false, defaultValue = "") String data,
                         @RequestBody(required = false) String body) throws Exception {

        String json = new String(DatatypeConverter.parseBase64Binary(data));
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(json);
        JSONObject jsonObj = (JSONObject) obj;

        String orderId = String.valueOf(jsonObj.get("oreder_id"));

        BillOrder billOrder = billOrderRepository.findOne(Long.valueOf(orderId));

        String amount = (String) jsonObj.get("amount");

        if (billOrder != null){



            if (!billOrder.isPayed()){

                String status = String.valueOf(jsonObj.get("status"));

                if (status.equals("sandbox") || status.equals("success")){
                    billOrder.setMoneyCapacity(new BigDecimal(amount));
                    billOrder.setPayed(true);
                    billOrderRepository.save(billOrder);

                    emailService.init();

                    //сюда надо вставить метод отправки смс с билетами на email заказчина

                    emailService.sendMessage("Your places from 'CINEMAbooking'", "Booking cinema bills",billOrder.getEmail());

                }else if (status.equals("error")){
                    return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
                }else if (status.equals("failure")){
                    return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
                }else if (status.equals("reversed")){
                    return new ResponseEntity<Void>(HttpStatus.LOCKED);
                }
            }

            return new ResponseEntity<Void>(HttpStatus.OK);
        }else
        /*
         * В jsonObj.get("order_id") будет лежать id заказа. По этому ID нужно найти order в базе, проверить какой статус пришёл от банка, и если всё ОК,
         * то мы переводим order в статус Оплачено и сохраняем его.
         */

        // ЭТО ПРИМЕР
//        externalRepository.transferOnPersonalAccount(Math.round(((Double)jsonObj.get("amount"))*100), ((String) jsonObj.get("order_id")).substring(8), 1, (String) jsonObj.get("liqpay_order_id"), "success");

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}