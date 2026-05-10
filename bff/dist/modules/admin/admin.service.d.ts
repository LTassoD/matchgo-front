export declare class AdminService {
    dashboard(): Promise<{
        stats: {
            totalUsuarios: number | null;
            totalEmpresas: number | null;
            totalTrabajadores: number | null;
            totalOfertas: number | null;
            totalPostulaciones: number | null;
            totalFacturas: number | null;
            pendientes: number | null;
        };
        ultimosUsuarios: any[] | null;
        ultimasFacturas: any[] | null;
    }>;
    listUsuarios(filters: any): Promise<{
        data: any[] | null;
        total: number;
        page: number;
        limit: number;
    }>;
    getUsuario(id: string): Promise<any>;
    updateUsuario(id: string, body: any): Promise<any>;
    deleteUsuario(id: string): Promise<{
        message: string;
    }>;
    listEmpresas(filters: any): Promise<{
        data: any[] | null;
        total: number;
        page: number;
        limit: number;
    }>;
    updateEmpresa(id: string, body: any): Promise<any>;
    listTrabajadores(filters: any): Promise<{
        data: any[] | null;
        total: number;
        page: number;
        limit: number;
    }>;
    updateTrabajador(id: string, body: any): Promise<any>;
    listSuscripciones(filters: any): Promise<{
        data: any[] | null;
        total: number;
        page: number;
        limit: number;
    }>;
    updateSuscripcion(id: string, body: any): Promise<any>;
    listFacturas(filters: any): Promise<{
        data: any[] | null;
        total: number;
        page: number;
        limit: number;
    }>;
    logs(desde?: string, hasta?: string): Promise<{
        message: string;
    }>;
}
