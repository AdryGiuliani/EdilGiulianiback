package com.piattaforme.edilgiulianiback.services;

import com.piattaforme.edilgiulianiback.entities.Cliente;
import com.piattaforme.edilgiulianiback.utils.exceptions.MailAlreadyExistsException;
import com.piattaforme.edilgiulianiback.utils.utility.Utils;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {

//   // @Autowired
//    // private RepoClienti repoClienti;
//
//    @Transactional(propagation = Propagation.REQUIRED)
//    public Cliente registraCliente(@NotNull Cliente user) throws MailAlreadyExistsException {
//        if ( repoClienti.existsByEmail(user.getEmail())) {
//            throw new MailAlreadyExistsException("Email già presente nel sistema");
//        }
//        return repoClienti.save(user);
//    }

//    @Transactional(readOnly = true)
//    public List<Cliente> getAllUsers() {
//        return repoClienti.findAll();
//    }

    public String test(int n, String id){
        return "test ok"+n+"\n"+id;
    }
}
