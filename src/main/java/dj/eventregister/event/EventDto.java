package dj.eventregister.event;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventDto {

    private Long id;
    private String name;
    private String description;
    private int maxParticipant;
    private int minParticipant;
    private int currentParticipants;
    private boolean majority;
    private LocalDate date;
    private LocalTime time;
    private String category;

}
