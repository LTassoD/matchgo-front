export declare class FacturacionService {
    getPlanes(): Promise<{
        ofertas_limite: string | number;
        trabajadores_visibles: string | number;
        nombre: string;
        precio: number;
        id: string;
    }[]>;
    crearFactura(empresa_id: string, plan_id: string): Promise<any>;
    cobrarFactura(id: string): Promise<any>;
    private activarSuscripcion;
    private proximaRenovacion;
    listarFacturas(empresa_id?: string, page?: number, limit?: number): Promise<{
        data: any[] | null;
        total: number;
        page: number;
        limit: number;
    }>;
    webhookPago(payload: any): Promise<any>;
    reporteFacturacion(desde?: string, hasta?: string): Promise<{
        total: any;
        count: number;
        data: any[];
    }>;
}
