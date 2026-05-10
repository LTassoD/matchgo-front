export declare class AuthService {
    signIn(email: string, password: string): Promise<{
        user: import("@supabase/supabase-js").AuthUser;
        session: import("@supabase/supabase-js").AuthSession;
    }>;
    signUp(email: string, password: string, nombre: string, tipo: string): Promise<{
        user: import("@supabase/supabase-js").AuthUser | null;
        session: import("@supabase/supabase-js").AuthSession | null;
    }>;
    getMe(token: string): Promise<import("@supabase/supabase-js").AuthUser>;
    signOut(): Promise<{
        message: string;
    }>;
}
