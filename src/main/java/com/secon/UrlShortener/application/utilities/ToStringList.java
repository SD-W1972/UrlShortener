package com.secon.UrlShortener.application.utilities;

import com.secon.UrlShortener.domain.model.Click;

import java.util.List;

public class ToStringList {

    public static List<String> toStringList(List<Click> clicks){
        return clicks.stream()
                .map(Click::toString)
                .toList();
    }
}
