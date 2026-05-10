"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.AuthService = void 0;
const common_1 = require("@nestjs/common");
const supabase_1 = require("../../common/supabase");
let AuthService = class AuthService {
    async signIn(email, password) {
        const supabase = (0, supabase_1.getAnonClient)();
        const { data, error } = await supabase.auth.signInWithPassword({ email, password });
        if (error)
            throw new common_1.UnauthorizedException(error.message);
        return { user: data.user, session: data.session };
    }
    async signUp(email, password, nombre, tipo) {
        const supabase = (0, supabase_1.getAnonClient)();
        const { data, error } = await supabase.auth.signUp({
            email, password,
            options: { data: { nombre, tipo } },
        });
        if (error)
            throw new common_1.UnauthorizedException(error.message);
        if (data.user) {
            const serverClient = (0, supabase_1.getServerClient)();
            await serverClient.from('usuario').insert({
                id: data.user.id, email, nombre, tipo,
            });
            if (tipo === 'EMPRESA') {
                await serverClient.from('empresa').insert({
                    usuario_id: data.user.id, razon_social: nombre,
                    rut: `rut-${Date.now()}`, telefono: '', region: 'RM', contacto_nombre: nombre,
                });
            }
            else {
                await serverClient.from('trabajador').insert({
                    usuario_id: data.user.id, nombre_completo: nombre,
                    rut: `rut-${Date.now()}`, telefono: '', region: 'RM', comuna: 'Santiago',
                });
            }
        }
        return { user: data.user, session: data.session };
    }
    async getMe(token) {
        const supabase = (0, supabase_1.getAnonClient)();
        const { data: { user }, error } = await supabase.auth.getUser(token);
        if (error || !user)
            throw new common_1.UnauthorizedException('Token inválido');
        return user;
    }
    async signOut() {
        return { message: 'Sesión cerrada' };
    }
};
exports.AuthService = AuthService;
exports.AuthService = AuthService = __decorate([
    (0, common_1.Injectable)()
], AuthService);
//# sourceMappingURL=auth.service.js.map