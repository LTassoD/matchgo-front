"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.EmpresasService = void 0;
const common_1 = require("@nestjs/common");
const supabase_1 = require("../../common/supabase");
let EmpresasService = class EmpresasService {
    async create(body) {
        const supabase = (0, supabase_1.getServerClient)();
        const { data, error } = await supabase.from('empresa').insert({
            ...body,
            plan: 'TRIAL',
            fecha_trial_fin: new Date(Date.now() + 30 * 86400000).toISOString(),
        }).select().single();
        if (error?.code === '23505')
            throw new common_1.ConflictException('El RUT ya existe');
        if (error)
            throw new Error(error.message);
        return data;
    }
    async findOne(id, usuarioId) {
        const supabase = (0, supabase_1.getServerClient)();
        let query = supabase.from('empresa').select('*');
        if (id)
            query = query.eq('id', id);
        else if (usuarioId)
            query = query.eq('usuario_id', usuarioId);
        const { data, error } = await query.single();
        if (error || !data)
            throw new common_1.NotFoundException('Empresa no encontrada');
        return data;
    }
    async update(id, body) {
        const supabase = (0, supabase_1.getServerClient)();
        const { data, error } = await supabase.from('empresa').update(body).eq('id', id).select().single();
        if (error)
            throw new Error(error.message);
        if (!data)
            throw new common_1.NotFoundException('Empresa no encontrada');
        return data;
    }
};
exports.EmpresasService = EmpresasService;
exports.EmpresasService = EmpresasService = __decorate([
    (0, common_1.Injectable)()
], EmpresasService);
//# sourceMappingURL=empresas.service.js.map