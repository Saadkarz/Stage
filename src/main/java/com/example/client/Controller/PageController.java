package com.example.client.Controller;

import com.example.client.Bean.Clients;
import com.example.client.Repository.ClientsRepository;
import com.example.client.Service.ClientsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PageController {

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private ClientsRepository ClientsRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(PageController.class);
    @GetMapping("/dash")
    public String showDashboard(Model model) {
        long clientCount = ClientsRepository.count();
        model.addAttribute("clientCount", clientCount);

        // Example data, replace with actual client data
        List<Integer> clientData = Arrays.asList(10, 20, 30, 40, 50, 60, 70);
        model.addAttribute("clientData", clientData);

        return "dash";
    }

    @GetMapping("/add-client")
    public String addClientForm(Model model) {
        model.addAttribute("client", new Clients());
        return "add-client";
    }

    @GetMapping("/update-client/{id}")
    public String updateClientForm(Model model, @PathVariable Long id) {
        Clients client = clientsService.getClientById(id);
        if (client != null) {
            model.addAttribute("client", client);
            return "update-client"; // Make sure this matches the actual HTML file name
        } else {
            return "error";
        }
    }



    @GetMapping("/calendar")
    public String calendar() {
        return "calendar";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/map")
    public String map() {
        return "map";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Add more mappings as needed

    @GetMapping("/send-email")
    public String showSendEmailForm(Model model) {
        List<Clients> clients = clientsService.getAllClients();
        List<String> emails = clients.stream().map(Clients::getEmail).collect(Collectors.toList());
        model.addAttribute("emails", emails);
        return "send-email";
    }

    @PostMapping("/send-email")
    public String sendEmail(@RequestParam("subject") String subject, @RequestParam("body") String body, @RequestParam("emails") List<String> emails, Model model) {
        for (String email : emails) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject(subject);
                message.setText(body);
                mailSender.send(message);
            } catch (Exception e) {
                logger.error("Failed to send email to {}", email, e);
                model.addAttribute("error", "Failed to send email to " + email + ": " + e.getMessage());
                return "send-email"; // Display the send-email form again with the error message
            }
        }
        return "redirect:/send-email?success";
    }


    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        // Check if username and password match the predefined values
        if ("admin".equals(username) && "admin".equals(password)) {
            return "redirect:/dash"; // Redirect to dash.html if credentials are correct
        } else {
            return "redirect:/login"; // Redirect back to login page if credentials are incorrect
        }
    }



}
