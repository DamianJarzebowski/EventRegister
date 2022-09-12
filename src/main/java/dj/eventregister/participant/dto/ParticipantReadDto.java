package dj.eventregister.participant.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ParticipantReadDto {

    private Long id;
    private String name;
    private String lastName;
    private int age;
    private String email;
}
