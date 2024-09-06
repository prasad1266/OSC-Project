package com.osc.userdataservice.service;

import com.user.UniqueEmailRequest;
import com.user.UniqueEmailResponse;

public interface VerifyEmailAddressService {

    UniqueEmailResponse verifyEmailAddressIsUnique(UniqueEmailRequest uniqueEmailRequest);
}
