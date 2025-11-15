package com.example.gestionresiduos.model

// Enum que representa el estado de una Orden de Servicio (OS)
enum class EstadoOS {
    PENDIENTE,      // Aún no iniciada
    EN_PROCESO,     // En ejecución (chofer la comenzó)
    COMPLETADA      // Finalizada correctamente
}