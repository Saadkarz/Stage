package com.example.client.Service;

import com.example.client.Bean.Clients;
import com.example.client.Repository.ClientsRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class ClientsServiceImpl implements ClientsService {

    @Autowired
    private ClientsRepository clientsRepository;

    @Override
    public Clients saveClient(Clients client) {
        return clientsRepository.save(client);
    }

    @Override
    public List<Clients>    getAllClients() {
        return clientsRepository.findAll();
    }

    @Override
    public Clients getClientById(Long id) {
        Optional<Clients> optionalClient = clientsRepository.findById(id);
        return optionalClient.orElse(null);
    }

    @Override
    public Clients updateClient(Clients client) {
        return clientsRepository.save(client);
    }

    @Override
    public void deleteClient(Long id) {
        clientsRepository.deleteById(id);
    }
}
