package com.witcher.ttrpgapi.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Armor {


    private int id;
    @NotNull
    private String name;
    @NotNull
    private String category;
    @NotNull
    private Integer stoppingPower;
    @NotNull
    private String availability;
    @NotNull
    private Integer armorEnhancement;

    private String effect;
    @NotNull
    private Integer encumbranceValue;
    @NotNull
    private Double weight;
    @NotNull
    private Integer price;
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}
