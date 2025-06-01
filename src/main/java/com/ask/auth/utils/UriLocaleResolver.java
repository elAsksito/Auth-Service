package com.ask.auth.utils;

import java.util.Locale;

import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UriLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String[] parts = uri.split("/");
        if (parts.length > 2) {
            String lang = parts[2];
            if ("es".equalsIgnoreCase(lang)) {
                return new Locale("es");
            } else if ("en".equalsIgnoreCase(lang)) {
                return new Locale("en");
            }
        }
        return Locale.getDefault();
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        throw new UnsupportedOperationException("Unimplemented method 'setLocale'");
    }
}