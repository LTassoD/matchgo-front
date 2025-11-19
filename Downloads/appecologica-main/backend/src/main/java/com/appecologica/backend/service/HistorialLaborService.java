package com.appecologica.backend.service;

import com.appecologica.backend.model.HistorialLabor;
import com.appecologica.backend.repository.HistorialLaborRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialLaborService {

    private final HistorialLaborRepository historialLaborRepository;

    public HistorialLaborService(HistorialLaborRepository historialLaborRepository) {
        this.historialLaborRepository = historialLaborRepository;
    }

    public List<HistorialLabor> historialPorChofer(Long choferId) {
        return historialLaborRepository.findByChoferId(choferId);
    }
}
