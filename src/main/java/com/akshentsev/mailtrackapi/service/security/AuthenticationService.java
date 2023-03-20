package com.akshentsev.mailtrackapi.service.security;

import com.akshentsev.mailtrackapi.dto.AuthenticationRequest;
import com.akshentsev.mailtrackapi.dto.AuthenticationResponse;
import com.akshentsev.mailtrackapi.dto.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
