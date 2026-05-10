"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.TrabajadoresService = void 0;
const common_1 = require("@nestjs/common");
const supabase_1 = require("../../common/supabase");
const PLAN_LIMITS = {
    BASICO: { busquedas: 10 },
    PROFESIONAL: { busquedas: 50 },
    ENTERPRISE: { busquedas: -1 },
    TRIAL: { busquedas: 50 },
};
let TrabajadoresService = class TrabajadoresService {
    async create(body) {
        const supabase = (0, supabase_1.getServerClient)();
        const { data, error } = await supabase.from('trabajador').insert({
            movilizacion_propia: false,
            disponibilidad: { dias: [], horarios: [] },
            pretension_renta: { min: 0, max: 0, tipo: 'jornada' },
            experiencia: [],
            certificaciones: [],
            ...body,
        }).select().single();
        if (error?.code === '23505')
            throw new common_1.ConflictException('El RUT ya existe');
        if (error)
            throw new Error(error.message);
        return data;
    }
    async findOne(id, usuarioId) {
        const supabase = (0, supabase_1.getServerClient)();
        let query = supabase.from('trabajador').select('*');
        if (id)
            query = query.eq('id', id);
        else if (usuarioId)
            query = query.eq('usuario_id', usuarioId);
        const { data, error } = await query.single();
        if (error || !data)
            throw new common_1.NotFoundException('Trabajador no encontrado');
        return data;
    }
    async update(id, body) {
        const supabase = (0, supabase_1.getServerClient)();
        const { data, error } = await supabase.from('trabajador').update(body).eq('id', id).select().single();
        if (error)
            throw new Error(error.message);
        if (!data)
            throw new common_1.NotFoundException('Trabajador no encontrado');
        return data;
    }
    async search(empresaId, filters) {
        const supabase = (0, supabase_1.getServerClient)();
        const { data: empresa } = await supabase.from('empresa').select('*').eq('id', empresaId).single();
        if (!empresa)
            throw new common_1.NotFoundException('Empresa no encontrada');
        const limit = PLAN_LIMITS[empresa.plan]?.busquedas ?? 10;
        if (limit !== -1 && empresa.busquedas_usadas >= limit) {
            throw new common_1.ForbiddenException('Límite de búsquedas alcanzado');
        }
        let query = supabase.from('trabajador').select('*', { count: 'exact' });
        if (filters.region)
            query = query.eq('region', filters.region);
        if (filters.comuna)
            query = query.eq('comuna', filters.comuna);
        if (filters.movilizacion_propia)
            query = query.eq('movilizacion_propia', true);
        const page = filters.page || 1;
        const pageLimit = filters.limit || 10;
        query = query.range((page - 1) * pageLimit, page * pageLimit - 1);
        const { data, count } = await query;
        await supabase.from('empresa').update({ busquedas_usadas: empresa.busquedas_usadas + 1 }).eq('id', empresaId);
        return { data, total: count || 0, page, limit: pageLimit };
    }
};
exports.TrabajadoresService = TrabajadoresService;
exports.TrabajadoresService = TrabajadoresService = __decorate([
    (0, common_1.Injectable)()
], TrabajadoresService);
//# sourceMappingURL=trabajadores.service.js.map