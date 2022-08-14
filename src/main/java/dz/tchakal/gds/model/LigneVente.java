package dz.tchakal.gds.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true )
@Table(name = "ligne_vente")
@Entity
public class LigneVente extends AbstractEntity{

    @Column(name = "id_vente")
    private Vente vente;

    @Column(name = "quantite")
    private BigDecimal quantite;
}
