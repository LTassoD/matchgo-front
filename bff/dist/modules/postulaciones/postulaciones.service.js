"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.PostulacionesService = void 0;
const common_1 = require("@nestjs/common");
const supabase_1 = require("../../common/supabase");
let PostulacionesService = class PostulacionesService {
    async create(body) {
        const supabase = (0, supabase_1.getServerClient)();
        const { data: oferta } = await supabase.from('oferta').select('*').eq('id', body.oferta_id).single();
        if (!oferta)
            throw new common_1.NotFoundException('Oferta no encontrada');
        if (!['ABIERTA', 'CON_CANDIDATOS'].includes(oferta.estado)) {
            throw new common_1.BadRequestException('La oferta no está disponible');
        }
        const { data: existing } = await supabase.from('postulacion').select('id')
            .eq('oferta_id', body.oferta_id).eq('trabajador_id', body.trabajador_id).single();
        if (existing)
            throw new common_1.ConflictException('Ya postulaste a esta oferta');
        const { data, error } = await supabase.from('postulacion').insert({
            ...body, score_match: oferta.score_promedio || 50, estado: 'PENDIENTE',
        }).select().single();
        if (error)
            throw new Error(error.message);
        return data;
    }
    async list(trabajadorId, estado) {
        const supabase = (0, supabase_1.getServerClient)();
        let query = supabase.from('postulacion')
            .select('*, oferta:oferta(id, titulo, categoria, region, remuneration, empresa:empresa(id, razon_social))')
            .eq('trabajador_id', trabajadorId);
        if (estado)
            query = query.eq('estado', estado);
        const { data, error } = await query.order('created_at', { ascending: false });
        if (error)
            throw new Error(error.message);
        return { data, total: data?.length || 0 };
    }
    async accept(id) {
        const supabase = (0, supabase_1.getServerClient)();
        const { data: p } = await supabase.from('postulacion').select('*').eq('id', id).single();
        if (!p)
            throw new common_1.NotFoundException('Postulación no encontrada');
        if (p.estado !== 'PENDIENTE')
            throw new common_1.BadRequestException('Ya fue procesada');
        const { data } = await supabase.from('postulacion').update({ estado: 'ACEPTADO' }).eq('id', id).select().single();
        await supabase.from('oferta').update({ estado: 'CON_CANDIDATOS' }).eq('id', p.oferta_id);
        return { postulacion: data, message: 'Postulación aceptada' };
    }
    async reject(id, motivo) {
        const supabase = (0, supabase_1.getServerClient)();
        const { data: p } = await supabase.from('postulacion').select('*').eq('id', id).single();
        if (!p)
            throw new common_1.NotFoundException('Postulación no encontrada');
        if (p.estado !== 'PENDIENTE')
            throw new common_1.BadRequestException('Ya fue procesada');
        const mensaje = motivo ? `[Rechazado: ${motivo}]` : p.mensaje;
        const { data } = await supabase.from('postulacion').update({ estado: 'RECHAZADO', mensaje }).eq('id', id).select().single();
        return { postulacion: data, message: 'Postulación rechazada' };
    }
};
exports.PostulacionesService = PostulacionesService;
exports.PostulacionesService = PostulacionesService = __decorate([
    (0, common_1.Injectable)()
], PostulacionesService);
//# sourceMappingURL=postulaciones.service.js.map