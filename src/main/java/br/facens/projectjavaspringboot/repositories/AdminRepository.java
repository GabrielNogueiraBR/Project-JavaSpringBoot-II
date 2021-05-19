package br.facens.projectjavaspringboot.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.facens.projectjavaspringboot.entities.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

    @Query("SELECT a FROM Admin a")
    public Page<Admin> findAdminPageable(PageRequest pageRequest);
    
}
