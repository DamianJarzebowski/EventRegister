package dj.eventregister.repository;

import dj.eventregister.models.eventrecord.EventRecord;
import dj.eventregister.models.participant.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRecordRepository extends JpaRepository<EventRecord, Long> {

    Long countByEventId(Long eventId);
}
