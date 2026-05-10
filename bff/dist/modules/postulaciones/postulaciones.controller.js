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
exports.PostulacionesController = void 0;
const common_1 = require("@nestjs/common");
const postulaciones_service_1 = require("./postulaciones.service");
const jwt_auth_guard_1 = require("../../common/guards/jwt-auth.guard");
let PostulacionesController = class PostulacionesController {
    constructor(service) {
        this.service = service;
    }
    create(body) { return this.service.create(body); }
    list(trabajadorId, estado) {
        return this.service.list(trabajadorId, estado);
    }
    accept(id) { return this.service.accept(id); }
    reject(id, body) {
        return this.service.reject(id, body.motivo);
    }
};
exports.PostulacionesController = PostulacionesController;
__decorate([
    (0, common_1.Post)(),
    __param(0, (0, common_1.Body)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [Object]),
    __metadata("design:returntype", void 0)
], PostulacionesController.prototype, "create", null);
__decorate([
    (0, common_1.Get)(),
    __param(0, (0, common_1.Query)('trabajador_id')),
    __param(1, (0, common_1.Query)('estado')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, String]),
    __metadata("design:returntype", void 0)
], PostulacionesController.prototype, "list", null);
__decorate([
    (0, common_1.Patch)(':id/accept'),
    __param(0, (0, common_1.Param)('id')),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String]),
    __metadata("design:returntype", void 0)
], PostulacionesController.prototype, "accept", null);
__decorate([
    (0, common_1.Patch)(':id/reject'),
    __param(0, (0, common_1.Param)('id')),
    __param(1, (0, common_1.Body)()),
    __metadata("design:type", Function),
    __metadata("design:paramtypes", [String, Object]),
    __metadata("design:returntype", void 0)
], PostulacionesController.prototype, "reject", null);
exports.PostulacionesController = PostulacionesController = __decorate([
    (0, common_1.Controller)('postulaciones'),
    (0, common_1.UseGuards)(jwt_auth_guard_1.JwtAuthGuard),
    __metadata("design:paramtypes", [postulaciones_service_1.PostulacionesService])
], PostulacionesController);
//# sourceMappingURL=postulaciones.controller.js.map