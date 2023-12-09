package com.witcher.ttrpgapi.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Campaign {


    private int id;
    private int userId;
    @NotNull
    private String title;
    private String description;
    private String invitationLink;
    private int characterId;
}
