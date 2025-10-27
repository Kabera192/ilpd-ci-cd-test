package rw.ac.ilpd.mis.auth.entity.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 10/07/2025
 */
@Entity
@Table(name = "auth_head_of_units")
public class HeadOfUnitEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "unit_id", referencedColumnName = "unit_id")
    @JsonBackReference
    private UnitEntity unitEntity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonBackReference
    private UserEntity userEntity;

    private boolean is_current_head;

}
