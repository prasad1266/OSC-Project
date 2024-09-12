package com.osc.userservice.ServiceImpl;

import com.osc.userservice.dto.UniqueEmailResponseDto;
import com.osc.userservice.mapper.UserMapper;
import com.osc.userservice.service.verifyUniqueEmailService;
import com.user.UniqueEmailRequest;
import com.user.UniqueEmailResponse;
import com.user.UserDataServiceGrpc;
import org.springframework.stereotype.Service;

@Service
public class VerifyUniqueEmailServiceImpl implements verifyUniqueEmailService {

    private UserDataServiceGrpc.UserDataServiceBlockingStub stub;

    public VerifyUniqueEmailServiceImpl(UserDataServiceGrpc.UserDataServiceBlockingStub stub) {
        this.stub = stub;
    }

    @Override
    public boolean verifyUniqueEmail(String email){
        UniqueEmailRequest uniqueEmailRequest = UserMapper.toUniqueEmailRequest(email);
        UniqueEmailResponse uniqueEmailResponse = stub.verifyEmailAddressIsUnique(uniqueEmailRequest);
       boolean isUnique = uniqueEmailResponse.getIsUnique();
        System.out.println(" Email is Unique  :"+isUnique);
       //if(!isUnique)  throw new EmailAlreadyExistException
       return isUnique;
    }
}
