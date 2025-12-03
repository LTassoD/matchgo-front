package com.example.appecologica.web.dto;

import com.example.appecologica.domain.Empleado;
import lombok.Data;

import java.util.List;

public class RankingDtos {
    @Data
    public static class RankingEmpleado {
        private EmpleadoDto empleado;
        private Float cumplimiento;
    }

    @Data
    public static class RankingResponse {
        private List<RankingEmpleado> ranking;
    }

    public static RankingEmpleado fromEmpleado(Empleado empleado, float cumplimiento) {
        RankingEmpleado dto = new RankingEmpleado();
        dto.setEmpleado(EmpleadoDto.fromEntity(empleado));
        dto.setCumplimiento(cumplimiento);
        return dto;
    }
}
