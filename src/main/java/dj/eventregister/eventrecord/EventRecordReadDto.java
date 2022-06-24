package dj.eventregister.eventrecord;

import lombok.Data;

@Data
public class EventRecordReadDto {

    private Long id;
    private Long eventId;
    private Long participantId;
}
