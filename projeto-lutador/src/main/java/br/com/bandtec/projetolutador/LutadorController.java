package br.com.bandtec.projetolutador;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lutador")
public class LutadorController
{

    @Autowired
    private LutadorRepository repository;


    //@GetMapping()
    //public ResponseEntity getLutadorVivo()
    {
        //return ResponseEntity.ok().body(repository.groupByNome());
    }

    @GetMapping()
    public ResponseEntity getLutador()
    {
        return ResponseEntity.ok().body(repository.findAll());
    }


    @GetMapping("/mortos")
    public ResponseEntity getLutadorMortos()
    {
        return ResponseEntity.ok().body(repository.findByVivoFalse());
    }

    @PostMapping("/cadastrar/{id}")
    public ResponseEntity postLutador(@RequestBody Lutador novoLutador,@PathVariable Integer id)
    {
        if(id.equals(novoLutador.getId())) {
            if (novoLutador.getNome().length() > 3 && novoLutador.getNome().length() < 12) {
                if (novoLutador.getForcaGolpe() > 0) {
                    novoLutador.setVida(100.00);
                    novoLutador.setConcentracoesRealizadas(0);
                    novoLutador.setVivo(true);
                    repository.save(novoLutador);

                } else {
                    return ResponseEntity.status(400).body("Golpe deve ser maior que 0");
                }


            } else {
                return ResponseEntity.status(400).body("Nome do Lutador deve ser entre 3 a 12 Caracteres");
            }
            return ResponseEntity.ok().build();
        }
        else
        {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/contagem-vivos")
    public ResponseEntity getLutadoresVivos()
    {
        return ResponseEntity.ok().body(repository.countByVivoTrue().size());
    }


    @PostMapping("/concentracao")
    public ResponseEntity postConcentracao(@RequestBody Lutador novaConcentracao)
    {
        Integer concentracao = novaConcentracao.getConcentracoesRealizadas();
        Double vida = novaConcentracao.getVida();
        Double novaVida = vida * 1.15;
        if(concentracao >= 3)
        {
            return ResponseEntity.status(400).body("Lutador já fez o maximo de concentrações");
        }
        else
        {
            concentracao++;

            novaConcentracao.setConcentracoesRealizadas(concentracao);
            novaConcentracao.setVida(novaVida);

        }

        return ResponseEntity.ok().build();
    }



}
