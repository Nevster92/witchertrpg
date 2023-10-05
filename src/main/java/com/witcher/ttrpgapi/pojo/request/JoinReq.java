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
public class JoinReq {

    @NotNull
    private int characterId;
    @NotNull
    private String invitationLink;
}
