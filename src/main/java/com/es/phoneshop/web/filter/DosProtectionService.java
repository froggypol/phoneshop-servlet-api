package com.es.phoneshop.web.filter;

import javax.servlet.http.HttpServletRequest;

public interface DosProtectionService {

    boolean allowed(HttpServletRequest request);

}
