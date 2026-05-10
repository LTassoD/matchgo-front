"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.AppModule = void 0;
const common_1 = require("@nestjs/common");
const config_1 = require("@nestjs/config");
const auth_module_1 = require("./modules/auth/auth.module");
const empresas_module_1 = require("./modules/empresas/empresas.module");
const trabajadores_module_1 = require("./modules/trabajadores/trabajadores.module");
const ofertas_module_1 = require("./modules/ofertas/ofertas.module");
const postulaciones_module_1 = require("./modules/postulaciones/postulaciones.module");
const matching_module_1 = require("./modules/matching/matching.module");
const admin_module_1 = require("./modules/admin/admin.module");
const facturacion_module_1 = require("./modules/facturacion/facturacion.module");
let AppModule = class AppModule {
};
exports.AppModule = AppModule;
exports.AppModule = AppModule = __decorate([
    (0, common_1.Module)({
        imports: [
            config_1.ConfigModule.forRoot({ isGlobal: true }),
            auth_module_1.AuthModule,
            empresas_module_1.EmpresasModule,
            trabajadores_module_1.TrabajadoresModule,
            ofertas_module_1.OfertasModule,
            postulaciones_module_1.PostulacionesModule,
            matching_module_1.MatchingModule,
            admin_module_1.AdminModule,
            facturacion_module_1.FacturacionModule,
        ],
    })
], AppModule);
//# sourceMappingURL=app.module.js.map