package dj.eventregister.eventrecord.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EventRecordReadDto {

    private Long id;
    private Long eventId;
    private Long participantId;
}
