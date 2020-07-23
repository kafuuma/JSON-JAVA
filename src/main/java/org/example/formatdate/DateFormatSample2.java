package org.example.formatdate;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbNumberFormat;
import java.util.Date;

public class DateFormatSample2 {

    @JsonbDateFormat("dd.MM.yyyy")
    public Date date = new Date();

    @JsonbNumberFormat("#.00")
    public Double number = 3.1415926;
}
