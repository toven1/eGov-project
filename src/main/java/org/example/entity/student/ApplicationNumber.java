package org.example.entity.student;

import javax.persistence.*;

@Entity
@Table(name = "application_number")
public class ApplicationNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int applicationNumber;

    public ApplicationNumber(int applicationNumber) {
        this.applicationNumber = applicationNumber;
    }
}
