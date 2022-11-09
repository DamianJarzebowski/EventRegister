package dj.eventregister.models.participant;

import dj.eventregister.models.eventrecord.EventRecord;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private int age;

    private String email;

    @OneToMany(mappedBy = "participant")
    private List<EventRecord> parties = new ArrayList<>();


}
