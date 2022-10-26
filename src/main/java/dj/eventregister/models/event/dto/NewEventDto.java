package dj.eventregister.models.event.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Accessors(chain = true)
@Data
public class NewEventDto {

    private String code;

    private String displayName;
    private String description;
    private int maxParticipant;
    private int minParticipant;
    private boolean maturity;
    private LocalDateTime whenStart;
    private String category;
    private List<Participant> participants;

    @Data
    public static class Participant {
        private String name;
        private String lastName;
        private int age;
        private String email;

    }
}
