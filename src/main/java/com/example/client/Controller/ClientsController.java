package com.example.client.Controller;

import com.example.client.Bean.Clients;
import com.example.client.Service.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientsController {
    @Autowired
    private ClientsService clientsService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Clients> createClient(@RequestBody Clients client) {
        Clients savedClient = clientsService.saveClient(client);
        return ResponseEntity.ok(savedClient);
    }

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Clients> createClientFromForm(@ModelAttribute Clients client) {
        Clients savedClient = clientsService.saveClient(client);
        return ResponseEntity.ok(savedClient);
    }

    @GetMapping
    public ResponseEntity<List<Clients>> getAllClients() {
        List<Clients> clients = clientsService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clients> getClientById(@PathVariable Long id) {
        Clients client = clientsService.getClientById(id);
        return ResponseEntity.ok(client);
    }
    @GetMapping("/update-client/{id}")
    public String updateClientForm(@PathVariable Long id, Model model) {
        Clients client = clientsService.getClientById(id);
        if (client != null) {

            return "update-client";
        } else {

            return "error";
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Clients> updateClient(@PathVariable Long id, @RequestBody Clients clientDetails) {
        Clients client = clientsService.getClientById(id);
        if (client != null) {
            client.setCin(clientDetails.getCin());
            client.setNom(clientDetails.getNom());
            client.setPrenom(clientDetails.getPrenom());
            client.setEmail(clientDetails.getEmail());
            client.setNomT(clientDetails.getNomT());
            client.setDv(clientDetails.getDv());
            client.setStatue(clientDetails.isStatue());

            Clients updatedClient = clientsService.updateClient(client);
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            Clients client = clientsService.getClientById(id);
            if (client != null) {
                clientsService.deleteClient(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

