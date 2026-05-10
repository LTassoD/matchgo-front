"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.FacturacionService = void 0;
const common_1 = require("@nestjs/common");
const supabase_1 = require("../../common/supabase");
const PLANES = {
    BASICO: { nombre: 'Básico', precio: 0, ofertas_limite: 3, trabajadores_visibles: 50 },
    PROFESIONAL: { nombre: 'Profesional', precio: 29900, ofertas_limite: 20, trabajadores_visibles: 200 },
    EMPRESARIAL: { nombre: 'Empresarial', precio: 99900, ofertas_limite: -1, trabajadores_visibles: -1 },
};
let FacturacionService = class FacturacionService {
    async getPlanes() {
        return Object.entries(PLANES).map(([id, plan]) => ({
            id,
            ...plan,
            ofertas_limite: plan.ofertas_limite === -1 ? 'Ilimitado' : plan.ofertas_limite,
            trabajadores_visibles: plan.trabajadores_visibles === -1 ? 'Ilimitado' : plan.trabajadores_visibles,
        }));
    }
    async crearFactura(empresa_id, plan_id) {
        const plan = PLANES[plan_id];
        if (!plan)
            throw new common_1.BadRequestException('Plan no válido');
        if (plan.precio === 0)
            throw new common_1.BadRequestException('El plan básico no genera facturación');
        const supabase = (0, supabase_1.getServerClient)();
        const { data: empresa } = await supabase.from('empresa').select('*').eq('id', empresa_id).single();
        if (!empresa)
            throw new common_1.NotFoundException('Empresa no encontrada');
        const factura = {
            empresa_id,
            monto: plan.precio,
            plan: plan_id,
            estado: 'PENDIENTE',
            concepto: `Suscripción ${plan.nombre} - ${new Date().toLocaleDateString('es-CL')}`,
        };
        const { data, error } = await supabase.from('factura').insert(factura).select().single();
        if (error)
            throw new Error(error.message);
        return data;
    }
    async cobrarFactura(id) {
        const supabase = (0, supabase_1.getServerClient)();
        const { data: factura, error: findError } = await supabase
            .from('factura').select('*').eq('id', id).single();
        if (findError || !factura)
            throw new common_1.NotFoundException('Factura no encontrada');
        if (factura.estado !== 'PENDIENTE')
            throw new common_1.BadRequestException('La factura ya fue procesada');
        const { data, error } = await supabase
            .from('factura').update({ estado: 'PAGADA', pagada_at: new Date().toISOString() })
            .eq('id', id).select().single();
        if (error)
            throw new Error(error.message);
        await this.activarSuscripcion(factura.empresa_id, factura.plan);
        return data;
    }
    async activarSuscripcion(empresa_id, plan) {
        const supabase = (0, supabase_1.getServerClient)();
        const { data: existente } = await supabase
            .from('suscripcion').select('*').eq('empresa_id', empresa_id).maybeSingle();
        if (existente) {
            await supabase.from('suscripcion').update({
                plan, estado: 'ACTIVA', renovacion_at: this.proximaRenovacion(),
            }).eq('id', existente.id);
        }
        else {
            await supabase.from('suscripcion').insert({
                empresa_id, plan, estado: 'ACTIVA', renovacion_at: this.proximaRenovacion(),
            });
        }
    }
    proximaRenovacion() {
        const d = new Date();
        d.setMonth(d.getMonth() + 1);
        return d.toISOString();
    }
    async listarFacturas(empresa_id, page = 1, limit = 20) {
        const supabase = (0, supabase_1.getServerClient)();
        let query = supabase.from('factura').select('*, empresa(razon_social)', { count: 'exact' }).order('created_at', { ascending: false });
        if (empresa_id)
            query = query.eq('empresa_id', empresa_id);
        const from = (page - 1) * limit;
        const to = page * limit - 1;
        query = query.range(from, to);
        const { data, count } = await query;
        return { data, total: count || 0, page, limit };
    }
    async webhookPago(payload) {
        if (payload.status === 'AUTHORIZED') {
            return this.cobrarFactura(payload.factura_id);
        }
        return { message: 'Webhook recibido, pendiente de autorización' };
    }
    async reporteFacturacion(desde, hasta) {
        const supabase = (0, supabase_1.getServerClient)();
        let query = supabase.from('factura').select('*');
        if (desde)
            query = query.gte('created_at', desde);
        if (hasta)
            query = query.lte('created_at', hasta);
        const { data } = await query;
        if (!data)
            return { total: 0, count: 0, data: [] };
        const total = data.reduce((sum, f) => sum + (f.estado === 'PAGADA' ? f.monto : 0), 0);
        return { total, count: data.length, data };
    }
};
exports.FacturacionService = FacturacionService;
exports.FacturacionService = FacturacionService = __decorate([
    (0, common_1.Injectable)()
], FacturacionService);
//# sourceMappingURL=facturacion.service.js.map