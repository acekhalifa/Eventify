package com.eventify.service;

import com.eventify.entity.Organiser;
import com.eventify.repository.OrganiserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrganiserService implements UserDetailsService {
    private OrganiserRepository organiserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return organiserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public Organiser getOrganiserByEmail(String email) {
        return organiserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public boolean existsByEmail(String email) {
        return organiserRepository.existsByEmail(email);
    }

    public Organiser createOrganiser(Organiser organiser) {
        return organiserRepository.save(organiser);
    }
}
