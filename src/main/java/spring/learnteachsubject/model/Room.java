package spring.learnteachsubject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.learnteachsubject.model.enumeration.RoomType;

import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int roomNumber;

    private int floorNumber;

    private int maxCapacity;

    private Boolean inUse;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;


    @ManyToOne()
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @Column(name = "is_deleted",nullable = false)
    private boolean isDeleted = false;




    public Room(int roomNumber, int floorNumber, int maxCapacity, Boolean inUse, RoomType roomType, Institution institution) {
        this.roomNumber = roomNumber;
        this.floorNumber = floorNumber;
        this.maxCapacity = maxCapacity;
        this.inUse = inUse;
        this.roomType = roomType;
        this.institution = institution;
    }
}
