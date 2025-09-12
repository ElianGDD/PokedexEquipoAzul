package com.RisosuIT.Pokedex.Controlador;

import com.RisosuIT.Pokedex.DTO.PokemonVistaDto;
import com.RisosuIT.Pokedex.Servicio.ServicioPokemon;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/favoritos")
public class ControladorFavoritos {

    private final ServicioPokemon servicioPokemon;

    public ControladorFavoritos(ServicioPokemon servicioPokemon) {
        this.servicioPokemon = servicioPokemon;
    }

    // Vista Thymeleaf
    @GetMapping("/vista")
    public String verFavoritos(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        Set<Integer> favoritos = (Set<Integer>) session.getAttribute("favoritos");
        if (favoritos == null || favoritos.isEmpty()) {
            model.addAttribute("pokemons", List.of());
        } else {
            List<PokemonVistaDto> lista = favoritos.stream()
                    .map(servicioPokemon::obtenerDetalleVistaPorId)
                    .collect(Collectors.toList());
            model.addAttribute("pokemons", lista);
        }
        return "favoritos";
    }

    // API JSON
    @GetMapping(value = "/json")
    @ResponseBody
    public Set<Integer> obtenerFavoritos(HttpSession session) {
        Set<Integer> favs = (Set<Integer>) session.getAttribute("favoritos");
        return favs != null ? favs : Set.of();
    }

    @PostMapping("/add/{id}")
    @ResponseBody
    public ResponseEntity<Void> agregarFavorito(@PathVariable int id, HttpSession session) {
        Set<Integer> favs = (Set<Integer>) session.getAttribute("favoritos");
        if (favs == null) {
            favs = ConcurrentHashMap.newKeySet();
            session.setAttribute("favoritos", favs);
        }
        favs.add(id);
        return ResponseEntity.ok().build(); // 200 sin vista
    }

    @PostMapping("/remove/{id}")
    @ResponseBody
    public ResponseEntity<Void> quitarFavorito(@PathVariable int id, HttpSession session) {
        Set<Integer> favs = (Set<Integer>) session.getAttribute("favoritos");
        if (favs != null) {
            favs.remove(id);
        }
        return ResponseEntity.ok().build();
    }

}
