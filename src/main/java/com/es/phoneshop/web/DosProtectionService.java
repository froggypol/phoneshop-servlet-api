package com.es.phoneshop.web;

import javax.servlet.http.HttpServletRequest;

public interface DosProtectionService {

    boolean allowed(HttpServletRequest request);

}
