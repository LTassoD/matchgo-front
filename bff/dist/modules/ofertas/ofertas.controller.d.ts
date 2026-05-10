import { OfertasService } from './ofertas.service';
export declare class OfertasController {
    private readonly service;
    constructor(service: OfertasService);
    create(body: any): Promise<any>;
    list(filters: any): Promise<{
        data: any[] | null;
        total: number;
        page: number;
        limit: number;
        totalPages: number;
    }>;
    findOne(id: string): Promise<any>;
    update(id: string, body: any): Promise<any>;
    remove(id: string): Promise<{
        message: string;
    }>;
}
