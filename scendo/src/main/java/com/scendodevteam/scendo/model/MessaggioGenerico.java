package com.scendodevteam.scendo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessaggioGenerico {

    private Object message;
    private String code;

}
