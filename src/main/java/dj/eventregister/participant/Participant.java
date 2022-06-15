package dj.eventregister.participant;

import dj.eventregister.party.Party;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private int age;
    private String email;

    @OneToMany(mappedBy = "participant")
    private List<Party> parties = new ArrayList<>();



}
