package dj.eventregister.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventWriteDto {

    private String name;
    private String description;
    private int maxParticipant;
    private int minParticipant;
    private boolean majority;
    private LocalDateTime dateTime;
    private String category;

}
