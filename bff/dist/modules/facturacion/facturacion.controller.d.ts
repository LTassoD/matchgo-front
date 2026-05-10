import { FacturacionService } from './facturacion.service';
export declare class FacturacionController {
    private readonly service;
    constructor(service: FacturacionService);
    getPlanes(): Promise<{
        ofertas_limite: string | number;
        trabajadores_visibles: string | number;
        nombre: string;
        precio: number;
        id: string;
    }[]>;
    crearFactura(body: {
        empresa_id: string;
        plan_id: string;
    }): Promise<any>;
    cobrarFactura(id: string): Promise<any>;
    listarFacturas(empresa_id?: string, page?: string, limit?: string): Promise<{
        data: any[] | null;
        total: number;
        page: number;
        limit: number;
    }>;
    webhookPago(body: any): Promise<any>;
    reporteFacturacion(desde?: string, hasta?: string): Promise<{
        total: any;
        count: number;
        data: any[];
    }>;
}
