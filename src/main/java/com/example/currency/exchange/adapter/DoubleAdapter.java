package com.example.currency.exchange.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DoubleAdapter extends XmlAdapter<String, Double> {
    @Override
    public Double unmarshal(String s) throws Exception {
        s = s.replace(",", ".");
        return Double.parseDouble(s);
    }

    @Override
    public String marshal(Double aDouble) throws Exception {
        return aDouble.toString();
    }
}
