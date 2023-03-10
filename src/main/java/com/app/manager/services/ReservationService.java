package com.app.manager.services;

import com.app.manager.entity.Reserver;
import com.app.manager.entity.reservation.Client;
import com.app.manager.entity.reservation.Passager;
import com.app.manager.entity.reservation.Reservation;
import com.app.manager.entity.reservation.ReservationID;
import com.app.manager.entity.vole.Vole;
import com.app.manager.repository.ClientRepository;
import com.app.manager.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private  final ClientRepository clientRepository;
    private  final ReservationRepository reservationRepository;

    public ReservationService(ClientRepository clientRepository, ReservationRepository reservationRepository) {
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
    }


    public List<Client> clientList(){
        return  clientRepository.findAll();
    }

    //add new reservation
    public void newReservation(Reserver reserver){

       try {
           if
           (
                   clientRepository.findByEmail(reserver.getEmail()).isEmpty()
           )
           {
               Client client  = new Client(
                       reserver.getClient_first_name(),
                       reserver.getClient_last_name(),
                       reserver.getAddress(),
                       reserver.getTelephone(),
                       reserver.getEmail()
               );
               Passager passager = new Passager(
                       reserver.getPassager_first_name(),
                       reserver.getPassager_last_name()
               );

               Vole vole = new Vole(
                       reserver.getStart_vole(),
                       reserver.getEnd_vole()
               );

               client.addReservation(new Reservation(
                       new ReservationID(
                               client.getClientID(),
                               passager.getPassagerID(),
                               vole.getVoleID()
                       ),
                       LocalDateTime.now(),
                       client,
                       passager,
                       vole
               ));
               clientRepository.save(client);
           }
           else{
               throw new RuntimeException("existed client with thous information");
           }
       }
       catch (Exception e)
           {
             throw new RuntimeException(e.getMessage());
           }
    }

    public void updateReservation(Long id,Reserver reserver){
        try {
                Reservation reservation = reservationRepository.findReservationById_ClientID(id);
            if
            (
                    reservation != null
            )
            {
                //client
                Client client = reservation.getClient();
                client.setFirst_name(reserver.getClient_first_name());
                client.setLast_name(reserver.getClient_last_name());
                client.setAddress(reserver.getAddress());
                client.setTelephone(reserver.getTelephone());
                client.setEmail(reserver.getEmail());

                //passager
                Passager passager = reservation.getPassager();
                passager.setFirst_name(reserver.getPassager_first_name());
                passager.setLast_name(reserver.getPassager_last_name());

                //vole
                Vole vole = reservation.getVole();
                vole.setDate_departure(reserver.getStart_vole());
                vole.setDate_arrive(reserver.getEnd_vole());

                //resrevation ID
                ReservationID reservationID = new ReservationID(
                        client.getClientID(),
                        passager.getPassagerID(),
                        vole.getVoleID()
                );

                //reservation
                reservation.setId(reservationID);
                reservation.setClient(client);
                reservation.setVole(vole);
                reservation.setPassager(passager);

                //client save
                client.addReservation(reservation);
                clientRepository.save(client);
            }
            else{
                throw new RuntimeException("existed client with thous information");
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteReservation(Long id){
        try{
            Reservation reservation =  reservationRepository.findReservationById_ClientID(id);
            if(reservation != null){
                reservationRepository.delete(reservation);
            }
            else {
                throw new ResourceNotfound("reservation not found with this id");
            }
        }
        catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }

    public void deleteClient(Long id){
        Client client = clientRepository.findByClientID(id);
            try{
                if(clientRepository.findById(id).isPresent()){
                    clientRepository.delete(client);
                }
                else{
                    throw  new ResourceNotfound("note found client with this name ");
                }
            }
            catch (Exception e){
              throw new RuntimeException(e.getMessage());
            }

    }
}
