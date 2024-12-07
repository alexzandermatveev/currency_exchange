package com.example.currency.exchange.dto;

import com.example.currency.exchange.adapter.DateAdapter;
import com.example.currency.exchange.adapter.DoubleAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankResponse {
    @XmlAttribute(name = "Date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date date;
    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "Valute")
    private List<Valute> valuteList;


    @XmlAccessorType(XmlAccessType.FIELD) // доступ к полям, а не через методы
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Valute {
        @XmlAttribute(name = "ID")
        private String id;
        @XmlElement(name = "NumCode")
        private String numCode;
        @XmlElement(name = "CharCode")
        private String charCode;
        @XmlElement(name = "Nominal")
        private Long nominal;
        @XmlElement(name = "Name")
        private String name;
        @XmlElement(name = "Value")
        @XmlJavaTypeAdapter(DoubleAdapter.class)
        private Double value;
        @XmlElement(name = "VunitRate")
        @XmlJavaTypeAdapter(DoubleAdapter.class)
        private Double vunitRate;
    }
}
