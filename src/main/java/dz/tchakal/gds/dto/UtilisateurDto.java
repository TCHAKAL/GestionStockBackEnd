package dz.tchakal.gds.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dz.tchakal.gds.model.*;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDto {

    private Integer id;

    private String nom;

    private String prenom;

    private String email;

    private Instant dateNaissance;

    private String motPasse;

    private Adresse adresse;

    private String photo;

    private EntrepriseDto entreprise;

    @JsonIgnore
    private List<Role> roles;


    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {
        if (utilisateur == null) {
            //TODO throw an exception
            return null;
        }
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .dateNaissance(utilisateur.getDateNaissance())
                .adresse(utilisateur.getAdresse())
                .email(utilisateur.getEmail())
                .motPasse(utilisateur.getMotPasse())
                .photo(utilisateur.getPhoto())
                .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
                .build();
    }
    public static Utilisateur toEntity(UtilisateurDto utilisateurDto) {
        if (utilisateurDto == null) {
            //TODO throw an exception
            return null;
        }
        return Utilisateur.builder()
                .id(utilisateurDto.getId())
                .nom(utilisateurDto.getNom())
                .prenom(utilisateurDto.getPrenom())
                .dateNaissance(utilisateurDto.getDateNaissance())
                .adresse(utilisateurDto.getAdresse())
                .email(utilisateurDto.getEmail())
                .motPasse(utilisateurDto.getMotPasse())
                .photo(utilisateurDto.getPhoto())
                .entreprise(EntrepriseDto.toEntity(utilisateurDto.getEntreprise()))
                .build();
    }

}
