package com.syndicg5.service.impl;

import com.syndicg5.model.Revenu;
import com.syndicg5.model.Syndic;
import com.syndicg5.repository.SyndicRepository;
import com.syndicg5.service.SyndicService;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SyndicServiceImpl implements SyndicService, UserDetailsService {

    @Autowired
    SyndicRepository syndicRepository;
    @Lazy
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void save(Syndic syndic) {
        Syndic s = new Syndic();
        s.setNom(syndic.getNom());
        s.setPrenom(syndic.getPrenom());
        s.setEmail(syndic.getEmail());
        s.setTelephone(syndic.getTelephone());
        s.setVille(syndic.getVille());
        s.setPhoto(syndic.getPhoto());
        s.setImmeubles(syndic.getImmeubles());
        s.setMdp(passwordEncoder.encode(syndic.getMdp()));
        syndicRepository.save(s);
    }

    @Override
    public void update(long id, Syndic syndic) {
        Syndic s = syndicRepository.getById(id);
        s.setNom(syndic.getNom());
        s.setPrenom(syndic.getPrenom());
        s.setEmail(syndic.getEmail());
        s.setMdp(syndic.getMdp());
        s.setTelephone(syndic.getTelephone());
        s.setVille(syndic.getVille());
        s.setPhoto(syndic.getPhoto());
        s.setImmeubles(syndic.getImmeubles());
        syndicRepository.save(s);
    }

    @Override
    public List<Syndic> findAll() {
        return syndicRepository.findAll();
    }

    @Override
    public Syndic findOne(Long id) {
        return syndicRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        syndicRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Objects.requireNonNull(email);
        Syndic syndic = syndicRepository.findByEmail(email);
        if (syndic == null) {
            throw new UsernameNotFoundException("Syndic Not found!");
        }

        return syndic;
    }
}

