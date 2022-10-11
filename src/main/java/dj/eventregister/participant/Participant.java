package dj.eventregister.participant;

import dj.eventregister.eventrecord.EventRecord;
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
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private int age;
    private String email;

    @OneToMany(mappedBy = "participant")
    private List<EventRecord> parties = new ArrayList<>();



}
