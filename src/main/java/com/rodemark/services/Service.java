package com.rodemark.services;

import jakarta.servlet.http.HttpServletRequest;

public interface Service {
    String run(HttpServletRequest request);
}
