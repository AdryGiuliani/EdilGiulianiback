package com.piattaforme.edilgiulianiback.services;

import org.springframework.stereotype.Service;

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
