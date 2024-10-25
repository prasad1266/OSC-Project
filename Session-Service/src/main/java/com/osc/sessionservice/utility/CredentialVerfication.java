package com.osc.sessionservice.utility;

import com.osc.sessionservice.dto.Logindto;
import com.osc.sessionservice.dto.VerifyCredentialsResponseDto;
import com.osc.sessionservice.exception.InvalidPasswordException;
import com.osc.sessionservice.exception.InvalidUserIDException;
import com.session.VerifyCredentialsResponse;
import org.springframework.stereotype.Component;

@Component
public class CredentialVerfication {

    public static Boolean verifyCredentials(Logindto logindto, VerifyCredentialsResponse responseDto){

        if(responseDto.getPassword().isEmpty() || responseDto.getPassword()=="" ) {
            throw new InvalidUserIDException();
        }

        if(!logindto.getPassword().equals(responseDto.getPassword())){
            throw new InvalidPasswordException();
        }
        return true;
    }
}


//InvalidPasswordException      {"code": 202," data Object ": null}
//InvalidUserIDException            Response: {"code": 201," data Object ": null}