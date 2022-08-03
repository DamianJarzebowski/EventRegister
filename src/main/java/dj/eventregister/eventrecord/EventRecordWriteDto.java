package dj.eventregister.eventrecord;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EventRecordWriteDto {

    private Long eventId;
    private Long participantId;
}
