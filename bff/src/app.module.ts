import { Module } from '@nestjs/common'
import { ConfigModule } from '@nestjs/config'
import { AuthModule } from './modules/auth/auth.module'
import { EmpresasModule } from './modules/empresas/empresas.module'
import { TrabajadoresModule } from './modules/trabajadores/trabajadores.module'
import { OfertasModule } from './modules/ofertas/ofertas.module'
import { PostulacionesModule } from './modules/postulaciones/postulaciones.module'
import { MatchingModule } from './modules/matching/matching.module'
import { AdminModule } from './modules/admin/admin.module'
import { FacturacionModule } from './modules/facturacion/facturacion.module'

@Module({
  imports: [
    ConfigModule.forRoot({ isGlobal: true }),
    AuthModule,
    EmpresasModule,
    TrabajadoresModule,
    OfertasModule,
    PostulacionesModule,
    MatchingModule,
    AdminModule,
    FacturacionModule,
  ],
})
export class AppModule {}
