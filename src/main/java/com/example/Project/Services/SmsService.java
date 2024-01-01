package com.example.Project.Services;

import com.example.Project.Config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {


        @Autowired
        private TwilioConfig twilioConfig;

        //SMS
        public void SendSMS(String toNumber,String code){
            // Find your Account Sid and Token at twilio.com/user/account
            Twilio.init(twilioConfig.getAccount_sid(), twilioConfig.getAuth_token());

            Message message = Message.creator(new PhoneNumber("+216" + toNumber),
                    new PhoneNumber(twilioConfig.getSender_number()),"Hello, this is your verification code  to update your password account :"+
                    code).create();

            System.out.println(message.getSid());
        }


    }

