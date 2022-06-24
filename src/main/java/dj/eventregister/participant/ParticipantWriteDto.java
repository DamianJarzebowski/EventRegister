package dj.eventregister.participant;

import lombok.Data;

@Data
public class ParticipantWriteDto {

    private String name;
    private String lastName;
    private int age;
    private String email;
}
