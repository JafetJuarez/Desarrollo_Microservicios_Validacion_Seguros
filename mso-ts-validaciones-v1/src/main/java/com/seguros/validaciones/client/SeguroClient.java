package com.seguros.validaciones.client;

import com.seguros.validaciones.dto.SeguroResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "seguroClient", url = "${seguros.consulta.url:http://localhost:8081}")
public interface SeguroClient {

    @GetMapping("/api/seguros/{id}")
    SeguroResponse obtenerSeguro(@PathVariable Long id);

}
