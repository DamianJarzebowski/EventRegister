package dj.eventregister.participant;

import lombok.Data;

@Data
public class ParticipantReadDto {

    private Long id;
    private String name;
    private String lastName;
    private int age;
    private String email;
}
