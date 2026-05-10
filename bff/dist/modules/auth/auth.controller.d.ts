import { AuthService } from './auth.service';
export declare class AuthController {
    private readonly authService;
    constructor(authService: AuthService);
    signIn(body: {
        email: string;
        password: string;
    }): Promise<{
        user: import("@supabase/supabase-js").AuthUser;
        session: import("@supabase/supabase-js").AuthSession;
    }>;
    signUp(body: {
        email: string;
        password: string;
        nombre: string;
        tipo: string;
    }): Promise<{
        user: import("@supabase/supabase-js").AuthUser | null;
        session: import("@supabase/supabase-js").AuthSession | null;
    }>;
    getMe(auth: string): Promise<import("@supabase/supabase-js").AuthUser>;
    signOut(): Promise<{
        message: string;
    }>;
}
