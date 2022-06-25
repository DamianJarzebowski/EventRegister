package dj.eventregister.eventrecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRecordRepository extends JpaRepository<EventRecord, Long> {}
