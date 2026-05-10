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
exports.TrabajadoresController = void 0;
const common_1 = require("@nestjs/common");
const trabajadores_service_1 = require("./trabajadores.service");
const jwt_auth_guard_1 = require("../../common/guards/jwt-auth.guard");
let TrabajadoresController = class TrabajadoresController {
    constructor(service) {
        this.service = service;
    }
    create(body) { return this.service.create(body); }
    findOne(id, usuarioId) {
        return this.service.findOne(id, usuarioId);
    }
    update(id, body) { return this.service.update(id, body); }
    search(empresaId, filters) {
        return this.service.search(empresaId, filters);
    }
};
exports.TrabajadoresController = TrabajadoresController;
__decorate([
    (0, common_1.Post)(),
    __param(0, (0, common_1.Body)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], TrabajadoresController.prototype, "create", null);
__decorate([
    (0, common_1.Get)(),
    __param(0, (0, common_1.Query)('id')),
    __param(1, (0, common_1.Query)('usuario_id')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, String]),
    __metadata("design:returntype", void 0)
], TrabajadoresController.prototype, "findOne", null);
__decorate([
    (0, common_1.Put)(),
    __param(0, (0, common_1.Query)('id')),
    __param(1, (0, common_1.Body)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, Object]),
    __metadata("design:returntype", void 0)
], TrabajadoresController.prototype, "update", null);
__decorate([
    (0, common_1.Get)('search'),
    __param(0, (0, common_1.Query)('empresa_id')),
    __param(1, (0, common_1.Query)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, Object]),
    __metadata("design:returntype", void 0)
], TrabajadoresController.prototype, "search", null);
exports.TrabajadoresController = TrabajadoresController = __decorate([
    (0, common_1.Controller)('trabajadores'),
    (0, common_1.UseGuards)(jwt_auth_guard_1.JwtAuthGuard),
    __metadata("design:paramtypes", [trabajadores_service_1.TrabajadoresService])
], TrabajadoresController);
//# sourceMappingURL=trabajadores.controller.js.map