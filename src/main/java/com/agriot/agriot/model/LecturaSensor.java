package com.agriot.agroiot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lecturas_iot")
public class LecturaSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modulo;
    private Double temperatura;

    @Column(name = "humedad_aire")
    @JsonProperty("humedad_aire")
    private Double humedadAire;

    @Column(name = "humedad_tierra")
    @JsonProperty("humedad_tierra")
    private Integer humedadTierra;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModulo() { return modulo; }
    public void setModulo(String modulo) { this.modulo = modulo; }

    public Double getTemperatura() { return temperatura; }
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }

    public Double getHumedadAire() { return humedadAire; }
    public void setHumedadAire(Double humedadAire) { this.humedadAire = humedadAire; }

    public Integer getHumedadTierra() { return humedadTierra; }
    public void setHumedadTierra(Integer humedadTierra) { this.humedadTierra = humedadTierra; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}