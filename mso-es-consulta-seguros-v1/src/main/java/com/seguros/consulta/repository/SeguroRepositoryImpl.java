package com.seguros.consulta.repository;

import com.seguros.consulta.model.Seguro;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SeguroRepositoryImpl implements SeguroRepository{

    private final List<Seguro> seguros;

    public SeguroRepositoryImpl() {
        this.seguros = new ArrayList<>();

        cargarSeguros();
    }

    private void cargarSeguros() {
        Seguro vida = Seguro.builder()
                .id(1L)
                .nombre("VIDA")
                .descripcion("Protección económica para tu familia en caso de fallecimiento natural o accidental.")
                .beneficios(List.of(
                        "Beneficio económico para tu familia, que se incrementará en caso de fallecimiento accidental.",
                        "Servicio funerario sin costo adicional."
                ))
                .costoAnual(BigDecimal.valueOf(700))
                .edadMinima(18)
                .edadMaxima(75)
                .requiereMujer(false)
                .build();

        seguros.add(vida);

        Seguro infarto = Seguro.builder()
                .id(2L)
                .nombre("INFARTO")
                .descripcion("Protección económica de $50,000 por la primera ocurrencia de infarto al miocardio.")
                .beneficios(List.of(
                        "Envío de ambulancia.",
                        "Consultas médicas telefónicas ilimitadas.",
                        "No se requiere presentar exámenes médicos."
                ))
                .costoAnual(BigDecimal.valueOf(400))
                .edadMinima(15)
                .edadMaxima(64)
                .requiereMujer(false)
                .build();

        seguros.add(infarto);

        Seguro mujer = Seguro.builder()
                .id(3L)
                .nombre("MUJER")
                .descripcion("Protección económica de $50,000 por el primer diagnóstico de cáncer de mama o cervicouterino.")
                .beneficios(List.of(
                        "20 consultas psicológicas a domicilio (aplican solo si se diagnostica el cáncer).",
                        "Consultas psicológicas por teléfono ilimitadas.",
                        "No se requiere presentar exámenes médicos."
                ))
                .costoAnual(BigDecimal.valueOf(300))
                .edadMinima(15)
                .edadMaxima(64)
                .requiereMujer(true)
                .build();

        seguros.add(mujer);
    }

    @Override
    public List<Seguro> findAll() {
        return seguros;
    }

    @Override
    public Optional<Seguro> findById(Long id) {
        return seguros.stream()
                .filter(seguro -> seguro.getId().equals(id))
                .findFirst();
    }
}
