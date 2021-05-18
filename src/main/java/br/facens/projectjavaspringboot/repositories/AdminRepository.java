package br.facens.projectjavaspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.facens.projectjavaspringboot.entities.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    
}
