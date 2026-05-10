import { TrabajadoresService } from './trabajadores.service';
export declare class TrabajadoresController {
    private readonly service;
    constructor(service: TrabajadoresService);
    create(body: any): Promise<any>;
    findOne(id?: string, usuarioId?: string): Promise<any>;
    update(id: string, body: any): Promise<any>;
    search(empresaId: string, filters: any): Promise<{
        data: any[] | null;
        total: number;
        page: any;
        limit: any;
    }>;
}
