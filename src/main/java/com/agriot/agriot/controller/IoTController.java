package com.agriot.agroiot.controller;

import com.agriot.agroiot.model.LecturaSensor;
import com.agriot.agroiot.repository.LecturaRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensores")
@CrossOrigin(origins = "*")
public class IoTController {

    @Autowired
    private LecturaRepository lecturaRepository;

    @PostMapping
    public ResponseEntity<String> recibirDatos(@RequestBody LecturaSensor lectura) {
        try {
            // 1. Guarda en Oracle DB (Funciona perfecto)
            lecturaRepository.save(lectura);

            // 2. Guarda en Firebase y atrapa errores de inmediato
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("lecturas_iot");
            DatabaseReference nuevaLectura = ref.push();

            // Corrección: Usar "nuevaLectura" con L mayúscula
            nuevaLectura.setValue(lectura, (databaseError, databaseReference) -> {
                if (databaseError != null) {
                    System.err.println("❌ ERROR AL GUARDAR EN FIREBASE: " + databaseError.getMessage());
                } else {
                    System.out.println("✅ ¡Dato guardado con éxito en Firebase en la ruta: " + databaseReference.getPath() + "!");
                }
            });

            return ResponseEntity.ok("Dato registrado correctamente en Oracle y Firebase");
        } catch (Exception e) {
            System.err.println("❌ Excepción general en el POST: " + e.getMessage());
            return ResponseEntity.status(500).body("Error al procesar la lectura: " + e.getMessage());
        }
    }

    @GetMapping
    public List<LecturaSensor> obtenerTodas() {
        return lecturaRepository.findAll();
    }

    @GetMapping("/recientes")
    public List<LecturaSensor> obtenerRecientes() {
        return lecturaRepository.findTop20ByOrderByFechaRegistroDesc();
    }

    @GetMapping("/ultimo/{modulo}")
    public ResponseEntity<LecturaSensor> obtenerUltimoPorModulo(@PathVariable String modulo) {
        return lecturaRepository.findFirstByModuloOrderByFechaRegistroDesc(modulo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}