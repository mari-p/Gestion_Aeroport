/*
* This code has been generated by the Rebel: a code generator for modern Java.
*
* Drop us a line or two at feedback@archetypesoftware.com: we would love to hear from you!
*/
package com.app.manager.entity.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Client")
@ToString
@Table(
        name = "client",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "client_email_unique",
                        columnNames = "client_email"
                )
        }
)
public class Client {

    /*
     * ID client
     * Column name is : client_id
     * */

    @Id
    @SequenceGenerator(
            name = "client_sequence",
            sequenceName = "client_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "client_sequence"
    )
    @Column(
            name = "client_id"
            ,nullable = false
    )
    private Long clientID;


    /*
    *  Client first name
    *  Column name : client_first_name
    * */

    @Column(
            name = "client_first_name"
            ,nullable = false
    )
    private String first_name;

    /*
     *  Client last name
     *  Column name : client_last_name
     * */

    @Column(
            name = "client_last_name"
            ,nullable = false
    )
    private String last_name;

    /*
     *  Client  Address
     *  Column name : client_address
     * */

    @Column(
            name = "client_adresse"
            ,nullable = false
    )
    private String address;

    /*
    * Client Telephone
    *  Column name client_telephone
    * */

    @Column(
            name = "client_telephone"
            ,nullable = false
    )
    private String telephone;

    /*
    * Client Email
    * Column name client_email
    * */

    @Column(
            name = "client_email"
            ,nullable = false
    )
    private String email;

    /*
    * Mapping RelationShip between Tables
    * */

    @OneToMany(
            mappedBy = "client",
            cascade = {CascadeType.ALL},
            fetch =  FetchType.LAZY
    )
    @JsonIgnore
    private Collection<Reservation> reservations = new ArrayList<>();

    public Client(
            String first_name,
            String last_name,
            String address,
            String telephone,
            String email
    ) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
    }

    public void addReservation(Reservation reservation){
        if(!reservations.contains(reservation)){
            reservations.add(reservation);
            reservation.setClient(this);
        }
    }

    public void  removeReservation(Reservation reservation){
        reservations.remove(reservation);
    }

}
