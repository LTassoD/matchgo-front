"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.OfertasService = void 0;
const common_1 = require("@nestjs/common");
const supabase_1 = require("../../common/supabase");
const PLAN_LIMITS = {
    BASICO: { publicaciones: 5 },
    PROFESIONAL: { publicaciones: 20 },
    ENTERPRISE: { publicaciones: -1 },
    TRIAL: { publicaciones: 20 },
};
let OfertasService = class OfertasService {
    async create(body) {
        const supabase = (0, supabase_1.getServerClient)();
        const { data: empresa } = await supabase.from('empresa').select('*').eq('id', body.empresa_id).single();
        if (!empresa)
            throw new common_1.NotFoundException('Empresa no encontrada');
        const limit = PLAN_LIMITS[empresa.plan]?.publicaciones ?? 5;
        if (limit !== -1 && empresa.publicaciones_usadas >= limit) {
            throw new common_1.ForbiddenException('Límite de publicaciones alcanzado');
        }
        const { data, error } = await supabase.from('oferta').insert({ ...body, estado: 'ABIERTA' }).select().single();
        if (error)
            throw new Error(error.message);
        await supabase.from('empresa').update({ publicaciones_usadas: empresa.publicaciones_usadas + 1 }).eq('id', body.empresa_id);
        return data;
    }
    async findOne(id) {
        const supabase = (0, supabase_1.getServerClient)();
        const { data, error } = await supabase.from('oferta')
            .select('*, empresa(id, razon_social, region), postulaciones(*)')
            .eq('id', id).single();
        if (error || !data)
            throw new common_1.NotFoundException('Oferta no encontrada');
        return data;
    }
    async list(filters) {
        const supabase = (0, supabase_1.getServerClient)();
        let query = supabase.from('oferta')
            .select('*, empresa(id, razon_social, region)', { count: 'exact' });
        if (filters.empresa_id)
            query = query.eq('empresa_id', filters.empresa_id);
        if (filters.region)
            query = query.eq('region', filters.region);
        if (filters.categoria)
            query = query.eq('categoria', filters.categoria);
        if (filters.estado)
            query = query.eq('estado', filters.estado);
        const page = parseInt(filters.page || '1');
        const limit = parseInt(filters.limit || '10');
        query = query.order('created_at', { ascending: false })
            .range((page - 1) * limit, page * limit - 1);
        const { data, count } = await query;
        return { data, total: count || 0, page, limit, totalPages: Math.ceil((count || 0) / limit) };
    }
    async update(id, body) {
        const supabase = (0, supabase_1.getServerClient)();
        const { data, error } = await supabase.from('oferta').update(body).eq('id', id).select().single();
        if (error)
            throw new Error(error.message);
        if (!data)
            throw new common_1.NotFoundException('Oferta no encontrada');
        return data;
    }
    async remove(id) {
        const supabase = (0, supabase_1.getServerClient)();
        await supabase.from('postulacion').delete().eq('oferta_id', id);
        const { error } = await supabase.from('oferta').delete().eq('id', id);
        if (error)
            throw new Error(error.message);
        return { message: 'Oferta eliminada' };
    }
};
exports.OfertasService = OfertasService;
exports.OfertasService = OfertasService = __decorate([
    (0, common_1.Injectable)()
], OfertasService);
//# sourceMappingURL=ofertas.service.js.map