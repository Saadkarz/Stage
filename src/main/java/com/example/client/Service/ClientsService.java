package com.example.client.Service;
import com.example.client.Bean.Clients;
import java.util.List;

public interface ClientsService {
    Clients saveClient(Clients client);
    List<Clients> getAllClients();
    Clients getClientById(Long id);
    Clients updateClient(Clients client);
    void deleteClient(Long id);
}
