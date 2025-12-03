// En: repository/remote/mapper/RankingMapper.kt
package com.example.gestionresiduos.repository.remote.mapper

import com.example.gestionresiduos.model.Empleado
import com.example.gestionresiduos.repository.remote.dto.RankingEmpleadoDTO

fun RankingEmpleadoDTO.toDomain(): Pair<Empleado, Float> {
    return Pair(
        first = this.empleado.toDomain(),
        second = this.cumplimiento
    )
}
