"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.FacturacionController = void 0;
const common_1 = require("@nestjs/common");
const facturacion_service_1 = require("./facturacion.service");
const jwt_auth_guard_1 = require("../../common/guards/jwt-auth.guard");
let FacturacionController = class FacturacionController {
    constructor(service) {
        this.service = service;
    }
    getPlanes() { return this.service.getPlanes(); }
    crearFactura(body) {
        return this.service.crearFactura(body.empresa_id, body.plan_id);
    }
    cobrarFactura(id) {
        return this.service.cobrarFactura(id);
    }
    listarFacturas(empresa_id, page, limit) {
        return this.service.listarFacturas(empresa_id, parseInt(page || '1'), parseInt(limit || '20'));
    }
    webhookPago(body) {
        return this.service.webhookPago(body);
    }
    reporteFacturacion(desde, hasta) {
        return this.service.reporteFacturacion(desde, hasta);
    }
};
exports.FacturacionController = FacturacionController;
__decorate([
    (0, common_1.Get)('planes'),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", []),
    __metadata("design:returntype", void 0)
], FacturacionController.prototype, "getPlanes", null);
__decorate([
    (0, common_1.Post)('facturas'),
    (0, common_1.UseGuards)(jwt_auth_guard_1.JwtAuthGuard),
    __param(0, (0, common_1.Body)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], FacturacionController.prototype, "crearFactura", null);
__decorate([
    (0, common_1.Post)('facturas/:id/cobrar'),
    (0, common_1.UseGuards)(jwt_auth_guard_1.JwtAuthGuard),
    __param(0, (0, common_1.Param)('id')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String]),
    __metadata("design:returntype", void 0)
], FacturacionController.prototype, "cobrarFactura", null);
__decorate([
    (0, common_1.Get)('facturas'),
    (0, common_1.UseGuards)(jwt_auth_guard_1.JwtAuthGuard),
    __param(0, (0, common_1.Query)('empresa_id')),
    __param(1, (0, common_1.Query)('page')),
    __param(2, (0, common_1.Query)('limit')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, String, String]),
    __metadata("design:returntype", void 0)
], FacturacionController.prototype, "listarFacturas", null);
__decorate([
    (0, common_1.Post)('webhook'),
    __param(0, (0, common_1.Body)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], FacturacionController.prototype, "webhookPago", null);
__decorate([
    (0, common_1.Get)('reportes'),
    __param(0, (0, common_1.Query)('desde')),
    __param(1, (0, common_1.Query)('hasta')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, String]),
    __metadata("design:returntype", void 0)
], FacturacionController.prototype, "reporteFacturacion", null);
exports.FacturacionController = FacturacionController = __decorate([
    (0, common_1.Controller)('facturacion'),
    __metadata("design:paramtypes", [facturacion_service_1.FacturacionService])
], FacturacionController);
//# sourceMappingURL=facturacion.controller.js.map