package dj.eventregister.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
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
