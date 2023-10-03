package com.witcher.ttrpgapi.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Weapon {


    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String category;
    @NotNull
    private String type;
    @NotNull
    private Integer weaponAccuracy;
    @NotNull
    private String availability;
    @NotNull
    private String damage;
    @NotNull
    private Integer reliability;
    @NotNull
    private Integer handsRequired;
    @NotNull
    private String range;
    private String effect;
    @NotNull
    private String concealment;
    @NotNull
    private Integer enhancements;
    @NotNull
    private Double weight;
    @NotNull
    private Integer cost;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
