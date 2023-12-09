package com.witcher.ttrpgapi.pojo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WearigItem {
    @NotNull
    private Integer characterId;
    @NotNull
    private  String bodyPart;
    @NotNull
    private Integer itemId;

}
