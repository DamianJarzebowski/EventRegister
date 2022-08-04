package dj.eventregister.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
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
