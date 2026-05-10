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
exports.AdminController = void 0;
const common_1 = require("@nestjs/common");
const admin_service_1 = require("./admin.service");
const jwt_auth_guard_1 = require("../../common/guards/jwt-auth.guard");
const admin_guard_1 = require("../../common/guards/admin.guard");
let AdminController = class AdminController {
    constructor(service) {
        this.service = service;
    }
    dashboard() { return this.service.dashboard(); }
    listUsuarios(filters) { return this.service.listUsuarios(filters); }
    getUsuario(id) { return this.service.getUsuario(id); }
    updateUsuario(id, body) { return this.service.updateUsuario(id, body); }
    deleteUsuario(id) { return this.service.deleteUsuario(id); }
    listEmpresas(filters) { return this.service.listEmpresas(filters); }
    updateEmpresa(id, body) { return this.service.updateEmpresa(id, body); }
    listTrabajadores(filters) { return this.service.listTrabajadores(filters); }
    updateTrabajador(id, body) { return this.service.updateTrabajador(id, body); }
    listSuscripciones(filters) { return this.service.listSuscripciones(filters); }
    updateSuscripcion(id, body) { return this.service.updateSuscripcion(id, body); }
    listFacturas(filters) { return this.service.listFacturas(filters); }
    logs(desde, hasta) {
        return this.service.logs(desde, hasta);
    }
};
exports.AdminController = AdminController;
__decorate([
    (0, common_1.Get)('dashboard'),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", []),
    __metadata("design:returntype", void 0)
], AdminController.prototype, "dashboard", null);
__decorate([
    (0, common_1.Get)('usuarios'),
    __param(0, (0, common_1.Query)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], AdminController.prototype, "listUsuarios", null);
__decorate([
    (0, common_1.Get)('usuarios/:id'),
    __param(0, (0, common_1.Param)('id')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String]),
    __metadata("design:returntype", void 0)
], AdminController.prototype, "getUsuario", null);
__decorate([
    (0, common_1.Put)('usuarios/:id'),
    __param(0, (0, common_1.Param)('id')),
    __param(1, (0, common_1.Body)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, Object]),
    __metadata("design:returntype", void 0)
], AdminController.prototype, "updateUsuario", null);
__decorate([
    (0, common_1.Delete)('usuarios/:id'),
    __param(0, (0, common_1.Param)('id')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String]),
    __metadata("design:returntype", void 0)
], AdminController.prototype, "deleteUsuario", null);
__decorate([
    (0, common_1.Get)('empresas'),
    __param(0, (0, common_1.Query)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], AdminController.prototype, "listEmpresas", null);
__decorate([
    (0, common_1.Put)('empresas/:id'),
    __param(0, (0, common_1.Param)('id')),
    __param(1, (0, common_1.Body)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, Object]),
    __metadata("design:returntype", void 0)
], AdminController.prototype, "updateEmpresa", null);
__decorate([
    (0, common_1.Get)('trabajadores'),
    __param(0, (0, common_1.Query)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], AdminController.prototype, "listTrabajadores", null);
__decorate([
    (0, common_1.Put)('trabajadores/:id'),
    __param(0, (0, common_1.Param)('id')),
    __param(1, (0, common_1.Body)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, Object]),
    __metadata("design:returntype", void 0)
], AdminController.prototype, "updateTrabajador", null);
__decorate([
    (0, common_1.Get)('suscripciones'),
    __param(0, (0, common_1.Query)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], AdminController.prototype, "listSuscripciones", null);
__decorate([
    (0, common_1.Put)('suscripciones/:id'),
    __param(0, (0, common_1.Param)('id')),
    __param(1, (0, common_1.Body)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, Object]),
    __metadata("design:returntype", void 0)
], AdminController.prototype, "updateSuscripcion", null);
__decorate([
    (0, common_1.Get)('facturas'),
    __param(0, (0, common_1.Query)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], AdminController.prototype, "listFacturas", null);
__decorate([
    (0, common_1.Get)('logs'),
    __param(0, (0, common_1.Query)('desde')),
    __param(1, (0, common_1.Query)('hasta')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, String]),
    __metadata("design:returntype", void 0)
], AdminController.prototype, "logs", null);
exports.AdminController = AdminController = __decorate([
    (0, common_1.Controller)('admin'),
    (0, common_1.UseGuards)(jwt_auth_guard_1.JwtAuthGuard, admin_guard_1.AdminGuard),
    __metadata("design:paramtypes", [admin_service_1.AdminService])
], AdminController);
//# sourceMappingURL=admin.controller.js.map