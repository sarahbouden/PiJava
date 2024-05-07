package services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioService {
    private final String accountSid;
    private final String authToken;
    private final String twilioPhoneNumber;

    public TwilioService(String accountSid, String authToken, String twilioPhoneNumber) {
        this.accountSid = "ACe1a37db6f4d67b10418ce32825bbff3a";
        this.authToken = "e2307d7bea11690ee2ed8c65a7057bd3";
        this.twilioPhoneNumber = "+12708060568";
        Twilio.init(accountSid, authToken);
    }

    public void sendSms(String toPhoneNumber, String message) {
        Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(twilioPhoneNumber),
                        message)
                .create();
    }
}