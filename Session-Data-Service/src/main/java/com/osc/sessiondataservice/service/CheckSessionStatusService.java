package com.osc.sessiondataservice.service;

import com.session.SessionStatusRequest;
import com.session.SessionStatusResponse;

public interface CheckSessionStatusService {
    SessionStatusResponse checkSessionSattusinKtable(SessionStatusRequest sessionStatusRequest);
}
