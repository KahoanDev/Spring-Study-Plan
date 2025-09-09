package com.KahoanDev.libraryapi.service;

import com.KahoanDev.libraryapi.model.Client;
import com.KahoanDev.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client salvar(Client client){
        var senhaCprito = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCprito);

        return repository.save(client);
    }

    public Client obterPorClientID(String clientId){
        return repository.findByClientId(clientId);
    }
}
