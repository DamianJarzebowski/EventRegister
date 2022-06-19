package dj.eventregister.eventrecord;

import lombok.Data;

@Data
public class EventRecordDto {

    private Long id;
    private Long eventId;
    private Long participantId;
}
