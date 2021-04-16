package br.com.bandtec.projetolutador;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LutadorRepository extends JpaRepository<Lutador,Integer> {

    List<Lutador> findByVivoFalse();
    List<Lutador> findAll();
    List<Lutador> countByVivoTrue();
}
