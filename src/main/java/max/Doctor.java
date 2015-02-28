package max;

import javax.persistence.*;
/**
 * Created by massimo on 2/28/15.
 */

@Entity
@Table(name="DOCTORS")
public class Doctor {

    @Id
    @Column(name="DOCTOR_ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int doctor_id;

    @Column(name="DOCTOR_NAME")
    private String doctor_name;

    @Column(name="DOCTOR_LASTNAME")
    private String doctor_lastname;

    public Doctor() {
        super();
    }

    public Doctor(int doctor_id, String doctor_name, String doctor_lastname) {
        super();
        this.doctor_id = doctor_id;
        this.doctor_name = doctor_name;
        this.doctor_lastname = doctor_lastname;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_lastname() {
        return doctor_lastname;
    }

    public void setDoctor_lastname(String doctor_lastname) {
        this.doctor_lastname = doctor_lastname;
    }

}