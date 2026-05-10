"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.MatchingService = void 0;
const common_1 = require("@nestjs/common");
const supabase_1 = require("../../common/supabase");
let MatchingService = class MatchingService {
    async runMatching(ofertaId) {
        const url = `${process.env.SUPABASE_URL}/functions/v1/matching/run?oferta_id=${ofertaId}`;
        const res = await fetch(url, {
            headers: { Authorization: `Bearer ${process.env.SUPABASE_SERVICE_ROLE_KEY}` },
        });
        if (!res.ok)
            throw new Error('Error al ejecutar matching');
        return res.json();
    }
    async getMatches(ofertaId, sortBy = 'score_match', order = 'desc') {
        const supabase = (0, supabase_1.getServerClient)();
        const { data, error } = await supabase.from('postulacion')
            .select('*, trabajador:trabajador(id, nombre_completo, region, comuna, disponibilidad, certificaciones, telefono)')
            .eq('oferta_id', ofertaId)
            .order(sortBy, { ascending: order === 'asc' });
        if (error)
            throw new common_1.NotFoundException('No se encontraron matches');
        return { data, total: data?.length || 0 };
    }
};
exports.MatchingService = MatchingService;
exports.MatchingService = MatchingService = __decorate([
    (0, common_1.Injectable)()
], MatchingService);
//# sourceMappingURL=matching.service.js.map