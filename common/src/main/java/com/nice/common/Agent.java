package com.nice.common;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "userID")
//@CheckBools
public class Agent {

    @Min(0)
    public int userID;
    @NotNull(message = "Name cannot be null")
    public String name;
    public boolean isVoiceRecorded;
    public boolean isScreenRecorded;
    public boolean isOnCall;
//    @Setter
//    public LocalDateTime startEventDateTime;
}
