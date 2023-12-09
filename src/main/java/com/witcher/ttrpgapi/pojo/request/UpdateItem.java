package com.witcher.ttrpgapi.pojo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItem {

    @NotNull
    private Integer itemId;
    @NotNull
    private String attribute;
    @NotNull
    private String value;

public int getIntValue(){
    return Integer.parseInt(this.value);
}
    public double getDoubleValue(){
        return Double.valueOf(this.value);
    }
}
